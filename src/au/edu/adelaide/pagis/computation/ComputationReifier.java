package au.edu.adelaide.pagis.computation;

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
import au.edu.adelaide.enigma.dynamic.SerializableMethodInvocation;
import au.edu.adelaide.enigma.dynamic.StartPointMetaObject;

import java.lang.reflect.Method;

import au.edu.adelaide.kahn.pn.*;

/**
 * An implementation of the Enigma MOP using Java Dynamic Proxies.
 *
 * @author Darren Webb
 * @version $Id: ComputationReifier.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class ComputationReifier extends StartPointMetaObject
{
	private StartPointMetaObject reifier;
	private TargetAction action;

	public ComputationReifier(TargetAction action,StartPointMetaObject reifier)
	{
		super("default",null);
		this.reifier = reifier;
		this.action = action;
	}

	/**
	 * Method to add a new message semantic to this reifier.
	 */
	public void addMetaMarshalHandler(MetaMarshalHandler newNextHandler)
	{
		reifier.addMetaMarshalHandler(newNextHandler);
	}

	/**
	 * Marshal a method invocation to send to the metaobject.
	 */
	public MetaResult marshal(MetaMethodInvocation invocation)
	{
		return reifier.marshal(invocation);
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
		if ((method.getDeclaringClass().equals(Computation.class))
			|| (method.getDeclaringClass().equals(MetaObject.class))
			|| (method.getDeclaringClass().equals(MethodReifier.class))
			|| (method.getDeclaringClass().equals(MetaLevelInterface.class))) {
			try {
				Object result = reifier.invoke(proxy,method,args);
				return result;
			}
			catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		else {
			
			MetaMethodInvocation invoc = new SerializableMethodInvocation(method,args);

			//my change 
//			System.out.println("ComputationReifier.invoke(): "+method );
			
//			if(!args.equals(null)){
//				{
//				if(args.length > 0)
//			for (int i = 0; i < args.length; i++) {
//				System.out.println("MyDEBUG: method arg " + i + " is: " + args[i]);
//				}
//			}
//			}
			// create a new method invocation and send via sp metaobject
			Method computationMethod = Computation.class.getDeclaredMethod("invoke",
                                                                     new Class[] {TargetAction.class,
                                                                                  MetaMethodInvocation.class});
			MetaMethodInvocation wrappedInvoc = new SerializableMethodInvocation(computationMethod,
                                                                           new Object[] {action,invoc});
			MetaResult wrappedResult = reifier.marshal(wrappedInvoc);

      if (wrappedResult instanceof MetaExceptionResult) {
        System.out.println("wrapped result is an exception");
        System.out.println("method was "+method+" args "+args);
        ((MetaExceptionResult)wrappedResult).getException().printStackTrace();
				return new IllegalStateException();
      }
      else {
        MetaResult result = (MetaResult)(((MetaValueResult)wrappedResult).getValue());
        if (result instanceof MetaValueResult) {
          return ((MetaValueResult)result).getValue();
        }
        else if (result instanceof MetaExceptionResult) {
          throw ((MetaExceptionResult)result).getException();
        }
        else {
          return new IllegalStateException();
        }
      }
		}
	}
}
