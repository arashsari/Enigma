package au.edu.adelaide.enigma.remote;

import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaObject;

import java.util.Map;
import java.util.HashMap;
import java.rmi.RemoteException;

/**
 *
 * @author Darren Webb
 * @version $Id: RemoteTransmissionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class RemoteTransmissionHandler implements MetaTransmissionHandler
{
	private RemoteTransmissionServer server;
	private static int count = 0;
	private int id = count++;

	public RemoteTransmissionHandler(MetaObject metaObject,RemoteTransmissionServer first)
	{
		this.server = first;

		Map properties = new HashMap();
		addProperty(properties,"name",RemoteTransmissionHandlerClass.NAME);
		addProperty(properties,"description",RemoteTransmissionHandlerClass.DESC);
		addProperty(properties,"class",getClass().getName());
		addProperty(properties,"gui.properties.class",RemoteTransmissionHandlerClass.GUI_PROPERTIES);
		addProperty(properties,"gui.decorator.class",RemoteTransmissionHandlerClass.GUI_DECORATOR);
		try
		{
			addProperty(properties,"location.current",first.getServerDescriptor());
			addProperty(properties,"location.preferred",first.getServerDescriptor());
		}
		catch (Exception e)
		{
		}

		metaObject.updateMetaInformation(properties);
	}

	protected void addProperty(Map properties,String property,Object value)
	{
		properties.put(RemoteTransmissionHandlerClass.PROPERTY_NAME+"."+property,value);
	}

	/**
	 * Set the next transmission handler in the chain.
	 */
	public void setNextTransmissionHandler(MetaTransmissionHandler handler)
	{
		while (true)
		{
			try
			{
				server.setNext(handler);
				return;
			}
			catch (MigratedException e)
			{
				server = e.getNextServer();
			}
			catch (RemoteException e)
			{
				// need to do something better here...
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * Trap to introduce new transmission behaviour to the baselevel.  The
	 * metalevel programmer implements this method to introduce new
	 * behaviour before and a message is sent to the metaobject of a 
	 * baseobject.  The actual message is delegated to the next transmission
	 * handler.
	 */
	public MetaResult handleTransmission(MetaMessage message)
	{
		while (true)
		{
			try
			{
				return server.propogate(message);
			}
			catch (MigratedException e)
			{
				server = e.getNextServer();
			}
			catch (RemoteException e)
			{
				// need to do something better here...
				throw new IllegalStateException();
			}
		}
	}
}
