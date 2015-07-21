package au.edu.adelaide.enigma.debug;

import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MetaHandlerClass;

import java.util.Map;

/**
 *
 *
 * @author Darren Webb
 * @version $Id: TraceExecutionHandlerClass.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class TraceExecutionHandlerClass implements MetaHandlerClass
{
	public static final String PROPERTY_NAME = "metabehavior.trace";
	public static final String NAME = "Trace Behavior";
	public static final String DESC = "Displays trace messages to console.";
	public static final String GUI_PROPERTIES = "au.edu.adelaide.enigma.gui.TraceBehaviorPanel";
	public static final String GUI_DECORATOR = "";

	public void customizeMetaObject(MetaLevelInterface metalevelInterface)
	{
		MetaObject metaObject = metalevelInterface.getTargetMetaObject();
		TraceExecutionHandler handler = new TraceExecutionHandler(metaObject);
		metaObject.addMetaExecutionHandler(handler);
	}

	public void customizeMetaObject(MetaLevelInterface metalevelInterface,
									Map metaInformation)
	{
		customizeMetaObject(metalevelInterface);
	}
}
