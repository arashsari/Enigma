package au.edu.adelaide.enigma.remote;

import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaHandlerClass;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MetaObject;

import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Darren Webb
 * @version $Id: RemoteTransmissionHandlerClass.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class RemoteTransmissionHandlerClass implements MetaHandlerClass
{
	public static final String PROPERTY_NAME = "metabehavior.remote";
	public static final String NAME = "Remote Transmission";
	public static final String DESC = "Enables transmission to a baseobject that is remote.";
	public static final String GUI_PROPERTIES = "au.edu.adelaide.gui.meta.RemoteTransmissionPanel";
	public static final String GUI_DECORATOR = "";

	public void customizeMetaObject(MetaLevelInterface metalevelInterface)
	{
		MetaObject metaObject = metalevelInterface.getTargetMetaObject();
		try
		{
			RemoteTransmissionServer server = LocalServerDescriptor.getServerDescriptor().newRemoteTransmissionServer();
			RemoteTransmissionHandler handler = new RemoteTransmissionHandler(metaObject,server);
			metaObject.addMetaTransmissionHandler(handler);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	public void customizeMetaObject(MetaLevelInterface metalevelInterface,
									Map metaInformation)
	{
		MetaObject metaObject = metalevelInterface.getTargetMetaObject();
		try
		{
			String descName = RemoteTransmissionHandlerClass.PROPERTY_NAME
				+"serverdescriptor.preferred";
			ServerDescriptor desc = (ServerDescriptor)metaInformation.get(descName);

			if (desc == null)
			{
				customizeMetaObject(metalevelInterface);
				return;
			}

			RemoteTransmissionServer server = desc.newRemoteTransmissionServer();
			RemoteTransmissionHandler handler = new RemoteTransmissionHandler(metaObject,server);
			metaObject.addMetaTransmissionHandler(handler);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
}
