package au.edu.adelaide.enigma.provanance;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.openprovenance.model.Account;
import org.openprovenance.model.Agent;
import org.openprovenance.model.Annotation;
import org.openprovenance.model.Artifact;
import org.openprovenance.model.Artifacts;
import org.openprovenance.model.EmbeddedAnnotation;
import org.openprovenance.model.OPMFactory;
import org.openprovenance.model.OPMGraph;
import org.openprovenance.model.OPMSerialiser;
import org.openprovenance.model.Overlaps;
import org.openprovenance.model.Process;
import org.openprovenance.model.Used;
import org.openprovenance.model.Value;
import org.openprovenance.model.WasControlledBy;
import org.openprovenance.model.WasDerivedFrom;
import org.openprovenance.model.WasGeneratedBy;
import org.openprovenance.model.WasTriggeredBy;

import au.edu.adelaide.enigma.dynamic.SerializableMethodInvocation;
import au.edu.adelaide.enigma.dynamic.SerializableMethodInvocation.FixMethodWrapper;
import au.edu.adelaide.enigma.mop.MetaMethodInvocation;
import au.edu.adelaide.enigma.mop.MetaResult;
import au.edu.adelaide.enigma.mop.MetaValueResult;
import au.edu.adelaide.pna.data.IntToken;
import au.edu.adelaide.pna.data.Token;
import au.edu.adelaide.pna.halfchannel.HalfChannelInputPort;
import au.edu.adelaide.pna.halfchannel.HalfChannelOutputPort;
import au.edu.adelaide.kahn.pn.AbstractProcess;
import au.edu.adelaide.pna.system.DataFlowStrategy;
import au.edu.adelaide.pna.system.ProcessThreadImpl;

public class MultipleGrainedPC {

	private static MultipleGrainedPC instance = null;

	private MultipleGrainedPC() {
	}

	public static synchronized MultipleGrainedPC getInstance() {
		if (instance == null) {
			instance = new MultipleGrainedPC();
		}
		return instance;
	}

	HashMap processesMap = new HashMap();
	HashMap artifactMap = new HashMap();
	HashMap agentMap = new HashMap();
	HashMap accountMap = new HashMap();
	HashMap iartifactMap = new HashMap();
	
	int processCounter = 0;
	int artifactCounter = 0;
	int usedCounter = 0;
	int wasDerivedFromCounter = 0;
	int wasgeneratedByCounter = 0;
	int wasTriggeredByCounter = 0;
	int wasControlledByCounter = 0;
	int agentCounter = 0;
	int accountCounter = 0;

	int artifactAnnotateId = 0;
	int wgbAnnotateId = 0;
	int usedAnnoateId = 0;
	int wdfAnnotateId = 0;
	int wtbAnnotateId = 0;

	
	int IwasDerivedFromCounter =0;
	int IwdfAnnotateId = 0;
	
	int IartifactCounter = 0;
	int IartifactAnnotateId = 0;
	
	// added for opm
	// OPMFactory oFactory;
	// Account account;
	Collection<Account> accountCollection;
	Collection<Account> accountCollection2;
	Collection<Account> accountCollection3;

	Collection<Account> accountCollectionFinal;

	Vector<Process> processes = new Vector<Process>();
	Vector<Agent> agents = new Vector<Agent>();
	Vector<Artifact> artifactes = new Vector<Artifact>();
	Vector<Object> objects = new Vector<Object>();
	EmbeddedAnnotation ann = new EmbeddedAnnotation();
	Vector<Artifact> iartifactes = new Vector<Artifact>();
	
	Vector<Account> accounts = new Vector<Account>();
	OPMFactory oFactory = new OPMFactory();

	InformationServiceQuantizer isq = InformationServiceQuantizer.getInstance();
	Account account = new Account();
	Account account2 = new Account();
	Account account3 = new Account();

	// private FixMethodWrapper method;
	private Object[] args;

	// Logger log = Logger.getLogger(MIProvenance.class);

