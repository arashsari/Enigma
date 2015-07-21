package au.edu.adelaide.enigma.remote;

import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.rmi.RemoteException;

/**
 * Interface for a migratable RMI server object.  The interface defines
 * methods necessary to set the next handler from this server, and also
 * to set the next server so this server object can set the handler of
 * the new server.  The propogate method then throws a migrated exception 
 * containing the next server address.  The server disappears when there
 * are no longer references to it.
 *
 * @author Darren Webb
 * @version $Id: RemoteTransmissionServerImpl.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class RemoteTransmissionServerImpl extends UnicastRemoteObject
	implements RemoteTransmissionServer
{
	private MetaTransmissionHandler nextHandler;
	private boolean migrated = false;
	private boolean waitingToMigrate = false;
	private int callCount = 0;
	private RemoteTransmissionServer nextServer;
	private ServerDescriptor descriptor;

	public RemoteTransmissionServerImpl(ServerDescriptor descriptor) throws RemoteException
	{
		this.descriptor = descriptor;
	}

	public ServerDescriptor getServerDescriptor()
	{
		return descriptor;
	}

	public MetaResult propogate(MetaMessage message) throws MigratedException
	{
		synchronized (this)
		{
			// don't try anything if we're migrating
			while (waitingToMigrate)
				try{wait();} catch(Exception e) {}

			// only enter the method if we haven't migrated
			if (migrated)
				throw new MigratedException(nextServer);
			else
				callCount++;
		}

		try
		{
			// actually execute the method
			return nextHandler.handleTransmission(message);
		}
		finally
		{
			synchronized (this)
			{
				// decrement the call count
				callCount--;
				notifyAll();
			}
		}
	}

	public void migrate(ServerDescriptor nextServer) throws RemoteException
	{
		migrate(nextServer.newRemoteTransmissionServer());
	}

	public void migrate(RemoteTransmissionServer nextServer)
	{
		synchronized (this)
		{
			// wait until there are no more callers
			waitingToMigrate = true;
		    while (callCount > 0)
				try{wait();} catch(Exception e) {}

			try
			{
				nextServer.setNext(nextHandler); // copy the metaobject to new server
				migrated = true;                 // only migrated if no exception
				this.nextServer = nextServer;
			}
			catch (RemoteException e)
			{
				// migration failed
				System.out.println("migration failed");
				waitingToMigrate = false;
			}

			notifyAll();
		}
	}

	public void setNext(MetaTransmissionHandler nextHandler)
	{
		this.nextHandler = nextHandler;
	}
}
