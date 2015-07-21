	package au.edu.adelaide.enigma.provanance;

	import java.io.File;
	import java.lang.reflect.InvocationTargetException;
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
	import au.edu.adelaide.enigma.mop.MetaValueResult;
	import au.edu.adelaide.pna.data.IntToken;
	import au.edu.adelaide.pna.data.Token;
	import au.edu.adelaide.kahn.pn.AbstractProcess;
	import au.edu.adelaide.pna.system.ProcessThreadImpl;

	public class ProvenanceCollector2 {

		
	    private static ProvenanceCollector2 instance = null;
	    private ProvenanceCollector2() { }
	    public static synchronized ProvenanceCollector2 getInstance() {
	        if (instance == null) {
	            instance = new ProvenanceCollector2();
	        }
	        return instance;
	    }
	    


		HashMap processesMap = new HashMap();
		HashMap artifactMap = new HashMap();
		HashMap agentMap = new HashMap();

		int processCounter = 0;
		int artifactCounter = 0;
		int usedCounter = 0;
		int wasDerivedFromCounter = 0;
		int wasgeneratedByCounter = 0;
		int wasTriggeredByCounter = 0;
		int wasControlledByCounter = 0;
		int agentCounter= 0;
		
		int artifactAnnotateId = 0;
		int wgbAnnotateId = 0;
		int usedAnnoateId = 0;
		int wdfAnnotateId = 0;
		
		// added for opm
//		 OPMFactory oFactory;
//		Account account;
		 Collection<Account> accountCollection;
			Collection<Account> accountCollection2;
			Collection<Account> accountCollection3;

		 Vector<Process> processes = new Vector<Process>();
		 Vector<Agent> agents = new Vector<Agent>();
		 Vector<Artifact> artifactes = new Vector<Artifact>();
		 Vector<Object> objects = new Vector<Object>();
		 EmbeddedAnnotation ann = new EmbeddedAnnotation();
		
		 OPMFactory	oFactory = new OPMFactory();
		 Account account = new Account();
		 InformationServiceQuantizer isq = InformationServiceQuantizer.getInstance();
		 
			Account account2 = new Account();
			Account account3 = new Account();
			

		
//		private FixMethodWrapper method;
		private Object[] args;
//		Logger log = Logger.getLogger(MIProvenance.class);

		
		// Method and Invocation are the same.
		public void methodInvocationFiltering(MetaMethodInvocation invocation,Object baseObject)
				throws IllegalAccessException, InvocationTargetException {
			
			
//			MetaValueResult mr = new MetaValueResult(method.getMethod().invoke(
//					baseObject, args));
			account.setId("account1");
			accountCollection = new ArrayList<Account>();
			accountCollection.add(account);
			
			account2.setId("account2");
			accountCollection2 = new ArrayList<Account>();
			accountCollection2.add(account2);
			
			account3.setId("account3");
			accountCollection3 = new ArrayList<Account>();
			accountCollection3.add(account3);
			
			
			
			
//			if(invocation.getMethod().getName() != "invoke"){
//				if(invocation.getMethod().getName() != "handleMethodInvocation"){
//					if(invocation.getMethod().getName() != "hashCode"){
//				 System.out.println("  "+ invocation.getMethod().getName()+
//						 "   == caller: "+baseObject.getClass().getName());
//				}
//			}
//			}
	//=====================================================================================		
			// Ba invoke metavani b object Computation dasresi payda koni.
			
//			//if ((method.getMethod().getName().equalsIgnoreCase("invoke"))) {
//			if ((invocation.getMethod().getName().equalsIgnoreCase("put"))) {
//				if(baseObject.getClass().getSimpleName().equalsIgnoreCase("ComputationImpl")){
					
//					String output = " ComputationImpl: "
////							+((ComputationImpl) baseObject).getDirectory()
//							+ "  ComputationImpl Id: " + baseObject;
//					System.out.println(output );	
					
//					Map cp = (Map)((ComputationImpl) baseObject).getDirectory();
//					Set<String> keySet = cp.keySet();
//					for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
//						String key = (String) iterator.next();
//						if (cp.containsKey(key)) {
//							wasGeneratedBy(invocation,baseObject,mr, cp, key);
////						}
//					}
//				}
//			}
			
			

								// New Process - RegProcess
//			if ((invocation.getMethod().getName().equalsIgnoreCase("newProcess"))) {
		if ((invocation.getMethod().getName().equalsIgnoreCase("newInputPort")) || (invocation.getMethod().getName().equalsIgnoreCase("newOutputPort"))) {
			Object[] argg = invocation.getArguments();
//			Object argg = baseObject;
			String output =  "  Process: " + argg[0] +"  portNo. :" +argg[1];
			Process findedProcess = findProcessOPMXML(argg[0]);
//			System.out.println(output );	
		}

				if ((invocation.getMethod().getName().equalsIgnoreCase("trigger"))) {
//							WasControlledBy
				createWCB(baseObject);
//				
			}
				 if((invocation.getMethod().getName().equalsIgnoreCase("get"))) {
					 System.out.println("  "+ invocation.getMethod().getName()+
							 "   == caller: "+baseObject.getClass().getName());	
					 
//					 String output ="  " +baseObject.getClass().getSimpleName() +
//					 "   Id: " +baseObject.hashCode() + "   value: " + (mr.getValue());
					 System.out.println("mmmm "+baseObject);
					 }
				
//			if (invocation.getMethodName().startsWith("get")) {
			if ((invocation.getMethod().getName().equalsIgnoreCase("fire"))) {
				if (!(((AbstractProcess) baseObject).getPreviousProcess() == null)) {
//				      					WasTriggerBy
					createWTB(baseObject);
				}
				
				used(invocation, baseObject);
			
			}
			if (invocation.getMethod().getName().startsWith("getData")) {
				/// wasGeneratedBy
				wasGeneratedBy(invocation, baseObject);		
				
//								wasDeriviedFrom
				wasDeriviedFrom(invocation, baseObject);
//				
			}

	//=====================================================================================
			
//			if((method.getMethod().getName().contentEquals("get"))) {
//				 used(baseObject, invocation, cp, key);	
//			}

//			 if((method.getMethod().getName().contentEquals("get"))) {
//			
//			 String output ="  " +baseObject.getClass().getSimpleName() +
//			 "   Id: " +baseObject.hashCode() + "   value: " + (mr.getValue());
//			 System.out.println(output);
//			 }

			// if((method.getMethod().getName().contentEquals("getData"))) {
			// /*
			// * Fire process with full Info ActorFireWithInfo
			// */
			// String output = " Process: " +baseObject.getClass().getSimpleName() +
			// "  Process Id: " +baseObject.hashCode() + "  process properties: " +
			// (mr.getValue());
			// log.info(output);
			// }else if((method.getMethod().getName().contentEquals("get"))) {
			// /*
			// * input_Port_event
			// */
			// String output =" Channel: " +baseObject.getClass().getSimpleName() +
			// "  Channel Id: " +baseObject.hashCode() + "  Token value: " +
			// (mr.getValue());
			// log.info(output);
			// }else if((method.getMethod().getName().contentEquals("put"))) {
			// /*
			// * Output_Port_event
			// // */
			// HalfChannelOutputPort in = ((HalfChannelOutputPort) baseObject);
			// String output =" Channel: " +baseObject.getClass().getSimpleName() +
			// "  Channel Id: " +baseObject.hashCode()+"  Token value: " +
			// (mr.getValue());
			// log.info(output);
			// }
			//
//			return mr;
		}



	//------------------------------
//		FixMethodWrapper invocation, Object target,
		private void used(MetaMethodInvocation invocation,
				Object target) {
			if ((isq.getGranularityLevel() == 0) && !(isq.granularityLevel == 9)) {
			
		Object chanl = new Object();
		AbstractProcess ab = (AbstractProcess) target;
		Map res = ab.getData();
		Set<String> usedkeySet = res.keySet();

//		/**
//		 * this code added for XML
//		 **/
	//
//		Element usedRelationElement = document
//				.createElement("used_relation");
//		excecutionElement.appendChild(usedRelationElement);
	//
//		Element channelElement = document.createElement("channel");
//		usedRelationElement.appendChild(channelElement);
//		Text OTLNOText = document.createTextNode("");
//		channelElement.appendChild(OTLNOText);
//		OTLNOText.setNodeValue(String.valueOf(ret));
	//
//		Element processElement = document.createElement("process");
//		usedRelationElement.appendChild(processElement);
//		Text OTLNOText2 = document.createTextNode("");
//		processElement.appendChild(OTLNOText2);
//		OTLNOText2.setNodeValue(String.valueOf(callee));
	//
//		Element tokensElement = document.createElement("tokens");
//		usedRelationElement.appendChild(tokensElement);

		for (Iterator iterator = usedkeySet.iterator(); iterator.hasNext();) {
			String WGFkey = (String) iterator.next();

			if (WGFkey.equalsIgnoreCase("InputValue")) {

				Object inval = res.get(WGFkey);
//				createUSED(ab, inval);
//				Element tElement = document.createElement("InputValue");
//				tokensElement.appendChild(tElement);
	//
//				Text OTLNOText3 = document.createTextNode("");
//				tElement.appendChild(OTLNOText3);
//				OTLNOText3.setNodeValue(((Token) inval).toString());
	//
				
				
				// opm
				createUSED(ab, inval,chanl,00);


			}

			// if (WGFkey.contains("InputValue1")) {
			if (WGFkey.equalsIgnoreCase("InputValue1")) {
				Object inval = res.get(WGFkey);
//				createUSED(ab, inval);
				// System.out.println("InputValue1: artifact value: "+(int)
				// inval + output);
				// System.out.println("InputValue1: artifact value: "+
				// ((Token) inval).toString() + output);

//				Element tElement = document.createElement("InputValue1");
//				tokensElement.appendChild(tElement);
	//
//				Text OTLNOText3 = document.createTextNode("");
//				tElement.appendChild(OTLNOText3);
//				OTLNOText3.setNodeValue(((Token) inval).toString());
	//
//				/*
//				 * // * this code add for pom xml , used //
//				 */
	//
				createUSED(ab, inval,chanl,10);


	
			}

			// if (WGFkey.contains("InputValue2")) {
			if (WGFkey.equalsIgnoreCase("InputValue2")) {
				Object inval2 = res.get(WGFkey);
//				createUSED(ab, inval2);
//				Element tokenElement = document
//						.createElement("InputValue2");
//				tokensElement.appendChild(tokenElement);
//				Text OTLNOText3 = document.createTextNode("");
//				tokenElement.appendChild(OTLNOText3);
//				OTLNOText3.setNodeValue(((Token) inval2).toString());
	//
//				// ///////////////////////////////////////////////////////////////////////////////
//				/*
//				 * // * this code add for pom xml , used //
//				 */
				
				createUSED(ab, inval2,chanl,01);

			}}
			}
		}
		

		private void createUSED(Object callee, Object inval1,Object channel, int portIndex ) {
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token)inval1);
			this.usedCounter++;
			String usedId = "U_" + this.usedCounter;
			Used u1 = oFactory.newUsed(usedId,findedProcess,
					oFactory.newRole("in1"), findedArtifact,
					accountCollection);

		
			
			// annotation 
					if ((isq.getGranularityLevel() == 0) || (isq.granularityLevel == 1)) {
						Value portAnnotate= new Value();
						portAnnotate.setId("port index");
//						portAnnotate.setContent(portIndex);
						String pindex = null;
						switch (portIndex){
						case 00: pindex="0 => the only input port";break;
						case 10: pindex="1 => there is two input ports";break;
						case 01: pindex="2 => there is two input ports";break;
						}
						portAnnotate.setEncoding(pindex);
						Value channelAnnotate= new Value();
						channelAnnotate.setId("channel");
						EmbeddedAnnotation embAnno = new EmbeddedAnnotation();
//						embAnno.setId("USED - Port and Channel");
						
						oFactory.addAnnotation(embAnno, channelAnnotate);
						oFactory.addAnnotation(embAnno, portAnnotate);	
						this.usedAnnoateId++;	
					String usedAnn = "usedAnnotation_"+this.usedAnnoateId;	
					oFactory.addAnnotation(u1, oFactory.newEmbeddedAnnotation(usedAnn, "USED - Port and Channel", embAnno, accountCollection2));
					}
					
					this.objects.add(u1);
		}
		
		
	//------------------------------------------------------------------------------------------------
		private void wasGeneratedBy(MetaMethodInvocation invocation,
				Object target) {
			
			if ((isq.getGranularityLevel() == 0) && !(isq.granularityLevel == 9)) {
			AbstractProcess ab = (AbstractProcess) target;
			Map res = ab.getData();
			Set<String> WDFkeySet = res.keySet();

			/**
			 * this code added for XML
			 **/
	//
//			Element wasGeneratedByElement = document
//					.createElement("was_generated_by");
//			excecutionElement.appendChild(wasGeneratedByElement);
	//
//			Element channelElement = document.createElement("channel");
//			wasGeneratedByElement.appendChild(channelElement);
//			Text OTLNOText = document.createTextNode("");
//			channelElement.appendChild(OTLNOText);
//			OTLNOText.setNodeValue(String.valueOf(ret));
	//
//			Element processElement = document.createElement("process");
//			wasGeneratedByElement.appendChild(processElement);
//			Text OTLNOText2 = document.createTextNode("");
//			processElement.appendChild(OTLNOText2);
//			OTLNOText2.setNodeValue(String.valueOf(callee));
	//
//			Element artifactsElement = document.createElement("artifacts");
//			wasGeneratedByElement.appendChild(artifactsElement);

			for (Iterator iterator = WDFkeySet.iterator(); iterator.hasNext();) {
				String WGFkey = (String) iterator.next();
				Object chanl= new Object();
				if (WGFkey.equalsIgnoreCase("OutputValue")) {
					Object outval = res.get(WGFkey);

					
		//				output = output + "  Channel.id = " + +ret.hashCode()
//							+ "  Channel.name = " + ret;
//					output = output + "  Process.id = " + +callee.hashCode()
//							+ "  Process.name = " + callee;
//					// System.out.println("outputVal1: artifact value: "+(int)
//					// outval + output);
//					// System.out.println("outputVal1: artifact value: "+
//					// ((Token) outval).toString() + output); %
	//
//					Element artifactElement = document
//							.createElement("OutputValue");
//					artifactsElement.appendChild(artifactElement);
//					Text OTLNOText4 = document.createTextNode("");
//					artifactElement.appendChild(OTLNOText4);
//					OTLNOText4.setNodeValue(String.valueOf(outval));
	//
					
					
//					// opm was generated by
					createWGB(ab, outval, chanl, 00);
					

				}

				if (WGFkey.equalsIgnoreCase("OutputValue1")) {
					Object outval2 = res.get(WGFkey);
//					createWGB(ab, outval2);
//					output = output + "  Channel.id = " + +ret.hashCode()
//							+ "  Channel.name = " + ret;
//					output = output + "  Process.id = " + +callee.hashCode()
//							+ "  Process.name = " + callee;
//					
//					//
	//
//					Element artifactElement = document
//							.createElement("OutputValue1");
//					artifactsElement.appendChild(artifactElement);
//					Text OTLNOText4 = document.createTextNode("");
//					artifactElement.appendChild(OTLNOText4);
//					OTLNOText4.setNodeValue(String.valueOf(outval2));
	//
//					// opm was generated by
					createWGB(ab, outval2, chanl, 10);
					


				}
				if (WGFkey.equalsIgnoreCase("OutputValue2")) {
					Object outval2 = res.get(WGFkey);
//					createWGB(ab, outval2);
//					output = output + "  Channel.id = " + +ret.hashCode()
//							+ "  Channel.name = " + ret;
//					output = output + "  Process.id = " + +callee.hashCode()
//							+ "  Process.name = " + callee;
//			
//					Element artifactElement = document
//							.createElement("OutputValue2");
//					artifactsElement.appendChild(artifactElement);
//					Text OTLNOText4 = document.createTextNode("");
//					artifactElement.appendChild(OTLNOText4);
//					OTLNOText4.setNodeValue(String.valueOf(outval2));
	//
//					// /////////////////////////////////////////////////////////
//					// opm was generated by
					createWGB(ab, outval2, chanl, 01);
					

				}
			}
		} 
	}
		private void createWGB(Object callee, Object token,Object channel, int portIndex ) {
			// find process
			Process findedProcess = findProcessOPMXML(callee);

			// find artifact
			Artifact findedArtifact = findArtifactOPMXML((Token)token);
			this.wasgeneratedByCounter++;
			String wasgeneratedById = "WGB_"
					+ this.wasgeneratedByCounter;
			WasGeneratedBy wg1 = oFactory.newWasGeneratedBy(wasgeneratedById,
					findedArtifact, oFactory.newRole("out"),
					findedProcess, this.accountCollection);
			


			// annotation 
			if ((isq.getGranularityLevel() == 0) || (isq.granularityLevel == 1)) {
				Value portAnnotate= new Value();
				portAnnotate.setId("port index");
				portAnnotate.setContent(portIndex);
				String pindex = null;
				switch (portIndex){
				case 00: pindex="0 => the only input port";break;
				case 10: pindex="1 => there is two input ports";break;
				case 01: pindex="2 => there is two input ports";break;
				}
				portAnnotate.setEncoding(pindex);
				Value channelAnnotate= new Value();
				channelAnnotate.setId("channel");
				channelAnnotate.setContent(channel.getClass().getSimpleName());
				channelAnnotate.setEncoding(channel.toString());
				EmbeddedAnnotation embAnno = new EmbeddedAnnotation();
//				embAnno.setId("WGB - Port and Channel");
				
				oFactory.addAnnotation(embAnno, channelAnnotate);
				oFactory.addAnnotation(embAnno, portAnnotate);	
				
				this.wgbAnnotateId++;	
			String wgbAnn = "wgbAnnotation_"+this.wgbAnnotateId;	
			oFactory.addAnnotation(wg1, oFactory.newEmbeddedAnnotation(wgbAnn, "WGB - Port and Channel", embAnno, accountCollection2));
			}

			
			this.objects.add(wg1);
		}
		
	//-------------------------------------------------------------------------------------------------
