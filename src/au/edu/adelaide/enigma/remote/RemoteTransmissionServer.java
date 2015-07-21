package au.edu.adelaide.enigma.remote;

import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;

import java.rmi.Remote;
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
 * @version $Id: RemoteTransmissionServer.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface RemoteTransmissionServer extends Remote
{
	public MetaResult propogate(MetaMessage message) throws RemoteException, MigratedException;
	public void migrate(RemoteTransmissionServer nextServer) throws RemoteException;
	public void migrate(ServerDescriptor nextServer) throws RemoteException;
	public void setNext(MetaTransmissionHandler nextHandler) throws RemoteException, MigratedException;
	public ServerDescriptor getServerDescriptor() throws RemoteException;
}