	// Method and Invocation are the same.
	public void methodInvocationFiltering(MetaMethodInvocation invocation,
			Object baseObject) throws IllegalAccessException,
			InvocationTargetException {
		account.setId("account1");// coarse
		accountCollection = new ArrayList<Account>();
		accountCollection.add(account);

		// accountCollection.add(account3);

		account2.setId("account2");// medium
		accountCollection2 = new ArrayList<Account>();
		accountCollection2.add(account2);

		account3.setId("account3");
		accountCollection3 = new ArrayList<Account>();
		accountCollection3.add(account3);

//		 if (invocation.getMethod().getName() != "invoke") {
//		 if (invocation.getMethod().getName() != "handleMethodInvocation") {
//		 if (invocation.getMethod().getName() != "hashCode") {
//		 System.out.println("  " + invocation.getMethod().getName()
//		 + "   == caller: "
//		 + baseObject.getClass().getName());
//		 }
//		 }
//		 }

		// =====================================================================================

		if (!(isq.getGranularityLevel() == 9)) {

			Agent findedAgent = null;
			// New Process - RegProcess
			if ((invocation.getMethod().getName()
					.equalsIgnoreCase("newInputPort"))
					|| (invocation.getMethod().getName()
							.equalsIgnoreCase("newOutputPort"))) {
				Object[] argg = invocation.getArguments();
				String output = "  Process: " + argg[0] + "  portNo. :"
						+ argg[1];
				Process findedProcess = findProcessOPMXML(argg[0]);

				findedAgent = findAgentOPMXML((Object) (new ProcessThreadImpl(
						(au.edu.adelaide.kahn.pn.Process) argg[0],
						new DataFlowStrategy())));
				// createWCB(baseObject, findedAgent);
			}

			if ((invocation.getMethod().getName().equalsIgnoreCase("init"))) {
				// WasControlledBy
				// createWCB(baseObject);
				createWCB(baseObject, (Object) (new ProcessThreadImpl(
						(au.edu.adelaide.kahn.pn.Process) baseObject,
						new DataFlowStrategy())));
				//
			}
			
			 if((invocation.getMethod().getName().equalsIgnoreCase("put"))) {
				 Object[] para = invocation.getArguments();		
				 
//				 System.out.println("PUT method : Produced token inside Process and transfered into HalfChannelOutputPort : "+  para[0].toString());
				 // this can be used in intermediate data 
				 }
			
			 if((invocation.getMethod().getName().equalsIgnoreCase("get"))) {
	// not working		
//				 HalfChannelInputPort hi = (HalfChannelInputPort) baseObject;
//				 System.out.println("kkkkkkkkk "+ hi.getConnection().getInputPort().getClass().getSimpleName().);
//				 Object[] res = (Object[]) 
//				MetaResult mr= invocation.invoke(baseObject);//getMethod().getDefaultValue();			
//				 System.out.println("MetaResult of get invoke "+  mr.toString());

				 }
			 if((invocation.getMethod().getName().equalsIgnoreCase("isFireable"))) {
				 
				 Object[] paraInfireable = invocation.getArguments();	
				 
				 System.out.println("paraInfireable : "+  paraInfireable[0].toString());
				 
				 // this can be used in intermediate data and USED
				 // CAn be used like USED below for implementation
				 
				 used(invocation, baseObject);
				 }


			if ((invocation.getMethod().getName().startsWith("fire"))) {
				if (!(((AbstractProcess) baseObject).getPreviousProcess() == null)) {
					// WasTriggerBy
					createWTB(baseObject);
				}

				IWDF(invocation, baseObject);
//				used(invocation, baseObject);
// used dependecy can be captured in isfireable
			}
			if (invocation.getMethod().getName().startsWith("getData")) {
				// / wasGeneratedBy
				wasGeneratedBy(invocation, baseObject);

				// wasDeriviedFrom
				wasDeriviedFrom(invocation, baseObject);
			}
			
		}else if (isq.getGranularityLevel() == 9){
			System.out.println("No provenance collected");
		}
	}


	//-----------------------------------------------
	private void used(MetaMethodInvocation invocation, Object target) {
		if (!(isq.granularityLevel == 9)) {


			Object chanl = new Object();
			AbstractProcess ab = (AbstractProcess) target;
			Map res = ab.getData();
			Set<String> usedkeySet = res.keySet();

			for (Iterator iterator = usedkeySet.iterator(); iterator.hasNext();) {
				String WGFkey = (String) iterator.next();

				if (WGFkey.equalsIgnoreCase("InputValue")) {
					Object inval = res.get(WGFkey);
					// createUSED(ab, inval);
					// opm
					createUSED(ab, inval, chanl, 00);
				}
				// if (WGFkey.contains("InputValue1")) {
				if (WGFkey.equalsIgnoreCase("InputValue1")) {
					Object inval = res.get(WGFkey);
					// createUSED(ab, inval);
					createUSED(ab, inval, chanl, 10);
				}
				// if (WGFkey.contains("InputValue2")) {
				if (WGFkey.equalsIgnoreCase("InputValue2")) {
					Object inval2 = res.get(WGFkey);
					// createUSED(ab, inval2);
					createUSED(ab, inval2, chanl, 01);
				}
			}
		}
	}

