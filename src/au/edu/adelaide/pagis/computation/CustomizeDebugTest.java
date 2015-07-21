package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.pna.processes.Identity;
import au.edu.adelaide.enigma.mop.MetaFactory;
import au.edu.adelaide.enigma.dynamic.DynamicMetaFactory;
import au.edu.adelaide.enigma.debug.TraceExecutionHandlerClass;
import au.edu.adelaide.kahn.pn.Process;

/**
 * Regression test for metaobject creation using the factory.
 *
 * @author Darren Webb
 * @version $Id: CustomizeDebugTest.java,v 1.2 2005-08-02 11:54:55 cvsproject Exp $
 */
public class CustomizeDebugTest
{
	public static void main(String args[])
	{
		MetaFactory factory = new ComputationMetaFactory("computation");
//		MetaFactory factory = new DynamicMetaFactory();
		factory.addMetaLevelCustomization(Object.class,new TraceExecutionHandlerClass());

		Process foo = (Process)factory.newMetaLevelFor(new Identity());
		System.out.println(foo.getInputPort(0));
	}
}
