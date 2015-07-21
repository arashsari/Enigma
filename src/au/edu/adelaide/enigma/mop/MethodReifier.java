package au.edu.adelaide.enigma.mop;

import java.lang.reflect.InvocationHandler;

/**
 * Interface that defines an object that reifies method invocations
 * on a baseobject.  The source metaobject implements the reifier
 * as an invocation handler of the baseobject proxy.
 *
 * @author Darren Webb
 * @version $Id: MethodReifier.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MethodReifier extends MetaLevelObject, InvocationHandler
{
	/**
	 * Add a new handler of message semantics that the reifier
	 * will delegate to during a method invocation marshal.
	 */
	public void addMetaMarshalHandler(MetaMarshalHandler handler);
}