	private void createUSED(Object callee, Object inval1, Object channel,
			int portIndex) {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}

		if ((isq.getGranularityLevel() == 2)) {
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token) inval1,false);
			this.usedCounter++;
			String usedId = "USED_" + this.usedCounter;
			Used u1 = oFactory.newUsed(usedId, findedProcess,
					oFactory.newRole("in1"), findedArtifact, accountColtemp);
			this.objects.add(u1);
		} else if ((isq.getGranularityLevel() == 0)
				|| (isq.granularityLevel == 1)) {
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token) inval1, false);
			this.usedCounter++;
			String usedId = "USED_" + this.usedCounter;
			Used u1 = oFactory.newUsed(usedId, findedProcess,
					oFactory.newRole("in1"), findedArtifact, accountColtemp);
			Value portAnnotate = new Value();
			portAnnotate.setId("port index");
			// portAnnotate.setContent(portIndex);
			String pindex = null;
			switch (portIndex) {
			case 00:
				pindex = "0 => the only input port";
				break;
			case 10:
				pindex = "1 => there is two input ports";
				break;
			case 01:
				pindex = "2 => there is two input ports";
				break;
			}
			portAnnotate.setEncoding(pindex);
			Value channelAnnotate = new Value();
			channelAnnotate.setId("channel");
			EmbeddedAnnotation embAnno = new EmbeddedAnnotation();
			// embAnno.setId("USED - Port and Channel");

			oFactory.addAnnotation(embAnno, channelAnnotate);
			oFactory.addAnnotation(embAnno, portAnnotate);
			this.usedAnnoateId++;
			String usedAnn = "usedAnnotation_" + this.usedAnnoateId;
			oFactory.addAnnotation(u1, oFactory.newEmbeddedAnnotation(usedAnn,
					"USED - Port and Channel", embAnno, accountColtemp));
			this.objects.add(u1);
		}

	}

	// ------------------------------------------------------------------------------------------------
	private void wasGeneratedBy(MetaMethodInvocation invocation, Object target) {

		if (!(isq.granularityLevel == 9)) {

			// System.out.println("WGB" + target);
			AbstractProcess ab = (AbstractProcess) target;
			Map res = ab.getData();
			Set<String> WDFkeySet = res.keySet();

			for (Iterator iterator = WDFkeySet.iterator(); iterator.hasNext();) {
				String WGFkey = (String) iterator.next();
				Object chanl = new Object();
				if (WGFkey.equalsIgnoreCase("OutputValue")) {
					Object outval = res.get(WGFkey);

					// // opm was generated by
					createWGB(ab, outval, chanl, 00);

				}

				if (WGFkey.equalsIgnoreCase("OutputValue1")) {
					Object outval2 = res.get(WGFkey);
					// // opm was generated by
					createWGB(ab, outval2, chanl, 10);

				}
				if (WGFkey.equalsIgnoreCase("OutputValue2")) {
					Object outval2 = res.get(WGFkey);
					// createWGB(ab, outval2);
					// // opm was generated by
					createWGB(ab, outval2, chanl, 01);
				}
			}
		}
	}

	private void createWGB(Object callee, Object token, Object channel,
			int portIndex) {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}

		if ((isq.getGranularityLevel() == 2)) {
			// find process
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token) token, false);
			this.wasgeneratedByCounter++;
			String wasgeneratedById = "WGB_" + this.wasgeneratedByCounter;
			WasGeneratedBy wg1 = oFactory.newWasGeneratedBy(wasgeneratedById,
					findedArtifact, oFactory.newRole("out"), findedProcess,
					accountColtemp);
			this.objects.add(wg1);
			// annotation
		} else if ((isq.getGranularityLevel() == 0)
				|| (isq.granularityLevel == 1)) {
			// find process
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token) token, false);
			this.wasgeneratedByCounter++;
			String wasgeneratedById = "WGB_" + this.wasgeneratedByCounter;
			WasGeneratedBy wg1 = oFactory.newWasGeneratedBy(wasgeneratedById,
					findedArtifact, oFactory.newRole("out"), findedProcess,
					accountColtemp);

			Value portAnnotate = new Value();
			portAnnotate.setId("port index");
			portAnnotate.setContent(portIndex);
			String pindex = null;
			switch (portIndex) {
			case 00:
				pindex = "0 => the only input port";
				break;
			case 10:
				pindex = "1 => there is two input ports";
				break;
			case 01:
				pindex = "2 => there is two input ports";
				break;
			}
			portAnnotate.setEncoding(pindex);
			Value channelAnnotate = new Value();
			channelAnnotate.setId("channel");
			channelAnnotate.setContent(channel.getClass().getSimpleName());
			channelAnnotate.setEncoding(channel.toString());
			EmbeddedAnnotation embAnno = new EmbeddedAnnotation();
			// embAnno.setId("WGB - Port and Channel");

			oFactory.addAnnotation(embAnno, channelAnnotate);
			oFactory.addAnnotation(embAnno, portAnnotate);

			this.wgbAnnotateId++;
			String wgbAnn = "wgbAnnotation_" + this.wgbAnnotateId;
			oFactory.addAnnotation(wg1, oFactory.newEmbeddedAnnotation(wgbAnn,
					"WGB - Port and Channel", embAnno, accountColtemp));
			this.objects.add(wg1);
		}

	}

	// -------------------------------------------------------------------------------------------------
	// MetaResult result, Map cp, String key) {
	public void wasDeriviedFrom(MetaMethodInvocation invocation, Object target) {
		if (!(isq.granularityLevel == 9)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/dd/MM hh:mm:ss.SSS");
			String output = " ***" + simpleDateFormat.format(new Date());
			String processN = "zzz";
			String proName;
			Object procObj = null;
			int inportNo = 0, outPortNo = 0;

			String inputPort1 = null, outPort1 = null, outPort = null, inputPort = null;
			String inputPort2 = null, outPort2 = null;

			Token inputToken = null, inputToken1 = null, inputToken2 = null;
			Token outToken = null, outToken1 = null, outToken2 = null;

			Object inchanel = null;
			Object outchanel = null;
			AbstractProcess ap = null;
			boolean yek = false, dovom = false;
			String temp1 = " ";
			String temp2 = " ";
			String temp3 = " ";

			Map res = (Map) ((AbstractProcess) target).getData();
			Set<String> WDFkeySet = res.keySet();
			// AbstractProcess ap = (AbstractProcess) cp.get("process");

			for (Iterator iterator = WDFkeySet.iterator(); iterator.hasNext();) {
				String WGFkey = (String) iterator.next();

				if (WGFkey.startsWith("Output")) {
					if (WGFkey.equalsIgnoreCase("OutputValue")) {
						outToken = (Token) res.get(WGFkey);
						outPort = WGFkey;
					} else if (WGFkey.equalsIgnoreCase("OutputValue1")) {
						outToken1 = (Token) res.get(WGFkey);
						outPort1 = WGFkey;
					} else if (WGFkey.equalsIgnoreCase("OutputValue2")) {
						outToken2 = (Token) res.get(WGFkey);
						outPort2 = WGFkey;
					}
				}
				if (WGFkey.startsWith("Input")) {
					if (WGFkey.equalsIgnoreCase("InputValue")) {
						inputToken = (Token) res.get(WGFkey);
						inputPort = WGFkey;
					} else if (WGFkey.equalsIgnoreCase("InputValue1")) {
						inputToken1 = (Token) res.get(WGFkey);
						inputPort1 = WGFkey;
					} else if (WGFkey.equalsIgnoreCase("InputValue2")) {
						inputToken2 = (Token) res.get(WGFkey);
						inputPort2 = WGFkey;
					}
				}
			}
			AbstractProcess proces = (AbstractProcess) target;

			if (!(target.equals(null))) {
				// Jaygozin kon----------------------------------------
				// Process findedProcess = findProcessOPMXML(target);
				if (!(outToken == null) || !(outToken1 == null)
						|| !(outToken2 == null)) {
					if (!(inputToken == null) || !(inputToken1 == null)
							|| !(inputToken2 == null)) {
						// // outToken
						if (!(outToken == null)) {
							if (!(inputToken == null)) {

								createWDF(inputToken, outToken, target);

							}
							if (!(inputToken1 == null)) {

								createWDF(inputToken1, outToken, target);
							}
							if (!(inputToken2 == null)) {

								createWDF(inputToken2, outToken, target);
							}

						}
						// outToken1
						if (!(outToken1 == null)) {
							if (!(inputToken == null)) {

								createWDF(inputToken, outToken1, target);

							}
							if (!(inputToken1 == null)) {
								createWDF(inputToken1, outToken1, target);
							}
							if (!(inputToken2 == null)) {
								createWDF(inputToken2, outToken1, target);
							}

						}
						// outToken2
						if (!(outToken2 == null)) {
							if (!(inputToken == null)) {
								createWDF(inputToken, outToken2, target);
							}
							if (!(inputToken1 == null)) {
								createWDF(inputToken1, outToken2, target);
							}
							if (!(inputToken2 == null)) {
								createWDF(inputToken2, outToken2, target);
							}

						}
					}
				}
			}
		}
	}

	public void createWDF(Token inputToken, Token outToken, Object process) {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}

		if ((isq.granularityLevel == 2)) {
			// find artifact
			Artifact findedArtifact1 = findArtifactOPMXML(outToken, false);
			Artifact findedArtifact2 = findArtifactOPMXML(inputToken, false);
			// Account findAccount = findAccountOPMXML(account);
			this.wasDerivedFromCounter++;
			String wasDerivedFromid = "WDF_" + this.wasDerivedFromCounter;

			// show just WDFId
			WasDerivedFrom WDF = this.oFactory.newWasDerivedFrom(
					wasDerivedFromid, findedArtifact1, findedArtifact2,
					accountColtemp);
			this.objects.add(WDF);
			// annotation
		} else if ((isq.getGranularityLevel() == 0)
				|| (isq.granularityLevel == 1)) {
			// find artifact
			Artifact findedArtifact1 = findArtifactOPMXML(outToken, false);
			Artifact findedArtifact2 = findArtifactOPMXML(inputToken, false);
			// Account findAccount = findAccountOPMXML(account);
			this.wasDerivedFromCounter++;
			String wasDerivedFromid = "WDF_" + this.wasDerivedFromCounter;

			// show just WDFId
			WasDerivedFrom WDF = this.oFactory.newWasDerivedFrom(
					wasDerivedFromid, findedArtifact1, findedArtifact2,
					accountColtemp);
			// Account account21 = new Account();
			// accountCollection.add(account21);
			this.wdfAnnotateId++;
			String wdfAnn = "wdfAnnotation_" + this.wdfAnnotateId;
			oFactory.addAnnotation(WDF, oFactory.newEmbeddedAnnotation(wdfAnn,
					"WDB - process", process.toString(), accountColtemp));// accountCollection
			this.objects.add(WDF);
		}

	}

	// ==================================================================================
	public void createWCB(Object baseObject, Object ag) {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}
		// ProcessThreadImpl pti = (ProcessThreadImpl) agent;
		Process findedProcess1 = findProcessOPMXML((Object) (baseObject));

		Agent agent = findAgentOPMXML(ag);
		this.wasControlledByCounter++;
		String wasControlledById = "WCB_" + this.wasControlledByCounter;
		// Agent agent = findAgentOPMXML(baseObject);
		WasControlledBy wcb = this.oFactory.newWasControlledBy(
				wasControlledById,
				findedProcess1,
				oFactory.newRole(String.valueOf("DataFlow-Scheduler")
						+ "- scheduler"), agent, accountColtemp);

		this.objects.add(wcb);

		// AbstractProcess proces = (AbstractProcess) baseObject;
		// proces.get
		// Process findedProcess1 = findProcessOPMXML((Object) (baseObject));
		// String output = " Process: "
		// +
		// baseObject.getClass().getSimpleName()+"ProcessThreadImpl"+baseObject.getClass().get
		// + "  ProcessThreadImpl Id: " + baseObject.hashCode();
		// System.out.println("  WCF : " + output);
	}

	// ========================================================
	// //////////////////////////////////////////////////////////

	public void createWTB(Object baseObject) {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}

		if ((isq.granularityLevel == 2)) {

			// opm xml
			// find process1
			Process findedProcess1 = findProcessOPMXML(baseObject);
			Process findedProcess2 = findProcessOPMXML(((AbstractProcess) baseObject)
					.getPrevProcessObj());
			// Process findedProcess2 = (Process) this.processesMap.get(ab
			// .getPrevProcessObj());

			this.wasTriggeredByCounter++;
			String wasTriggeredById = "WTB_" + this.wasTriggeredByCounter;

			WasTriggeredBy wtb = this.oFactory.newWasTriggeredBy(
					wasTriggeredById, findedProcess1, findedProcess2,
					accountColtemp);
			this.objects.add(wtb);
		} else if ((isq.getGranularityLevel() == 0)
				|| (isq.granularityLevel == 1)) {

			// opm xml
			// find process1
			Process findedProcess1 = findProcessOPMXML(baseObject);
			Process findedProcess2 = findProcessOPMXML(((AbstractProcess) baseObject)
					.getPrevProcessObj());
			// Process findedProcess2 = (Process) this.processesMap.get(ab
			// .getPrevProcessObj());

			this.wasTriggeredByCounter++;
			String wasTriggeredById = "WTB_" + this.wasTriggeredByCounter;

			WasTriggeredBy wtb = this.oFactory.newWasTriggeredBy(
					wasTriggeredById, findedProcess1, findedProcess2,
					accountColtemp);

			// token ra payda kon
			int inportNo = 0, outPortNo = 0;
			String inputPort1 = null, outPort1 = null, outPort = null, inputPort = null;
			String inputPort2 = null, outPort2 = null;
			Token inputToken = null, inputToken1 = null, inputToken2 = null;
			Token outToken = null, outToken1 = null, outToken2 = null;
			String output = "";

			wtbAnnotateId = 0;

			String wtbAnn = "wtbAnnotation_" + this.wtbAnnotateId;
			Map res = (Map) (((AbstractProcess) baseObject)).getData();
			Set<String> WDFkeySet = res.keySet();
			// AbstractProcess ap = (AbstractProcess) cp.get("process");

			for (Iterator iterator = WDFkeySet.iterator(); iterator.hasNext();) {
				String WGFkey = (String) iterator.next();

				if (WGFkey.startsWith("Input")) {
					if (WGFkey.equalsIgnoreCase("InputValue")) {
						inputToken = (Token) res.get(WGFkey);
						inputPort = WGFkey;

						this.wtbAnnotateId++;
						oFactory.addAnnotation(wtb, oFactory
								.newEmbeddedAnnotation(wtbAnn,
										"WTB - token coming into input port ",
										inputToken.toString(), accountColtemp));

					} else if (WGFkey.equalsIgnoreCase("InputValue1")) {
						inputToken1 = (Token) res.get(WGFkey);
						inputPort1 = WGFkey;

						this.wtbAnnotateId++;
						oFactory.addAnnotation(
								wtb,
								oFactory.newEmbeddedAnnotation(
										wtbAnn,
										"WTB - token coming into input port No. 1",
										inputToken1.toString(), accountColtemp));

					} else if (WGFkey.equalsIgnoreCase("InputValue2")) {
						inputToken2 = (Token) res.get(WGFkey);
						inputPort2 = WGFkey;

						this.wtbAnnotateId++;
						oFactory.addAnnotation(
								wtb,
								oFactory.newEmbeddedAnnotation(
										wtbAnn,
										"WTB - token coming into input port No. 2 ",
										inputToken2.toString(), accountColtemp));
					}
				}
				this.objects.add(wtb);
			}
		}
	}

	// //----------------------------------------------------------------------------

	public Process findProcessOPMXML(Object proccc) {
		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}

		Process findedProcess = null;
		Set<String> prockeySet = this.processesMap.keySet();
		for (Iterator iter = prockeySet.iterator(); iter.hasNext();) {
			au.edu.adelaide.kahn.pn.Process app = (au.edu.adelaide.kahn.pn.Process) iter
					.next();
			if ((proccc.hashCode()) == (app.hashCode())) {
				findedProcess = (Process) this.processesMap.get(app);
			}
		}
		if (findedProcess == null) {
			this.processCounter++;
			String processId = "P_" + this.processCounter;
			findedProcess = oFactory.newProcess(processId, accountColtemp,
					String.valueOf(proccc));
			this.processes.add(findedProcess);
			processesMap.put(proccc, findedProcess);
		}
		return findedProcess;
	}

	// =========================================================

	public Agent findAgentOPMXML(Object agent) {
		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}
		Agent findedAgent = null;
		Set<String> agkeySet = this.agentMap.keySet();
		for (Iterator iter = agkeySet.iterator(); iter.hasNext();) {
			ProcessThreadImpl app = (ProcessThreadImpl) iter.next();
			if ((agent.hashCode()) == (app.hashCode())) {
				findedAgent = (Agent) this.processesMap.get(app);
			}
		}
		if (findedAgent == null) {
			this.agentCounter++;
			String agentId = "Agent_" + this.agentCounter;
			findedAgent = oFactory.newAgent(agentId, accountColtemp,
					String.valueOf(agent));
			this.agents.add(findedAgent);
			agentMap.put(agent, findedAgent);
		}
		return findedAgent;
	}

	// ======================================
	public Artifact findArtifactOPMXML(au.edu.adelaide.pna.data.Token token, boolean intermediateArtifact) {
		Artifact findedArtifact=null;
		Collection<Account> accountColtemp = null;
		String artifactId = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}
