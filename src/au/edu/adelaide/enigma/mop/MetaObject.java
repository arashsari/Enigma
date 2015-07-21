package au.edu.adelaide.enigma.mop;

import java.util.Map;

/**
 * Reification of a baseobject.  A MetaObject reifies (or makes explicit)
 * metainformation and metabehaviour of an object at the baselevel.
 *
 * @author Darren Webb
 * @version $Id: MetaObject.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaObject extends MetaLevelObject, MetaTransmit, MetaExecute
{
	/**
	 * Get a table of metainformation for this metaobject.  This does not 
	 * include metainformation obtainable from the Java Reflection API.
	 * Metainformation can be user-defined, used, for example, to trigger
	 * change in interaction and execution behaviours.
	 */
	public Map getMetaInformation();

	/**
	 * Update the metainformation for this metaobject.  The entries provided 
	 * override existing metaproperties with the same key.
	 */
	public void updateMetaInformation(Map metaInformation);

	/**
	 * Handle the update of metainformation.  The metaobject will invoke the 
	 * provided update handler each time the metainformation is updated.
	 */
	public void addMetaInformationHandler(MetaInformationHandler handler);

	/**
	 * Handle a method invocation on the baseobject.  The metaobject invokes 
	 * the provided invocation metaobject on the baseobject.
	 */
	public MetaResult handleMethodInvocation(MetaMethodInvocation invocation);
}
