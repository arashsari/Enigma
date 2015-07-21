package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.enigma.dynamic.*;
import au.edu.adelaide.enigma.mop.*;
import au.edu.adelaide.kahn.pn.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.*;
/**
 * An implementation of the Enigma MOP using Java Dynamic Proxies.
 *
 * @author Darren Webb
 * @version $Id: ComputationMetaFactory.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class ComputationMetaFactory extends DynamicMetaFactory
{
	public Map<String, Object> specMap = new HashMap<>();
//	public Map<Integer, Object> specMap;
	public ComputationMetaFactory()
	{
		super();
	}

	public ComputationMetaFactory(String viewName)
	{
		super(viewName);
	}

	public Object newMetaLevelFor(Object baseObject,MethodReifier reifier)
	{
		Set interfaceSet = new HashSet();
		Toolkit.getAllInheritedInterfaces(interfaceSet,baseObject.getClass());
//		Toolkit.getAllInheritedInterfaces(baseObject.getClass());
		
		interfaceSet.add(MethodReifier.class);
		interfaceSet.add(MetaObject.class);
		interfaceSet.add(MetaLevelInterface.class);
		interfaceSet.add(Computation.class);
		Class[] interfaces = Toolkit.setToClassArray(interfaceSet);
		Object baseObjectProxy = Proxy.newProxyInstance(baseObject.getClass().getClassLoader(),
                                                    interfaces,reifier);
//    System.out.println("INFO: added metalevel for baseobject "+baseObject );
		
		/*
		 * It is used for specification provenance collection purposes.
		 */
//		addedEntitiesToWF(baseObject);
		return baseObjectProxy;
	}

	private void addedEntitiesToWF(Object baseObject) {
		String output = " ";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/dd/MM hh:mm:ss.SSS");
		output = "***" + simpleDateFormat.format(new Date());
    
    if(!specMap.containsValue(baseObject)){
    specMap.put(baseObject.getClass().getSimpleName(), baseObject);
    System.out.println(output+" Entity : "+baseObject.getClass().getSimpleName()+ "  ---  " + baseObject);
	}
	}

  public MetaObject newDefaultTransmissionHandler(MetaObject metaObject)
  {
//    System.out.println("INFO: adding transmission handler to metaObject");
    return super.newDefaultTransmissionHandler(metaObject);
  }

	public MetaObject newMetaObject(Object baseObject,MetaExecutionHandler defaultHandler)
	{
		// create a new Computation
//    System.out.println("INFO: creating new computation metaobject,  baseObject " +baseObject + "   "+ defaultHandler);
	return new ComputationImpl(baseObject,defaultHandler);

	}
}