if(!intermediateArtifact){
		 findedArtifact = (Artifact) this.artifactMap.get(token);
		if (findedArtifact == null) {
			this.artifactCounter++;
			 artifactId = "a_" + this.artifactCounter;
			findedArtifact = oFactory.newArtifact(artifactId, accountColtemp,
					String.valueOf(token.getType()));
			if ((isq.getGranularityLevel() == 0) || (isq.granularityLevel == 1)) {
				this.artifactAnnotateId++;
				String artAnn = "artifactAnnotation_" + this.artifactAnnotateId;
				oFactory.addAnnotation(findedArtifact, oFactory
						.newEmbeddedAnnotation(artAnn, "tokenValue",
								((IntToken) token).intValue(),
								accountColtemp));
			}
			this.artifactes.add(findedArtifact); // add artifact at the end of
													// artifacts array
			this.artifactMap.put(token, findedArtifact);
		}
		}else{
			Artifact findedintermediateArtifact = (Artifact) this.iartifactMap.get(token);
			if (findedintermediateArtifact == null) {
				
				// add to i-artifact map
				this.IartifactCounter++;
				String iartifactId = "IntermediateToken_" + this.IartifactCounter;
				findedintermediateArtifact = oFactory.newArtifact(iartifactId, accountCollection3,
						String.valueOf(token.getType()));
			
					this.IartifactAnnotateId++;
					String artAnn = "IartifactAnnotation_" + this.IartifactAnnotateId;
					oFactory.addAnnotation(findedintermediateArtifact, oFactory
							.newEmbeddedAnnotation(artAnn, "tokenValue",
									((IntToken) token).intValue(),
									accountCollection3));
		
					this.iartifactes.add(findedintermediateArtifact); // add artifact at the end of
					this.iartifactMap.put(token, findedintermediateArtifact);
					
				this.artifactes.add(findedintermediateArtifact); // add artifact at the end of
			}
			findedArtifact =findedintermediateArtifact;	
		}
		return findedArtifact;
	}
