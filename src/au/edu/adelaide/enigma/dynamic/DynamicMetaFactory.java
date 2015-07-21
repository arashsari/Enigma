package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaFactory;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaHandlerClass;
import au.edu.adelaide.enigma.mop.MetaMarshalHandler;
import au.edu.adelaide.enigma.mop.MetaExecutionHandler;
import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MethodReifier;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.lang.reflect.Proxy;

/**
 * An implementation of the Enigma MOP using Java Dynamic Proxies.
 *
 * @author Darren Webb
 * @version $Id: DynamicMetaFactory.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class DynamicMetaFactory implements MetaFactory
{
	private List customizeList = new LinkedList();
	private String viewName;

	public DynamicMetaFactory()
	{
		this("default");
	}

	public DynamicMetaFactory(String viewName)
	{
		this.viewName = viewName;
	}

	public String getViewName()
	{
		return viewName;
	}

	/**
	 * Customize all future instances of the baseClass with the provided
	 * customization class.  The metahandler class is invoked for each
	 * instance of the baseclass or subclass of the baseclass.
	 * Customizations are invoked in the order they are added to the
	 * factory.
	 */
	public void addMetaLevelCustomization(Class baseClass,
										  MetaHandlerClass customization)
	{
		customizeList.add(new ListEntry(baseClass,customization));
	}

	protected class ListEntry implements MetaLevelObject
	{
		private Class key;
		private MetaHandlerClass value;

		public ListEntry(Class key,MetaHandlerClass value)
		{
			this.key = key;
			this.value = value;
		}

		public Class getBaseClass()
		{
			return key;
		}

		public MetaHandlerClass getMetaHandlerClass()
		{
			return value;
		}
	}

	public MetaObject newMetaObject(Object baseObject,MetaExecutionHandler defaultHandler)
	{
		return new EndPointMetaObject(baseObject,defaultHandler);
	}

	public MetaMarshalHandler newDefaultMarshalHandler(MetaObject metaObject)
	{
		
		return new DefaultMarshalHandler(metaObject);
	}

	public MetaObject newDefaultTransmissionHandler(MetaObject metaObject)
	{
		EndPointTransmissionHandler handler = new EndPointTransmissionHandler(metaObject);
		Set metaObjectInterfaceSet = new HashSet();
		
		Toolkit.getAllInheritedInterfaces(metaObjectInterfaceSet,metaObject.getClass());
//		Toolkit.getAllInheritedInterfaces(metaObject.getClass());
		
		Class[] metaObjectInterfaces = Toolkit.setToClassArray(metaObjectInterfaceSet);
		metaObject = (MetaObject)Proxy.newProxyInstance(metaObject.getClass().getClassLoader(),
														metaObjectInterfaces,handler);
		return metaObject;
	}

	public MetaExecutionHandler newDefaultExecutionHandler()
	{
		return new DefaultExecutionHandler();
	}

	public MetaObject newMetaObjectFor(Object baseObject)
	{
		// create the metaobject and its transmission proxy
		MetaExecutionHandler executionHandler = newDefaultExecutionHandler();
		return newMetaObjectFor(baseObject,executionHandler);
	}

	public MetaObject newMetaObjectFor(Object baseObject,MetaExecutionHandler defaultExecution)
	{
		// create the metaobject and its transmission proxy
		return newDefaultTransmissionHandler(newMetaObject(baseObject,defaultExecution));
	}

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public Object newMetaLevelFor(Object baseObject,MetaObject metaObject)
	{
		return newMetaLevelFor(viewName,baseObject,metaObject);
	}

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject)
	{
		// create the sender side and its baseobject proxy
		MetaMarshalHandler marshalHandler = newDefaultMarshalHandler(metaObject);
		return newMetaLevelFor(viewName,baseObject,metaObject,marshalHandler);
	}

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public Object newMetaLevelFor(Object baseObject,MetaObject metaObject,MetaMarshalHandler defaultMarshal)
	{
		return newMetaLevelFor(viewName,baseObject,metaObject,defaultMarshal);
	}

	public Object newMetaLevelFor(Object baseObject,MethodReifier reifier)
	{
		Set interfaceSet = new HashSet();
		Toolkit.getAllInheritedInterfaces(interfaceSet,baseObject.getClass());
//		Toolkit.getAllInheritedInterfaces(baseObject.getClass());
		
		interfaceSet.add(MethodReifier.class);
		interfaceSet.add(MetaObject.class);
		interfaceSet.add(MetaLevelInterface.class);
		Class[] interfaces = Toolkit.setToClassArray(interfaceSet);
		Object baseObjectProxy = Proxy.newProxyInstance(baseObject.getClass().getClassLoader(),
														interfaces,reifier);
		return baseObjectProxy;
	}

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject,MetaMarshalHandler defaultMarshal)
	{
		// create the sender side and its baseobject proxy
		MethodReifier reifier = new StartPointMetaObject(viewName,defaultMarshal);
		return newMetaLevelFor(baseObject,reifier);
	}

	/**
	 * Create a new metaobject for the given baseobject.  All necessary
	 * metalevel objects are created, and a new reference for the baseobject
	 * is returned.
	 */
	public Object newMetaLevelFor(Object baseObject)
	{
		return newMetaLevelFor(viewName,baseObject);
	}

	/**
	 * Create a new metaobject for the given baseobject.  All necessary
	 * metalevel objects are created, and a new reference for the baseobject
	 * is returned.
	 */
	public Object newMetaLevelFor(String viewName,Object baseObject)
	{
		baseObject = newMetaLevelFor(viewName,baseObject,newMetaObjectFor(baseObject));
		customize(baseObject);
		return baseObject;
	}

	public void customize(Object baseObject)
	{
		// apply the metalevel customizations
		Iterator i = customizeList.iterator();
		while (i.hasNext())
		{
			ListEntry entry = (ListEntry)i.next();
			if (entry.getBaseClass().isAssignableFrom(baseObject.getClass()))
			{
				entry.getMetaHandlerClass().customizeMetaObject((MetaLevelInterface)baseObject);
			}
		}
	}
}



