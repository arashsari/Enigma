package au.edu.adelaide.enigma.provanance;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import au.edu.adelaide.enigma.mop.AbstractMetaExecutionHandler;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaResult;

public class ProvenanceHandler extends AbstractMetaExecutionHandler
	{
		private static int instance = 0;
		private int id = instance++;

		public ProvenanceHandler(MetaObject metaObject)
		{
			Map properties = new HashMap();
			addProperty(properties,"name",ProvenanceHandlerClass.NAME);
			addProperty(properties,"description",ProvenanceHandlerClass.DESC);
			addProperty(properties,"class",getClass().getName());
			addProperty(properties,"gui.properties.class",ProvenanceHandlerClass.GUI_PROPERTIES);
			addProperty(properties,"gui.decorator.class",ProvenanceHandlerClass.GUI_DECORATOR);
			metaObject.updateMetaInformation(properties);
		}

		protected void addProperty(Map properties,String property,Object value)
		{
			properties.put(ProvenanceHandlerClass.PROPERTY_NAME+"."+id+"."+property,value);
		}

		/**
		 * Print a debug message before and after invocation.
		 */
		public MetaResult handleExecution(Object baseObject,
										  MetaMethodInvocation invocation)
		{
			String methodName = invocation.getMethod().getName();
//			System.out.println("Provenance: entering "+methodName);

			MetaResult result = getNextExecutionHandler().handleExecution(baseObject,invocation);

			if (result instanceof MetaExceptionResult)
			{
				System.out.println("Provenance: "+methodName+" threw exception");
			}
			else
			{
//				System.out.println("Provenance: exiting "+methodName);
				InformationServiceQuantizer isq = InformationServiceQuantizer
						.getInstance();
	//
				if (!(isq.granularityLevel == 9)) 
				{
					if((isq.granularityLevel == 2) || (isq.granularityLevel == 0) || (isq.granularityLevel == 1)) {
					MultipleGrainedPC CPC =MultipleGrainedPC.getInstance();
					try {
						CPC.methodInvocationFiltering(invocation, baseObject);
					} catch (IllegalAccessException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					}
//					else if((isq.granularityLevel == 1)) {
//					
//					ProvenanceCollector2 mip =ProvenanceCollector2.getInstance();
//					try {
//						mip.methodInvocationFiltering(invocation, baseObject);
//					} catch (IllegalAccessException | InvocationTargetException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					}
				}
			}

			return result;
		}
	}
