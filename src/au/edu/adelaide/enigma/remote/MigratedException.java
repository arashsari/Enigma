package au.edu.adelaide.enigma.remote;

import java.rmi.RemoteException;

/**
 *
 * @author Darren Webb
 * @version $Id: MigratedException.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class MigratedException extends RemoteException
{
	private RemoteTransmissionServer nextServer;

	public MigratedException(RemoteTransmissionServer nextServer)
	{
		this.nextServer = nextServer;
	}

	public RemoteTransmissionServer getNextServer()
	{
		return nextServer;
	}
}
