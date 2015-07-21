package au.edu.adelaide.enigma.remote;

import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaMessage;

import java.rmi.RemoteException;
import java.io.Serializable;

/**
 *
 * @author Darren Webb
 * @version $Id: ServerDescriptor.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface ServerDescriptor extends Serializable
{
	public RemoteTransmissionServer newRemoteTransmissionServer() throws RemoteException;
	public String getDisplayName();
}
