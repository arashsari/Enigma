package au.edu.adelaide.pagis.computation;

import au.edu.adelaide.kahn.pn.AbstractProcess;
import au.edu.adelaide.kahn.pn.Process;
import au.edu.adelaide.enigma.mop.MetaLevelObject;
import au.edu.adelaide.enigma.mop.MetaObject;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.enigma.mop.MetaExceptionResult;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.dynamic.EndPointMetaObject;
import au.edu.adelaide.enigma.mop.MetaExecutionHandler;

import au.edu.adelaide.pna.halfchannel.HalfChannelInputPortImpl;
import au.edu.adelaide.pna.halfchannel.HalfChannelOutputPortImpl;
import au.edu.adelaide.pna.system.*;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ComputationImpl extends EndPointMetaObject implements Computation {
	private Map directory;

	public ComputationImpl(Object baseObject,
			MetaExecutionHandler defaultHandler) {
		this(new HashMap(), defaultHandler);
		setBaseObject("process", baseObject);

		Map newProperties = new HashMap();
		newProperties.put("scheduler.gui.properties.class",
				"au.edu.adelaide.gui.meta.ComputationSchedulerPanel");
		newProperties.put("scheduler.name", "Scheduler");
		newProperties.put("reconfiguration.gui.properties.class",
				"au.edu.adelaide.gui.meta.ComputationReconfigurationPanel");
		newProperties.put("reconfiguration.name", "Reconfiguration");
		newProperties.put("members.gui.properties.class",
				"au.edu.adelaide.gui.meta.ComputationMembersPanel");
		newProperties.put("members.name", "Members");
		updateMetaInformation(newProperties);
	}

	public ComputationImpl(Map directory, MetaExecutionHandler defaultHandler) {
		super(null, defaultHandler);
		this.directory = directory;
	}

	public void setBaseObject(String name, Object baseObject) {
		directory.put(name, baseObject);
	}

	public Object getBaseObject(String name) {
		return directory.get(name);
	}

	public Map getDirectory() {
		return directory;
	}

	public MetaResult invoke(TargetAction target,
			MetaMethodInvocation invocation) {

		   MetaResult result = invocation.invoke(target.getTarget(this));
		    return result;
	}

	
	public MetaResult handleMethodInvocation(MetaMethodInvocation invocation) {
		return invocation.invoke(this);
	}

	public void setStrategy(ProcessThreadStrategy strategy) {
		synchronized (this) {
			Map currentProperties = getMetaInformation();

			Map newProperties = new HashMap();
			newProperties.put("scheduler.type", strategy.getName());
			newProperties.put("scheduler.description",
					strategy.getDescription());
			newProperties.put("scheduler.class", strategy.getClass());
			updateMetaInformation(newProperties);

			ProcessThread thread = (ProcessThread) currentProperties
					.get("pagis.computation.thread");
			thread.setStrategy(strategy);
		}
	}

	public void apply(Reconfiguration reconfiguration) {
		synchronized (this) {
			Map currentProperties = getMetaInformation();

			ProcessThread thread = (ProcessThread) currentProperties
					.get("pagis.computation.thread");
			thread.pause();
			Process process = (Process) getBaseObject("process");
			reconfiguration.apply(process);
			thread.resume();
		}
	}
	
	
	// //////////////////////////////////////////////////////////////////
		// print out the list of information inside the Directory map, including
		// process objects
		// that are reified by "ComputationMetfactory.newMetaObject".
		// Specifically, it contains process (as base object)
		// Inputport and outputport. Data the pass through network is captured
		// by "((MetaValueResult)result).getValue())".

//		private void comProvenance(TargetAction target,
//				MetaMethodInvocation invocation, MetaResult result) {
	//
//			
//			//		Map cp = getMetaInformation();
//					Map cp = getDirectory();
//					Set<String> keySet = cp.keySet();
//					for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
//						String key = (String) iterator.next();
//						if (cp.containsKey(key)) {
////							System.out.println("{{{{{{{{{{{{{{{ ---key  ---- " + key+"  value:  "+ cp.get(key));
	//
//							
////													if(key.equalsIgnoreCase("inputport0")){
////							System.out.println(" inputport0: " + ((HalfChannelInputPortImpl)cp.get("inputport0")).get());
////						}
////							if(key.equalsIgnoreCase("outputport0")){
////								System.out.println(" outputport0: " + ((HalfChannelOutputPortImpl)cp.get("outputport0")).getAll());
////							}
//						}
//					}
	//
//				
//					
////					if(!cp.get("process").equals(null)){
////						AbstractProcess p = ((AbstractProcess)cp.get("process"));
////						System.out.println(" Process: " + p + " process input data "+p.getData());
////					}
////					
//					// just method start with get, in order to deal with the data through
//					// the network.
//					if (invocation.getMethodName().startsWith("get") || invocation.getMethodName().startsWith("put")) {
////						Object res= ((MetaValueResult) result).getValue();
//					if(!((MetaValueResult) result).equals(null)){
//						System.out.println(" Process: " + cp.get("process")+
//								"  ----metavalue---:  traget : "
//								+ target.getTargetName() + "   method : "
//								+ invocation.getMethodName() + "  result  :  "
//								+ ((MetaValueResult) result).getValue());
//						}
//					}
	//
//					
//					if (invocation.getMethodName().startsWith("fire")) {
//						if(!((MetaValueResult) result).equals(null)){
//						System.out.println("----metavalue--- fire :  traget : "
//								+ target.getTargetName() + "   method : "
//								+ invocation.getMethodName() + "  result  :  "
//								+ ((MetaValueResult) result).getValue());
//						}
//					}	
	//
//		}
	// ////////////////////////////////////////
}
