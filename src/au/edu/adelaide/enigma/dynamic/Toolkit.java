package au.edu.adelaide.enigma.dynamic;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A class of convenience methods.
 *
 * @author Darren Webb
 * @version $Id: Toolkit.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public final class Toolkit
{
	private Toolkit() {}

	/**
	 * Get all inherited interfaces of the provided class.  Java's 
	 * Class.getInterfaces() only returns the directly implemented 
	 * interfaces of a class (i.e. it does not traverse the inheritance
	 * graph to determine all interfaces.
	 */
	public static Class[] getAllInheritedInterfaces(Class clazz)
	{
		Set interfaceSet = new HashSet();
		getAllInheritedInterfaces(interfaceSet,clazz);
		return setToClassArray(interfaceSet);
	}

	public static Class[] setToClassArray(Set clazzSet)
	{
		Class[] clazzes = new Class[clazzSet.size()];
		Iterator clazzIterator = clazzSet.iterator();
		for (int i=0; i<clazzes.length; i++)
		{
			clazzes[i] = (Class)clazzIterator.next();
		}
		return clazzes;		
	}

	public static void getAllInheritedInterfaces(Set interfaceSet,Class clazz)
	{
		// recursion terminator
		if (!clazz.getSuperclass().equals(Object.class)){
			
//			System.out.println(" ==interfaces == "+ interfaceSet );
//			System.out.println("clazz.getSuperclass()" + clazz.getSuperclass());
			getAllInheritedInterfaces(interfaceSet,clazz.getSuperclass());
			}
		
		Class[] interfaces = clazz.getInterfaces();
		for (int i=0;i<interfaces.length;i++)
		{
			
			if (interfaces[i].isInterface())
				interfaceSet.add(interfaces[i]);
//			 System.out.println(" ==interfaces[i]== "+ interfaces[i] );
		}               
	}
}
