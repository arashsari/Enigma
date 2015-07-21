package au.edu.adelaide.enigma.debug;

import au.edu.adelaide.enigma.mop.AbstractMetaExecutionHandler;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;
import au.edu.adelaide.enigma.provanance.ProvenanceCollector2;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.HashMap;

/**
 * A simple trace debugger concern.  Displays a message on method entry and exit.
 *
 * @author Darren Webb
 * @version $Id: TraceExecutionHandler.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class TraceExecutionHandler extends AbstractMetaExecutionHandler
{
	private static int instance = 0;
	private int id = instance++;

	public TraceExecutionHandler(MetaObject metaObject)
	{
		Map properties = new HashMap();
		addProperty(properties,"name",TraceExecutionHandlerClass.NAME);
		addProperty(properties,"description",TraceExecutionHandlerClass.DESC);
		addProperty(properties,"class",getClass().getName());
		addProperty(properties,"gui.properties.class",TraceExecutionHandlerClass.GUI_PROPERTIES);
		addProperty(properties,"gui.decorator.class",TraceExecutionHandlerClass.GUI_DECORATOR);
		metaObject.updateMetaInformation(properties);
	}

	protected void addProperty(Map properties,String property,Object value)
	{
		properties.put(TraceExecutionHandlerClass.PROPERTY_NAME+"."+id+"."+property,value);
	}

	/**
	 * Print a debug message before and after invocation.
	 */
	public MetaResult handleExecution(Object baseObject,
									  MetaMethodInvocation invocation)
	{
		String methodName = invocation.getMethod().getName();
		System.out.println("Debug: entering "+methodName);

		MetaResult result = getNextExecutionHandler().handleExecution(baseObject,invocation);

		if (result instanceof MetaExceptionResult)
		{
			System.out.println("Debug: "+methodName+" threw exception");
		}
		else
		{
			System.out.println("Debug: exiting "+methodName);
		}

		return result;
	}
}
