package au.edu.adelaide.enigma.mop;

/**
 * A handler of execution semantics of messages invoked on a baseobject.
 * The metaobject delegates execution to each handler to wrap the default
 * execution semantics with new execution semantics.
 *
 * @author Darren Webb
 * @version $Id: MetaExecutionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaExecutionHandler extends MetaLevelObject
{
	/**
	 * Set the next execution handler in the chain.
	 */
	public void setNextExecutionHandler(MetaExecutionHandler handler);

	/**
	 * Trap to introduce new execution behaviour to the baselevel.  The
	 * metalevel programmer implements this method to introduce new
	 * behaviour before and after the actual method invocation.  The
	 * actual method invocation is delegated to the next execution handler.
	 */
	public MetaResult handleExecution(Object target,
									  MetaMethodInvocation invocation);
}
