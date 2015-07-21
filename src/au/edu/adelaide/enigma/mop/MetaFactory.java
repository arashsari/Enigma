package au.edu.adelaide.enigma.mop;

import java.util.Map;

/**
 * The interface used to distinguish objects at the metalevel.
 *
 * @author Darren Webb
 * @version $Id: MetaFactory.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public interface MetaFactory extends MetaLevelObject
{
	public String getViewName();

	/**
	 * Customize all future instances of the baseClass with the provided
	 * customization class.  The metahandler class is invoked for each
	 * instance of the baseclass or subclass of the baseclass.
	 */
	public void addMetaLevelCustomization(Class baseClass,
										  MetaHandlerClass customization);

	public MetaObject newMetaObject(Object baseObject,MetaExecutionHandler defaultHandler);

	public MetaMarshalHandler newDefaultMarshalHandler(MetaObject metaObject);

	public MetaObject newDefaultTransmissionHandler(MetaObject metaObject);

	public MetaExecutionHandler newDefaultExecutionHandler();

	/**
	 * Create a new metaobject for the given baseobject.  All necessary
	 * metalevel objects are created, and a new reference for the baseobject
	 * is returned.
	 */
	public abstract MetaObject newMetaObjectFor(Object baseObject);

	public abstract MetaObject newMetaObjectFor(Object baseObject,
												MetaExecutionHandler defaultExecution);

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public abstract Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject);

	/**
	 * Complete the metalevel for the provided metaobject
	 */
	public abstract Object newMetaLevelFor(Object baseObject,MethodReifier reifier);

	public abstract Object newMetaLevelFor(Object baseObject,MetaObject metaObject);

	public abstract Object newMetaLevelFor(String viewName,Object baseObject,MetaObject metaObject,
										   MetaMarshalHandler defaultMarshal);

	public abstract Object newMetaLevelFor(Object baseObject,MetaObject metaObject,
										   MetaMarshalHandler defaultMarshal);

	/**
	 * Create a new metaobject for the given baseobject.  All necessary
	 * metalevel objects are created, and a new reference for the baseobject
	 * is returned.
	 */
	public abstract Object newMetaLevelFor(String viewName,Object baseObject);

	/**
	 * Create a new metaobject for the given baseobject.  All necessary
	 * metalevel objects are created, and a new reference for the baseobject
	 * is returned.
	 */
	public abstract Object newMetaLevelFor(Object baseObject);

	public abstract void customize(Object baseObject);
}
