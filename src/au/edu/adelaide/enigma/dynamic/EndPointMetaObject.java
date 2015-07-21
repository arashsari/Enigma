package au.edu.adelaide.enigma.dynamic;

import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaInformationHandler;
import au.edu.adelaide.enigma.mop.MetaInformationEvent;
import au.edu.adelaide.enigma.mop.MetaExecutionHandler;
import au.edu.adelaide.enigma.mop.MetaTransmissionHandler;
import au.edu.adelaide.enigma.mop.MetaResult;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Reification of a baseobject.  A MetaObject reifies (or makes explicit)
 * metainformation and metabehaviour of an object at the baselevel.  This
 * implementation of the metaobject is part of the dynamic proxy 
 * implementation of the Enigma MOP.
 *
 * @author Darren Webb
 * @version $Id: EndPointMetaObject.java,v 1.1.1.1 2005-07-14 13:34:48 cvsproject Exp $
 */
public class EndPointMetaObject implements MetaObject
{
	private Object baseObject;
	private Map metaInformation = new HashMap();
	private List metaInformationHandlers = new LinkedList();
	private MetaExecutionHandler metaExecutionHandler;

	public EndPointMetaObject(Object baseObject)
	{
		this.baseObject = baseObject;
	}

	public EndPointMetaObject(Object baseObject,MetaExecutionHandler defaultHandler)
	{
		this.baseObject = baseObject;
		this.metaExecutionHandler = defaultHandler;
	}

	/**
	 * Get a table of metainformation for this metaobject.  This does not 
	 * include metainformation obtainable from the Java Reflection API.
	 * Metainformation can be user-defined, used, for example, to trigger
	 * change in transmission and execution behaviours.
	 */
	public Map getMetaInformation()
	{
		/*
		// return a copy of the metainformation
		synchronized(metaInformation)
		{
			try
			{
				PipedOutputStream pos = new PipedOutputStream();
				PipedInputStream pis = new PipedInputStream(pos);

				ObjectOutputStream oos = new ObjectOutputStream(pos);
				oos.writeObject(metaInformation);
				oos.flush();

				ObjectInputStream ois = new ObjectInputStream(pis);
				Map copy = (Map)ois.readObject();

				oos.close();
				ois.close();

				return copy;
			}
			catch (Exception e)
			{
				return new HashMap();
			}
		}
		*/
		return Collections.unmodifiableMap(metaInformation);
	}

	/**
	 * Update the metainformation for this metaobject.  The entries provided 
	 * override existing metaproperties with the same key.
	 */
	public void updateMetaInformation(Map newMetaInformation)
	{
		synchronized(metaInformation)
		{
			metaInformation.putAll(newMetaInformation);
		}

		fireMetaInformationHandlers();
	}

	/**
	 * Handle the update of metainformation.  The metaobject will invoke the 
	 * provided update handler each time the metainformation is updated.
	 */
	public void addMetaInformationHandler(MetaInformationHandler handler)
	{
		metaInformationHandlers.add(handler);
	}

	public void fireMetaInformationHandlers()
	{
		MetaInformationEvent e = new MetaInformationEvent();
		Iterator i = metaInformationHandlers.iterator();
		while (i.hasNext())
		{
			((MetaInformationHandler)i).metaInformationChanged(e);
		}
	}

	/**
	 * Handle a method invocation on the baseobject.  The metaobject invokes 
	 * the provided invocation metaobject on the baseobject.
	 */
	public MetaResult handleMethodInvocation(MetaMethodInvocation invocation)
	{
		return metaExecutionHandler.handleExecution(baseObject,invocation);
	}

	/**
	 * Handle the execution of a method invocation on the baseobject.  The
	 * metaobject will invoke the provided execution handler each time
	 * the metaobject receives a method invocation.
	 */
	public void addMetaExecutionHandler(MetaExecutionHandler handler)
	{
		handler.setNextExecutionHandler(metaExecutionHandler);
		metaExecutionHandler = handler;
	}

	/**
	 * Handle the execution of a method invocation on the baseobject.  The
	 * metaobject will invoke the provided execution handler each time
	 * the metaobject receives a method invocation.
	 */
	public void addMetaTransmissionHandler(MetaTransmissionHandler handler)
	{
	}
}
