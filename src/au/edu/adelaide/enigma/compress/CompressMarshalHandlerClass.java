package au.edu.adelaide.enigma.compress;

import au.edu.adelaide.enigma.mop.MetaHandlerClass;
import au.edu.adelaide.enigma.mop.MetaLevelInterface;
import au.edu.adelaide.enigma.mop.MethodReifier;

import java.util.Map;

/**
 * Template for customizing the metalevel with new compression
 * message semantics.  This handler class customizes the metalevel
 * by adding a compression handler to the method reifier.
 *
 * @author Darren Webb
 * @version $Id: CompressMarshalHandlerClass.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class CompressMarshalHandlerClass implements MetaHandlerClass
{
	public void customizeMetaObject(MetaLevelInterface metalevelInterface)
	{
		MethodReifier reifier = metalevelInterface.getThisMethodReifier();
		reifier.addMetaMarshalHandler(new CompressMarshalHandler());
	}

	public void customizeMetaObject(MetaLevelInterface metalevelInterface,
									Map metaInformation)
	{
		customizeMetaObject(metalevelInterface);
	}
}
