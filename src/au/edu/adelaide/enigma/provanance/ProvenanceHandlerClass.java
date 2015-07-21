package au.edu.adelaide.enigma.provanance;

import java.util.Map;

import au.edu.adelaide.enigma.mop.MetaHandlerClass;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MetaObject;

public class ProvenanceHandlerClass implements MetaHandlerClass
	{
		public static final String PROPERTY_NAME = "metabehavior.provenance";
		public static final String NAME = "Provenance Behavior";
		public static final String DESC = "Displays provenance messages to console.";
		public static final String GUI_PROPERTIES = "au.edu.adelaide.enigma.gui.ProvenanceBehaviorPanel";
		public static final String GUI_DECORATOR = "";

		public void customizeMetaObject(MetaLevelInterface metalevelInterface)
		{
			MetaObject metaObject = metalevelInterface.getTargetMetaObject();
			ProvenanceHandler handler = new ProvenanceHandler(metaObject);
			metaObject.addMetaExecutionHandler(handler);
		}

		public void customizeMetaObject(MetaLevelInterface metalevelInterface,
										Map metaInformation)
		{
			customizeMetaObject(metalevelInterface);
		}
	}
