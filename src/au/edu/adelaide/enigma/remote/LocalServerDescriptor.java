package au.edu.adelaide.enigma.remote;

import java.rmi.RemoteException;
import java.net.InetAddress;

/**
 *
 * @author Darren Webb
 * @version $Id: LocalServerDescriptor.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class LocalServerDescriptor implements ServerDescriptor
{
	private static ServerDescriptor instance = new LocalServerDescriptor();

	private LocalServerDescriptor() {}

	public static ServerDescriptor getServerDescriptor()
	{
		return instance;
	}

	public RemoteTransmissionServer newRemoteTransmissionServer() throws RemoteException
	{
		return new RemoteTransmissionServerImpl(this);
	}

	public String getDisplayName()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch (Exception e)
		{
			return "unknown host";
		}
	}
}
