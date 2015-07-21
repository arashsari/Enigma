package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaMarshalHandler;
import au.edu.adelaide.enigma.mop.MetaMessage;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaObject;

/**
 * Default handler of message semantics.  This default
 * implementation is wrapped or decorated by user defined 
 * handlers.
 *
 * @author Darren Webb
 * @version $Id: DefaultMarshalHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class DefaultMarshalHandler implements MetaMarshalHandler
{
	private MetaObject metaObject;

	/**
	 * Constructor for the default marshal handler.  The
	 * default marshal handler invokes messages on the
	 * metaobject for the target metaobject.
	 */
	public DefaultMarshalHandler(MetaObject metaObject)
	{
		this.metaObject = metaObject;
	}

	/**
	 * Set the next marshal handler.  There is no interaction
	 * handler after the default so this method does nothing.
	 */
	public void setNextMarshalHandler(MetaMarshalHandler handler)
	{
	}

	/**
	 * Implements the default message semantics.  This marshal
	 * knows exactly where the metaobject is so invokes the
	 * reified invocation upon it.
	 */
	public MetaResult handleMarshal(MetaMethodInvocation invocation)
	{
		return invocation.invoke(metaObject);
	}
}
