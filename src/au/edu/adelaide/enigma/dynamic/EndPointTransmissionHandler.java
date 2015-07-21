package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaExecutionHandler;
import au.edu.adelaide.enigma.mop.MetaTransmit;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

/**
 * An implementation of the Enigma MOP using Java Dynamic Proxies.
 *
 * @author Darren Webb
 * @version $Id: EndPointTransmissionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class EndPointTransmissionHandler implements MetaLevelObject, InvocationHandler
{
	private MetaTransmissionHandler nextHandler;

	public EndPointTransmissionHandler(MetaObject metaObject)
	{
		this.nextHandler = new DefaultTransmissionHandler(metaObject);
	}

	/**
	 * Reify method invocations on the metaobject.  The reified
	 * invocations are redirected or delegated to the transmission
	 * handlers.  The default transmission handler executes the
	 * invocation on the real metaobject.
	 */
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable
	{
		
		// handle the metalevel interface methods
		if (method.getDeclaringClass().equals(MetaTransmit.class)
			&& method.getName().equals("addMetaTransmissionHandler"))
		{
		System.out.println("this is actually an addMetaTransmissionHandler");
			MetaTransmissionHandler newNextHandler = (MetaTransmissionHandler)args[0];
			newNextHandler.setNextTransmissionHandler(nextHandler);
			nextHandler = newNextHandler;

			return null;
		}

		MetaMethodInvocation invoc = new SerializableMethodInvocation(method,args);
		MetaResult result = nextHandler.handleTransmission(invoc);
//   my changes		
//		MetaExecutionHandler mxh = new DefaultExecutionHandler();
//		MetaResult res = mxh.handleExecution(target, invoc);
		
		if (result instanceof MetaValueResult)
			return ((MetaValueResult)result).getValue();
		else if (result instanceof MetaExceptionResult)
			throw ((MetaExceptionResult)result).getException();
		else
			return new IllegalStateException();
	}
}
