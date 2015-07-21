package au.edu.adelaide.enigma.remote;

import java.rmi.RemoteException;
import java.net.InetAddress;

/**
 *
 * @author Darren Webb
 * @version $Id: GlobusServerDescriptor.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class GlobusServerDescriptor implements ServerDescriptor
{
	//private GlobusRMIService cachedService;
	private String host;
	private int port;

	public GlobusServerDescriptor(String host,int port)
	{
		this.host = host;
		this.port = port;
	}

	public RemoteTransmissionServer newRemoteTransmissionServer() throws RemoteException
	{
		//if (!cachedService.isAlive())
		{
			// re-establish the service
		}
		return null;
		//return cachedService.newRemoteTransmissionServer(new RemoteTransmissionServiceMain());
	}

	public String getDisplayName()
	{
		return "globus://"+host+":"+port;
	}
}
