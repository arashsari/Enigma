package au.edu.adelaide.enigma.mop;

/**
 * A handler of execution semantics of messages invoked on a baseobject.
 * The metaobject delegates execution to each handler to wrap the default
 * execution semantics with new execution semantics.
 *
 * @author Darren Webb
 * @version $Id: AbstractMetaExecutionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public abstract class AbstractMetaExecutionHandler implements MetaExecutionHandler
{
	private MetaExecutionHandler handler;

	/**
	 * Set the next execution handler in the chain.
	 */
	public void setNextExecutionHandler(MetaExecutionHandler handler)
	{
		this.handler = handler;
	}

	protected MetaExecutionHandler getNextExecutionHandler()
	{
		return handler;
	}
}
