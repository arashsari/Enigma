package au.edu.adelaide.enigma.mop;

import java.lang.reflect.Method;

/**
 * Reification of a method invocation.  The MetaMethodInvocation reifies 
 * (or makes explicit) the act of invoking a method invocation on an object.
 *
 * @author Darren Webb
 * @version $Id: MetaMethodInvocation.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaMethodInvocation extends MetaMessage
{
	/**
	 * Get the method to be invoked by this message.
	 */
	public Method getMethod();

	/**
	 * Return the name of the method associated with this
	 * method invocation.
	 */
	public String getMethodName();

	/**
	 * Get the arguments for the method invocation.
	 */
	public Object[] getArguments();

	/**
	 * Invoke this method on the provided baseObject.
	 */
	public MetaResult invoke(Object baseObject);
}
