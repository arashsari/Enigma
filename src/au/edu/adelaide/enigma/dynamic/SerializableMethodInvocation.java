package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;
import au.edu.adelaide.enigma.provanance.InformationServiceQuantizer;
import au.edu.adelaide.enigma.provanance.ProvenanceCollector;
//import au.edu.adelaide.enigma.provanance.MIPSingleton;
//import au.edu.adelaide.extendPagis.system.InformationServiceQuantizer;

import java.lang.reflect.Method;
import java.io.Serializable;

/**
 * The representation of a method invocation in progress.  Objects 
 * of this interface do not specify the target baseobject until 
 * invocation time, providing a fair degree of decoupling in case the
 * invocation is delegated elsewhere.
 *
 * @author Darren Webb
 * @version $Id: SerializableMethodInvocation.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class SerializableMethodInvocation implements MetaMethodInvocation
{
	private FixMethodWrapper method;
	private Object[] args;

	public SerializableMethodInvocation(Method method,Object[] args)
	{
		// store the method, and info to restore if we're serialized
		// need method name, declaring class, and parameter classes
		this.method = new FixMethodWrapper(method);

		// store the args
		this.args = args;
	}

	/**
	 * Return the method description associated with this
	 * method invocation.
	 */
	public Method getMethod()
	{
		return method.getMethod();
	}

	/**
	 * Return the name of the method associated with this
	 * method invocation.
	 */
	public String getMethodName()
	{
		return getMethod().getName();
	}

	/**
	 * Get the user-provided arguments of this method
	 * invocation.
	 */
	public Object[] getArguments()
	{
		return args;
	}

	/**
	 * Invoke this method on the provided baseObject.
	 */
	public MetaResult invoke(Object baseObject) {
		try {
			InformationServiceQuantizer isq = InformationServiceQuantizer
					.getInstance();
//
//			if (!(isq.granularityLevel == 9)) {
//				// Call the provenance methods
//				ProvenanceCollector mip = ProvenanceCollector.getInstance();
//				MetaValueResult mr = mip.methodInvocationFiltering(this,
//						baseObject, method, args);
//				return mr;
//			} else {
				return new MetaValueResult(method.getMethod().invoke(
						baseObject, args));
//			}
		} catch (IllegalAccessException e) {
			return new MetaExceptionResult(e);

		} catch (java.lang.reflect.InvocationTargetException e) {
			System.out
					.println("DEBUG: Exception raised in SerializableMethodInvocation, "
							+ "invoking method: " + method.getMethod());
			System.out.println("DEBUG: baseobject class is: "
					+ baseObject.getClass());
			for (int i = 0; i < args.length; i++) {
				System.out
						.println("DEBUG: method arg " + i + " is: " + args[i]);
			}
			e.printStackTrace();
			System.out
					.println("DEBUG: done thread dump for serializable exception");
			return new MetaExceptionResult(e);
		} catch (Throwable e) {
			return new MetaExceptionResult(e);
		}
	}

	
//	public MetaResult invoke(Object baseObject)
//	{
//		 System.out.println("<<< Invocation:>>> "+ this.getMethod().getName()+
//			 "   == caller: "+baseObject.getClass().getSimpleName());
//		try
//		{
//			// call get method in case we've been serialized
//			return new MetaValueResult(method.getMethod().invoke(baseObject,args));
//		}
//    catch (java.lang.reflect.InvocationTargetException e)
//    {
//      System.out.println("DEBUG: Exception raised in SerializableMethodInvocation, "
//                         +"invoking method: "+method.getMethod());
//      System.out.println("DEBUG: baseobject class is: "+baseObject.getClass());
//      for (int i=0;i<args.length;i++) {
//        System.out.println("DEBUG: method arg "+i+" is: "+args[i]);
//      }
//      e.printStackTrace();
//      System.out.println("DEBUG: done thread dump for serializable exception");
//      return new MetaExceptionResult(e);
//    }
//		catch (Throwable e)
//		{
//			return new MetaExceptionResult(e);
//		}
//	}
	
	
	/**
	 * A serializable Method.
	 */
	public class FixMethodWrapper implements Serializable
	{
		// declared transient to avoid serialization
		// if we're serialized, these cache the regenerated objects
		private transient Method method;

		// info to regenerate method and method parameter objects
		private String methodName;
		private Class methodDeclaringClass;
		private FixClassWrapper[] fixedMethodParamTypes;

		public FixMethodWrapper(Method method)
		{
			methodName = method.getName();
			methodDeclaringClass = method.getDeclaringClass();
			Class[] methodParamTypes = method.getParameterTypes();
			fixedMethodParamTypes = new FixClassWrapper[methodParamTypes.length];
			for (int i=0;i<methodParamTypes.length;i++)
				fixedMethodParamTypes[i] = new FixClassWrapper(methodParamTypes[i]);
		}

		public Method getMethod()
		{
			try
			{
				if (method == null)
				{
					// unwrap the fixed classes
					Class[] methodParamTypes = new Class[fixedMethodParamTypes.length];
					for (int i=0;i<fixedMethodParamTypes.length;i++)
						methodParamTypes[i] = fixedMethodParamTypes[i].getWrapped();

					// get the method from its declaring class
					method = methodDeclaringClass.getDeclaredMethod(methodName,
																	methodParamTypes);
				}
				return method;
			}
			catch (Exception e)
			{
				// we can never get here
				throw new IllegalStateException("failed to find a method that *must* exist");
			}
		}
	}

	/*
	 * Wrapper to fix primitive class serialization problems
	 * in JDK 1.1-1.3 (fixed in 1.4).  Based on ProActive
	 * code.  Original copyright:<br>
	 *
	 * FixWrapper.java
	 *
	 * Copyright 1997 - 2001 INRIA Project Oasis. All Rights Reserved.
	 * 
	 * This software is the proprietary information of INRIA Sophia Antipolis.  
	 * 2004 route des lucioles, BP 93 , FR-06902 Sophia Antipolis 
	 * Use is subject to license terms.
	 */
	public class FixClassWrapper implements Serializable
	{
		public boolean isPrimitive = false;
		public Class encapsulated = null;

		/**
		 * Encapsulate primitives types into Class 
		 */
		public FixClassWrapper(Class c)
		{
			if (!c.isPrimitive()) {
				this.isPrimitive = false;
				this.encapsulated = c;
				return;
			}
			if (c.getName().equals("int")) {
				this.isPrimitive = true;
				this.encapsulated = Integer.class;
				return;
			}
			if (c.getName().equals("boolean")) {
				this.isPrimitive = true;
				this.encapsulated = Boolean.class;
				return;
			}
		}

		/**
		 * Give back the original class
		 */
		public Class getWrapped()
		{
			if (this.isPrimitive)
			{
				if (this.encapsulated.getName().equals("java.lang.Integer"))
				{
					return Integer.TYPE;
				}
				if (this.encapsulated.getName().equals("java.lang.Boolean"))
				{
					return Boolean.TYPE;
				}
				return null;
			}
			else
				return this.encapsulated;
		}
	}			
}
