package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaTransmit;
import au.edu.adelaide.enigma.mop.MetaExecute;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;
import au.edu.adelaide.enigma.mop.MethodReifier;
import au.edu.adelaide.enigma.mop.MetaMarshalHandler;

import java.lang.reflect.Method;

/**
 * An implementation of the Enigma MOP using Java Dynamic Proxies.
 *
 * @author Darren Webb
 * @version $Id: StartPointMetaObject.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class StartPointMetaObject implements MethodReifier
{
	private MetaMarshalHandler nextHandler;
	private String viewName;

	public StartPointMetaObject(String viewName,MetaMarshalHandler defaultHandler)
	{
		this.nextHandler = defaultHandler;
		this.viewName = viewName;
	}

	/**
	 * Method to add a new message semantic to this reifier.
	 */
	public void addMetaMarshalHandler(MetaMarshalHandler newNextHandler)
	{
		newNextHandler.setNextMarshalHandler(nextHandler);
		nextHandler = newNextHandler;
	}

	/**
	 * Marshal a method invocation to send to the metaobject.
	 */
	public MetaResult marshal(MetaMethodInvocation invocation)
	{
		return nextHandler.handleMarshal(invocation);
	}

	/**
	 * Reify method invocations intended for a baseobject.
	 * The invocation handler intercepts method invocations
	 * on a proxy for the baseobject, creates an object
	 * representing the invocation (reification) then delegates
	 * the reified invocation to the marshal handlers.
	 */
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable
	{
		// handle the metalevel interface methods
		if (method.getDeclaringClass().equals(MetaLevelInterface.class))
		{
			if (args == null || (args.length == 1 && args[0].equals(viewName))) {
				if (method.getName().equals("getThisMethodReifier")) {
					
					return this;
					
				}
				else {
					// this is us
					// either we're the default (lowest level to answer)
					// or we were named explicitly
					return proxy;
				}
			}
			else {
				// pass the method up to the next method reifier
				System.out.println("i'm not the named view....");
				System.out.println("passing method up to next method reifier (if it exists)");
				throw new UnsupportedOperationException();
			}
		}
		else if (method.getDeclaringClass().equals(MethodReifier.class))
		{
			// this object implements these interfaces
			return method.invoke(this,args);
		}
		// handle if the message is for the metaobject
		else if ((method.getDeclaringClass().equals(MetaObject.class))
				 || (method.getDeclaringClass().equals(MetaTransmit.class))
				 || (method.getDeclaringClass().equals(MetaExecute.class)))
		{
			MetaMethodInvocation invoc = new SerializableMethodInvocation(method,args);
			MetaResult result = marshal(invoc);

			
			if (result instanceof MetaValueResult)
				return ((MetaValueResult)result).getValue();
			else if (result instanceof MetaExceptionResult)
				throw ((MetaExceptionResult)result).getException();
			else
				return new IllegalStateException();
		}
		// this must be for the baseobject
		else
		{
			// representation of the baselevel method invocation
			MetaMethodInvocation invoc = new SerializableMethodInvocation(method,args);

			// invoke the handleMethodInvocation method of the metaobject
			Method metaObjectMethod = MetaObject.class.getDeclaredMethod("handleMethodInvocation",
																		 new Class[] {MetaMethodInvocation.class});
			MetaMethodInvocation metaInvoc = new SerializableMethodInvocation(metaObjectMethod,new Object[] {invoc});

			// the handler finds the metaobject for us
			MetaResult result = (MetaResult)((MetaValueResult)marshal(metaInvoc)).getValue();
			
			
//			if(method.getName() != "invoke"){
//				if(method.getName() != "handleMethodInvocation"){
//					if(method.getName() != "hashCode"){
//				 System.out.println("  "+ method.getName() + " -- class : " + invoc.);//+ "  ==proxy = "+proxy.getClass().getName());
//				}
//			}
//			}
			
			
			if (result instanceof MetaValueResult)
				return ((MetaValueResult)result).getValue();
			else if (result instanceof MetaExceptionResult)
				throw ((MetaExceptionResult)result).getException();
			else
				return new IllegalStateException();
		}
	}
}