//				MetaResult result, Map cp, String key) {
		public void wasDeriviedFrom(MetaMethodInvocation invocation, Object target) {
			if ((isq.getGranularityLevel() == 0) && !(isq.granularityLevel == 9)) {
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

								createWDF(inputToken, outToken,target);

							}
							if (!(inputToken1 == null)) {

								createWDF(inputToken1, outToken,target);
							}
							if (!(inputToken2 == null)) {

								createWDF(inputToken2, outToken,target);
							}

						}
						// outToken1
						if (!(outToken1 == null)) {
							if (!(inputToken == null)) {

								createWDF(inputToken, outToken1,target);

							}
							if (!(inputToken1 == null)) {
								createWDF(inputToken1, outToken1,target);
							}
							if (!(inputToken2 == null)) {
								createWDF(inputToken2, outToken1,target);
							}

						}
						// outToken2
						if (!(outToken2 == null)) {
							if (!(inputToken == null)) {
								createWDF(inputToken, outToken2,target);
							}
							if (!(inputToken1 == null)) {
								createWDF(inputToken1, outToken2,target);
							}
							if (!(inputToken2 == null)) {
								createWDF(inputToken2, outToken2,target);
							}

						}
					}
				}
			}
			}
		}

		
		//==================================================================================
		public void createWCB(Object baseObject) {
//			String output = " Process: "
//					+ ((ProcessThreadImpl)baseObject).getProcess()
//					+ "  Process Id: " + baseObject.hashCode();
//			System.out.println(output + " WasControlledBy:  "
//					+ (baseObject.getClass().getSimpleName()) + " Strategy: " + ((ProcessThreadImpl)baseObject).getStrategy() );
			
			////////////////////////////////////////////////////////////////////////
//			Element wasControlledByElement = document
//					.createElement("was_Controlled_By_Relation");
//			excecutionElement.appendChild(wasControlledByElement);
	//
//			Element processElement = document.createElement("process");
//			wasControlledByElement.appendChild(processElement);
//			Text OTLNOText = document.createTextNode("");
//			processElement.appendChild(OTLNOText);
//			OTLNOText.setNodeValue(String.valueOf(pti.getProcess()));
//			Element processElement1 = document.createElement("agent");
//			wasControlledByElement.appendChild(processElement1);
//			Text OTLNOText2 = document.createTextNode("");
//			processElement1.appendChild(OTLNOText2);
//			OTLNOText2.setNodeValue(String.valueOf(callee));
//			Element processElement2 = document.createElement("strategy");
//			wasControlledByElement.appendChild(processElement2);
//			Text OTLNOText3 = document.createTextNode("");
//			processElement2.appendChild(OTLNOText3);
//			OTLNOText3.setNodeValue(String.valueOf(pti.getStrategy()));
			// ///////////////////////////////////////////////////
			ProcessThreadImpl pti = (ProcessThreadImpl) baseObject;
			
			Process findedProcess1 = findProcessOPMXML((Object) (pti.getProcess()));

			this.wasControlledByCounter++;
			String wasControlledById = "WCB_" + this.wasControlledByCounter;
			Agent agent = findAgentOPMXML(baseObject);
			WasControlledBy wcb = this.oFactory.newWasControlledBy(
					wasControlledById, findedProcess1,
					oFactory.newRole(String.valueOf(pti.getStrategy())+"- scheduler"),
					agent,
					this.accountCollection);
			
			this.objects.add(wcb);
			
			String output = " ProcessThreadImpl: "
			+ baseObject.getClass().getSimpleName()
			+ "  ProcessThreadImpl Id: " + baseObject.hashCode();
	System.out.println("  WCF : " + output);
		}

		public void createWTB(Object baseObject) {
			if ((isq.getGranularityLevel() == 0) && !(isq.granularityLevel == 9)) {
//			String output = " Process: "
//					+ baseObject.getClass().getSimpleName()
//					+ "  Process Id: " + baseObject.hashCode();
//			System.out.println(output + " WasTrigeredBy:  "
//					+ ((AbstractProcess) baseObject).getPreviousProcess());
			
//////////////////////////////////////////////////////////////////////////			
//			Element wasTriggerByElement = document
//					.createElement("was_Trigger_By_Relation");
//			excecutionElement.appendChild(wasTriggerByElement);
	//
//			Element processElement = document
//					.createElement("triggered_process");
//			wasTriggerByElement.appendChild(processElement);
//			Text OTLNOText = document.createTextNode("");
//			processElement.appendChild(OTLNOText);
//			OTLNOText.setNodeValue(String.valueOf(callee));
//			Element processElement1 = document
//					.createElement("trigger_process");
//			wasTriggerByElement.appendChild(processElement1);
//			Text OTLNOText2 = document.createTextNode("");
//			processElement1.appendChild(OTLNOText2);
//			OTLNOText2
//					.setNodeValue(String.valueOf(ab.getPreviousProcess()));
	////////////////////////////////////////////////////////////
			// opm xml
			// find process1
			Process findedProcess1 = findProcessOPMXML(baseObject);
			Process findedProcess2 = findProcessOPMXML(((AbstractProcess) baseObject).getPrevProcessObj());
//			Process findedProcess2 = (Process) this.processesMap.get(ab
//					.getPrevProcessObj());

			this.wasTriggeredByCounter++;
			String wasTriggeredById = "WTB_" + this.wasTriggeredByCounter;

			WasTriggeredBy wtb = this.oFactory.newWasTriggeredBy(
					wasTriggeredById, findedProcess1, findedProcess2,
					this.accountCollection);
			this.objects.add(wtb);
			}
		}
