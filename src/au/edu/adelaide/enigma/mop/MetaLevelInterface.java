package au.edu.adelaide.enigma.mop;

/**
 * An interface to enable the baselevel to communicate with the
 * metalevel.  Enables the baselevel and other metalevel objects 
 * to obtain references to specific metalevel components.
 *
 * @author Darren Webb
 * @version $Id: MetaLevelInterface.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaLevelInterface extends MetaLevelObject
{
	/**
	 * Get the method reifier for this reference.  The method reifier
	 * is likely to be different for each reference.  (in the perfect
	 * implementation, it would be different for all references.  This
	 * method returns the method reifier of the default view.
	 */
	public MethodReifier getThisMethodReifier();

	/**
	 * Get the method reifier for this reference.  The method reifier
	 * is likely to be different for each reference.  (in the perfect
	 * implementation, it would be different for all references.  This
	 * method returns the method reifier of the specified view.
	 */
	public MethodReifier getThisMethodReifier(String viewName);

	/**
	 * Get the target metaobject of this reference.  This gets a
	 * reference to the metaobject for the baseobject that this
	 * reference represents.  This method returns the metaobject of
	 * the default view.
	 */
	public MetaObject getTargetMetaObject();

	/**
	 * Get the target metaobject of this reference.  This gets a
	 * reference to the metaobject for the baseobject that this
	 * reference represents.  This method returns the metaobject of
	 * the specified view.
	 */
	public MetaObject getTargetMetaObject(String viewName);
}
