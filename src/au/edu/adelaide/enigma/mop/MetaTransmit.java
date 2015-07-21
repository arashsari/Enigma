package au.edu.adelaide.enigma.mop;

import java.util.Map;

/**
 *
 * @author Darren Webb
 * @version $Id: MetaTransmit.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaTransmit extends MetaLevelObject
{
	/**
	 * Handle the execution of a method invocation on the baseobject.  The
	 * metaobject will invoke the provided execution handler each time
	 * the metaobject receives a method invocation.
	 */
	public void addMetaTransmissionHandler(MetaTransmissionHandler handler);
}