//		public void createWGB(AbstractProcess inputToken, Object outToken) {
//			String output =   outToken+ " WasGeneratedBy " +  inputToken+ " ";
////			System.out.println(output);
//		}
//		public void createUSED(AbstractProcess inputToken, Object outToken) {
//			String output =inputToken   + " USED " +  outToken+ " ";
////			System.out.println(output);
//		}
		
		
		public void createWDF(Token inputToken, Token outToken, Object process) {
			
			if ((isq.getGranularityLevel() == 0) && !(isq.granularityLevel == 9)) {
			String output = outToken + " wasDeriviedFrom from " + inputToken + " ";
//			System.out.println(output);
			
			// opm xml
			// find artifact
			 Artifact findedArtifact1 = findArtifactOPMXML(outToken);
			 Artifact findedArtifact2 = findArtifactOPMXML(inputToken);
			
			 this.wasDerivedFromCounter++;
			 String wasDerivedFromid = "WDF_" + this.wasDerivedFromCounter;
			
			 // show just WDFId
			 WasDerivedFrom WDF =
			 this.oFactory.newWasDerivedFrom(wasDerivedFromid,
			 findedArtifact1, findedArtifact2, accountCollection);
			 
			 
				// annotation 
				if ((isq.getGranularityLevel() == 0) || (isq.granularityLevel == 1)) {
					this.wdfAnnotateId++;	
				String wdfAnn = "wdfAnnotation_"+this.wdfAnnotateId;	
				oFactory.addAnnotation(WDF, oFactory.newEmbeddedAnnotation(wdfAnn, "WDB - process", process.toString(), accountCollection2));
				}
			 
			 
			
			 this.objects.add(WDF);
			
			 // /////////////////// Provenance.xml//////////////////////////////
//			 Element wasGeneratedByElement = document
//			 .createElement("wasDeriviedFrom");
//			 excecutionElement.appendChild(wasGeneratedByElement);
//			 Element channelElement = document.createElement("Artifact-output");
//			 wasGeneratedByElement.appendChild(channelElement);
//			 Text OTLNOText = document.createTextNode("");
//			 channelElement.appendChild(OTLNOText);
//			 OTLNOText.setNodeValue(String.valueOf(outToken));//
//			 Element processElement = document.createElement("Artifact-input");
//			 wasGeneratedByElement.appendChild(processElement);
//			 Text OTLNOText2 = document.createTextNode("");
//			 processElement.appendChild(OTLNOText2);
//			 OTLNOText2.setNodeValue(String.valueOf(inputToken));//
			 // //////////////////////////////////////////////////
		}
		}
	////----------------------------------------------------------------------------

	public Process findProcessOPMXML(Object proccc) {
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
			findedProcess = oFactory.newProcess(processId, accountCollection,
					String.valueOf(proccc));
			this.processes.add(findedProcess);
//			System.out.println("   sizeeeeeeeeeeee   " +processes.size() + " thisss "+ this.toString());
			processesMap.put(proccc, findedProcess);
		}
		return findedProcess;
	}


	public Agent findAgentOPMXML(Object agent) {
		Agent findedAgent = null;
		Set<String> agkeySet = this.agentMap.keySet();
		for (Iterator iter = agkeySet.iterator(); iter.hasNext();) {
			ProcessThreadImpl app = (ProcessThreadImpl) iter
					.next();
			if ((agent.hashCode()) == (app.hashCode())) {
				findedAgent = (Agent) this.processesMap.get(app);
			}
		}
		if (findedAgent == null) {
			this.agentCounter++;
			String agentId = "Agent_" + this.agentCounter;
			findedAgent = oFactory.newAgent(agentId, accountCollection, String.valueOf(agent));
			this.agents.add(findedAgent);
			agentMap.put(agent, findedAgent);
		}
		return findedAgent;
	}

	//public Artifact findArtifactOPMXML(Object token) {
	public Artifact findArtifactOPMXML(au.edu.adelaide.pna.data.Token token) {
		Artifact findedArtifact = (Artifact) this.artifactMap.get(token);
		if (findedArtifact == null) {
			this.artifactCounter++;
			String artifactId = "a_" + this.artifactCounter;
			findedArtifact = oFactory.newArtifact(artifactId,
					accountCollection, String.valueOf(token.hashCode()));
			
//			Value v= new Value();
//			v.setContent(((IntToken)token).intValue());
//			v.setId(String.valueOf(token.hashCode()));
//			v.setEncoding(token.getClass().getName());
//			
//			oFactory.addAnnotation(findedArtifact,v);
//				
			
			if ((isq.getGranularityLevel() == 0) || (isq.granularityLevel == 1)) {
				this.artifactAnnotateId++;	
			String artAnn = "artifactAnnotation_"+this.artifactAnnotateId;	
			oFactory.addAnnotation(findedArtifact, oFactory.newEmbeddedAnnotation(artAnn, "tokenValue", ((IntToken)token).intValue(), accountCollection2));
			}
			// findedArtifact=oFactory.newArtifact(artifactId,accountCollection,token.toString());
			this.artifactes.add(findedArtifact); // add artifact at the end of
													// artifacts array
			this.artifactMap.put(token, findedArtifact);
		}
		return findedArtifact;
	}
	//	
	//	
//		//=========================================================================
		

		public void createOPMGraph() {
			
//			account.setId("account1");
//			accountCollection = new ArrayList<Account>();
//			accountCollection.add(account);
			
			
			OPMGraph graph = oFactory.newOPMGraph(accountCollection,
					new Overlaps[] {},
					this.processes.toArray(new Process[this.processes.size()]),
					this.artifactes.toArray(new Artifact[this.artifactes.size()]),
					this.agents.toArray(new Agent[this.agents.size()]),
					this.objects.toArray(), 
					new Annotation[] {});

			 ann = oFactory.newEmbeddedAnnotation("an15",
					"http://property.org/hasQuality", "average", accountCollection);


			// OPMGraph graph=new MyGraph().getGraph(oFactory);
			OPMSerialiser serial2 = OPMSerialiser.getThreadOPMSerialiser();
			try {
				serial2.serialiseOPMGraph(new File("OPM-EnigmaPNACompuatation2.xml"), graph, true);
				System.out.println("xml is created");
			} catch (JAXBException e) {
				System.out.println("**** error in xml writing opm serialiser ");
				e.printStackTrace();
			}
		}
	}