// //=========================================================================
//  //================================ intermediate dependency for fine-grained provenance
	private void IWDF(MetaMethodInvocation invocation, Object target) {

			if (!(isq.granularityLevel == 9)) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy/dd/MM hh:mm:ss.SSS");
				
				Object procObj = null;
				int inportNo = 0, outPortNo = 0;

				String inputPort1 = null, outPort1 = null, outPort = null, inputPort = null;
				String inputPort2 = null, outPort2 = null;

				Token inputToken = null, inputToken1 = null, inputToken2 = null;
				Token outToken = null, outToken1 = null, outToken2 = null;

				Object inchanel = null;
				Object outchanel = null;
				AbstractProcess ap = null;
				boolean yek = false, dovom = false;
				String temp1 = " ";
				String temp2 = " ";
				String temp3 = " ";

				Map res = (Map) ((AbstractProcess) target).getData();
				Set<String> WDFkeySet = res.keySet();
				// AbstractProcess ap = (AbstractProcess) cp.get("process");

				for (Iterator iterator = WDFkeySet.iterator(); iterator.hasNext();) {
					String WGFkey = (String) iterator.next();

					if (WGFkey.startsWith("Output")) {
						if (WGFkey.equalsIgnoreCase("OutputValue")) {
							outToken = (Token) res.get(WGFkey);
							outPort = WGFkey;
						} else if (WGFkey.equalsIgnoreCase("OutputValue1")) {
							outToken1 = (Token) res.get(WGFkey);
							outPort1 = WGFkey;
						} else if (WGFkey.equalsIgnoreCase("OutputValue2")) {
							outToken2 = (Token) res.get(WGFkey);
							outPort2 = WGFkey;
						}
					}
					if (WGFkey.startsWith("Input")) {
						if (WGFkey.equalsIgnoreCase("InputValue")) {
							inputToken = (Token) res.get(WGFkey);
							inputPort = WGFkey;
						} else if (WGFkey.equalsIgnoreCase("InputValue1")) {
							inputToken1 = (Token) res.get(WGFkey);
							inputPort1 = WGFkey;
						} else if (WGFkey.equalsIgnoreCase("InputValue2")) {
							inputToken2 = (Token) res.get(WGFkey);
							inputPort2 = WGFkey;
						}
					}
				}
				AbstractProcess proces = (AbstractProcess) target;

				if (!(target.equals(null))) {
					// Jaygozin kon----------------------------------------
					// Process findedProcess = findProcessOPMXML(target);
					if (!(outToken == null) || !(outToken1 == null)
							|| !(outToken2 == null)) {
						if (!(inputToken == null) || !(inputToken1 == null)
								|| !(inputToken2 == null)) {
							// // outToken
							if (!(outToken == null)) {
								if (!(inputToken == null)) {

									createIWDF(inputToken, outToken, target);

								}
								if (!(inputToken1 == null)) {

									createIWDF(inputToken1, outToken, target);
								}
								if (!(inputToken2 == null)) {

									createIWDF(inputToken2, outToken, target);
								}

							}
							// outToken1
							if (!(outToken1 == null)) {
								if (!(inputToken == null)) {

									createIWDF(inputToken, outToken1, target);

								}
								if (!(inputToken1 == null)) {
									createIWDF(inputToken1, outToken1, target);
								}
								if (!(inputToken2 == null)) {
									createIWDF(inputToken2, outToken1, target);
								}

							}
							// outToken2
							if (!(outToken2 == null)) {
								if (!(inputToken == null)) {
									createIWDF(inputToken, outToken2, target);
								}
								if (!(inputToken1 == null)) {
									createIWDF(inputToken1, outToken2, target);
								}
								if (!(inputToken2 == null)) {
									createIWDF(inputToken2, outToken2, target);
								}

							}
						}
					}
				}
			}
		}
	
	public void createIWDF(Token inputToken, Token outToken, Object process) {

		Collection<Account> accountColtemp = accountCollection3;
		
		if ((isq.getGranularityLevel() == 0)) {
//			IntToken to = new IntToken();
//			to.
			
			// find artifact
			Artifact findedArtifact1 = findArtifactOPMXML(outToken, true);
			Artifact findedArtifact2 = findArtifactOPMXML(inputToken, true);
			
//		    Artifact findedArtifact1 = findIntermedateArtifact(outToken);
//			Artifact findedArtifact2 = findIntermedateArtifact(inputToken);
			
			// Account findAccount = findAccountOPMXML(account);
			this.IwasDerivedFromCounter++;
			String wasDerivedFromid = "I-WDF_" + this.IwasDerivedFromCounter;

			// show just WDFId
			WasDerivedFrom WDF = this.oFactory.newWasDerivedFrom(
					wasDerivedFromid, findedArtifact1, findedArtifact2,
					accountColtemp);
//	??----
			this.IwdfAnnotateId++;
			String wdfAnn = "I-wdfAnnotation_" + this.IwdfAnnotateId;
			oFactory.addAnnotation(WDF, oFactory.newEmbeddedAnnotation(wdfAnn,
					"I-WDB - process", process.toString(), accountColtemp));
			this.objects.add(WDF);
		}
	}
	// //=========================================================================
	// //=========================================================================

	public void createOPMGraph() {

		Collection<Account> accountColtemp = null;
		if (isq.getGranularityLevel() == 0) {
			accountColtemp = accountCollection3;
		} else if (isq.getGranularityLevel() == 1) {
			accountColtemp = accountCollection2;
		} else if (isq.getGranularityLevel() == 2) {
			accountColtemp = accountCollection;
		}
		
		OPMGraph graph = oFactory.newOPMGraph(accountColtemp,
				new Overlaps[] {},
				this.processes.toArray(new Process[this.processes.size()]),
				this.artifactes.toArray(new Artifact[this.artifactes.size()]),
				this.agents.toArray(new Agent[this.agents.size()]),
				this.objects.toArray(), new Annotation[] {});

		ann = oFactory.newEmbeddedAnnotation("an15",
				"http://property.org/hasQuality", "average", accountColtemp);
		this.objects.add(ann);

		// OPMGraph graph=new MyGraph().getGraph(oFactory);
		OPMSerialiser serial2 = OPMSerialiser.getThreadOPMSerialiser();
		try {
			
			if (isq.getGranularityLevel() == 0) {
				serial2.serialiseOPMGraph(new File("OPM-FineGrainedMoP.xml"),
						graph, true);
			} else if (isq.getGranularityLevel() == 1) {
				serial2.serialiseOPMGraph(new File("OPM-MediumGrainedMoP.xml"),
						graph, true);
			} else if (isq.getGranularityLevel() == 2) {
				serial2.serialiseOPMGraph(new File("OPM-CoarseGrainedMoP.xml"),
						graph, true);
			} else if (isq.getGranularityLevel() == 9) {
				serial2.serialiseOPMGraph(new File("NoProvenance.xml"),
						graph, true);
			}
			 
			
			System.out.println("xml is created");
		} catch (JAXBException e) {
			System.out.println("**** error in xml writing opm serialiser ");
			e.printStackTrace();
		}
	}
}