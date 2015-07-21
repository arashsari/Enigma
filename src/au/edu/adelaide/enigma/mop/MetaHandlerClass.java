package au.edu.adelaide.enigma.mop;

import java.util.Map;

/**
 * The metahandler class provides defines a pattern for populating 
 * the metalevel.
 *
 * @author Darren Webb
 * @version $Id: MetaHandlerClass.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaHandlerClass extends MetaLevelObject
{
	public void customizeMetaObject(MetaLevelInterface metalevelInterface);
	public void customizeMetaObject(MetaLevelInterface metalevelInterface,
									Map metaInformation);
}
