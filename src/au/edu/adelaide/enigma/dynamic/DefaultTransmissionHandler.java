package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaObject;

/**
 * Default handler of transmission semantics.  This default
 * implementation is wrapped or decorated by user defined 
 * handlers.
 *
 * @author Darren Webb
 * @version $Id: DefaultTransmissionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class DefaultTransmissionHandler implements MetaTransmissionHandler
{
	private MetaObject metaObject;

	public DefaultTransmissionHandler(MetaObject metaObject)
	{
		this.metaObject = metaObject;
	}

	/**
	 * Set the next transmission handler.  There is no transmission
	 * handler after the default so this method does nothing.
	 */
	public void setNextTransmissionHandler(MetaTransmissionHandler handler)
	{
	}

	/**
	 * Implements the default transmission semantics.
	 */
	public MetaResult handleTransmission(MetaMessage message)
	{
		MetaMethodInvocation invocation = (MetaMethodInvocation)message;
		return invocation.invoke(metaObject);
	}
}