//package au.edu.adelaide.enigma.dynamic;
//
//import au.edu.adelaide.enigma.mop.MetaFactory;
//import au.edu.adelaide.enigma.mop.MetaObject;
//import au.edu.adelaide.enigma.mop.MetaLevelInterface;
//import au.edu.adelaide.enigma.mop.MetaLevelObject;
//import au.edu.adelaide.enigma.mop.MetaHandlerClass;
//import au.edu.adelaide.enigma.mop.MetaMarshalHandler;
//import au.edu.adelaide.enigma.mop.MetaExecutionHandler;
//import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
//import au.edu.adelaide.enigma.mop.MethodReifier;
//
//import java.util.Map;
//import java.util.Set;
//import java.util.HashSet;
//import java.util.List;
//import java.util.LinkedList;
//import java.util.Iterator;
//import java.lang.reflect.Proxy;
//
///**
// * An implementation of the Enigma MOP using Java Dynamic Proxies.
// *
// * @author Darren Webb
// * @version $Id: DynamicMetaFactory.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
// */
//public class DynamicMetaFactory implements MetaFactory
//{
//	private List customizeList = new LinkedList();
//	private String viewName;
//
//	public DynamicMetaFactory()
//	{
//		this("default");
//	}
//
//	public DynamicMetaFactory(String viewName)
//	{
//		this.viewName = viewName;
//	}
//
//	public String getViewName()
//	{
//		return viewName;
//	}
//
//	/**
//	 * Customize all future instances of the baseClass with the provided
//	 * customization class.  The metahandler class is invoked for each
//	 * instance of the baseclass or subclass of the baseclass.
//	 * Customizations are invoked in the order they are added to the
//	 * factory.
//	 */
//	public void addMetaLevelCustomization(Class baseClass,
//										  MetaHandlerClass customization)
//	{
//		customizeList.add(new ListEntry(baseClass,customization));
//	}
//
//	protected class ListEntry implements MetaLevelObject
//	{
//		private Class key;
//		private MetaHandlerClass value;
//
//		public ListEntry(Class key,MetaHandlerClass value)
//		{
//			this.key = key;
//			this.value = value;
//		}
//
//		public Class getBaseClass()
//		{
//			return key;
//		}
//
//		public MetaHandlerClass getMetaHandlerClass()
//		{
//			return value;
//		}
//	}
//
//	public MetaObject newMetaObject(Object baseObject,MetaExecutionHandler defaultHandler)
//	{
//		return new EndPointMetaObject(baseObject,defaultHandler);
//	}
//
//	public MetaMarshalHandler newDefaultMarshalHandler(MetaObject metaObject)
//	{
//		return new DefaultMarshalHandler(metaObject);
//	}
//
//	public MetaObject newDefaultTransmissionHandler(MetaObject metaObject)
//	{
//		EndPointTransmissionHandler handler = new EndPointTransmissionHandler(metaObject);
//		Set metaObjectInterfaceSet = new HashSet();
//		Toolkit.getAllInheritedInterfaces(metaObjectInterfaceSet,metaObject.getClass());
//		Class[] metaObjectInterfaces = Toolkit.setToClassArray(metaObjectInterfaceSet);
//		metaObject = (MetaObject)Proxy.newProxyInstance(metaObject.getClass().getClassLoader(),
//														metaObjectInterfaces,handler);
//		return metaObject;
//	}
//
//	public MetaExecutionHandler newDefaultExecutionHandler()
//	{
//		return new DefaultExecutionHandler();
//	}
//
//	public MetaObject newMetaObjectFor(Object baseObject)
//	{
//		// create the metaobject and its transmission proxy
//		MetaExecutionHandler executionHandler = newDefaultExecutionHandler();
//		return newMetaObjectFor(baseObject,executionHandler);
//	}
//
//	public MetaObject newMetaObjectFor(Object baseObject,MetaExecutionHandler defaultExecution)
//	{
//		// create the metaobject and its transmission proxy
//		return newDefaultTransmissionHandler(newMetaObject(baseObject,defaultExecution));
//	}
//
//	/**
//	 * Complete the metalevel for the provided metaobject
//	 */
//	public Object newMetaLevelFor(Object baseObject,MetaObject metaObject)
//	{
//		return newMetaLevelFor(viewName,baseObject,metaObject);
//	}
//
//	/**
//	 * Complete the metalevel for the provided metaobject
//	 */
//	public Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject)
//	{
//		// create the sender side and its baseobject proxy
//		MetaMarshalHandler marshalHandler = newDefaultMarshalHandler(metaObject);
//		return newMetaLevelFor(viewName,baseObject,metaObject,marshalHandler);
//	}
//
//	/**
//	 * Complete the metalevel for the provided metaobject
//	 */
//	public Object newMetaLevelFor(Object baseObject,MetaObject metaObject,MetaMarshalHandler defaultMarshal)
//	{
//		return newMetaLevelFor(viewName,baseObject,metaObject,defaultMarshal);
//	}
//
//	public Object newMetaLevelFor(Object baseObject,MethodReifier reifier)
//	{
//		Set interfaceSet = new HashSet();
//		Toolkit.getAllInheritedInterfaces(interfaceSet,baseObject.getClass());
//		interfaceSet.add(MethodReifier.class);
//		interfaceSet.add(MetaObject.class);
//		interfaceSet.add(MetaLevelInterface.class);
//		Class[] interfaces = Toolkit.setToClassArray(interfaceSet);
//		Object baseObjectProxy = Proxy.newProxyInstance(baseObject.getClass().getClassLoader(),
//														interfaces,reifier);
//		return baseObjectProxy;
//	}
//
//	/**
//	 * Complete the metalevel for the provided metaobject
//	 */
//	public Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject,MetaMarshalHandler defaultMarshal)
//	{
//		// create the sender side and its baseobject proxy
//		MethodReifier reifier = new StartPointMetaObject(viewName,defaultMarshal);
//		return newMetaLevelFor(baseObject,reifier);
//	}
//
//	/**
//	 * Create a new metaobject for the given baseobject.  All necessary
//	 * metalevel objects are created, and a new reference for the baseobject
//	 * is returned.
//	 */
//	public Object newMetaLevelFor(Object baseObject)
//	{
//		return newMetaLevelFor(viewName,baseObject);
//	}
//
//	/**
//	 * Create a new metaobject for the given baseobject.  All necessary
//	 * metalevel objects are created, and a new reference for the baseobject
//	 * is returned.
//	 */
//	public Object newMetaLevelFor(String viewName,Object baseObject)
//	{
//		baseObject = newMetaLevelFor(viewName,baseObject,newMetaObjectFor(baseObject));
//		customize(baseObject);
//		return baseObject;
//	}
//
//	public void customize(Object baseObject)
//	{
//		// apply the metalevel customizations
//		Iterator i = customizeList.iterator();
//		while (i.hasNext())
//		{
//			ListEntry entry = (ListEntry)i.next();
//			if (entry.getBaseClass().isAssignableFrom(baseObject.getClass()))
//			{
//				entry.getMetaHandlerClass().customizeMetaObject((MetaLevelInterface)baseObject);
//			}
//		}
//	}
//}
