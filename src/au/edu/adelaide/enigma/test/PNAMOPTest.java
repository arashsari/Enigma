package au.edu.adelaide.enigma.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import au.edu.adelaide.enigma.provanance.MultipleGrainedPC;
import au.edu.adelaide.enigma.provanance.InformationServiceQuantizer;
import au.edu.adelaide.enigma.provanance.ProvenanceCollector;
import au.edu.adelaide.enigma.provanance.ProvenanceCollector2;
import au.edu.adelaide.kahn.pn.Builder;
import au.edu.adelaide.kahn.pn.Factory;
import au.edu.adelaide.kahn.pn.Network;
import au.edu.adelaide.kahn.pn.Process;
import au.edu.adelaide.pna.system.factory.PNAKahnFactory;
import au.edu.adelaide.pna.system.factory.PNAMOPFactory;
import au.edu.adelaide.pna.system.factory.PNACompFactory;

public class PNAMOPTest {
	/**
	 * @author Mehdi Sarikhani
	 * simple network sample in Survey 
	 * DataFlowStrategy and is a Process Network.
	 */
	public static void main(String[] args) {
	
		/*
		 * granularityLevel:
		 *   0 = fine-grained - DEFAULT 
		 *   1 = medium-grained
		 *   2 = coarse-grained 
		 *   9 = No Provenance
		 */
		InformationServiceQuantizer isq = InformationServiceQuantizer.getInstance();
		isq.setGranularityLevel(0);
		
		//Start Specification
		SimpleDateFormat simpleDateFormat =
				 new SimpleDateFormat("yyyy/dd/MM hh:mm:ss.SSS");
				 String output = " ***    ////////////    "+simpleDateFormat.format(new Date());
				 output = output +"   specificationStarted     /////////"+ " ";
				 System.out.println( output);
		
					/* setup the metalevel and customizations
					 * 
					 *  decide which factory do you need? just running 		 
					 *1- Just running kahn process netwrok
					 *2-running kahn process netwrok with metaObjectProtocol
					 *3-running kahn process netwrok with computation notation in MetaObject Protocol
					 *  
					*/	
//					Factory factory = PNAKahnFactory.newInstance();			 
					Factory factory = PNAMOPFactory.newInstance();
//					Factory factory = PNACompFactory.newInstance();

		// create a network
		Network network = factory.newNetwork(factory);
		Builder builder = factory.newBuilder(network);

		// create the processes
//		Integer Generator
	
		Process producer = factory.newProcess(network,
				"au.edu.adelaide.pna.processes.IntegerProducerWithToken");
		
		HashMap producerProperties = new HashMap();		
		producerProperties.put("Initial Value", "1");
		producerProperties.put("Upper Bound", "5");
		producer.updateProperties(producerProperties);
		builder.add(producer);
		

		Process producer1 = factory.newProcess(network,
				"au.edu.adelaide.pna.processes.IntegerProducerWithToken");
		
		HashMap producer1Properties = new HashMap();
		producer1Properties.put("Initial Value", "1");
		producer1Properties.put("Upper Bound", "5");
		producer1.updateProperties(producer1Properties);
		builder.add(producer1);
		
//		//		 Add process

		Process add =factory.newProcess(network,
				"au.edu.adelaide.pna.processes.ArithmaticProcessWithToken");
		HashMap addProperties = new HashMap();
		addProperties.put("operator","+");
		add.updateProperties(addProperties);
		builder.add(add);
		
//		Copy one input into to output
		
		Process splitter = factory.newProcess(network,
				"au.edu.adelaide.pna.processes.SplitterWithToken");
		builder.add(splitter);
		
//		Multiply process
		Process multiplyByConstantValue =factory.newProcess(network,
				"au.edu.adelaide.pna.processes.ArithmaticProcessWithConstantWithToken");
		
		HashMap multiplyByConstantValueProperties = new HashMap();
		multiplyByConstantValueProperties.put("operator","*");
		multiplyByConstantValueProperties.put("defaultValue","1");
		multiplyByConstantValueProperties.put("staticPort","0");
		multiplyByConstantValue.updateProperties(multiplyByConstantValueProperties);
		builder.add(multiplyByConstantValue);
		
//		Minus process
	
		Process minusByConstantValue =factory.newProcess(network,
				"au.edu.adelaide.pna.processes.ArithmaticProcessWithConstantWithToken");
		HashMap minusByConstantValueProperties = new HashMap();
		minusByConstantValueProperties.put("operator","-");
		minusByConstantValueProperties.put("defaultValue","0");
		minusByConstantValueProperties.put("staticPort","1");
		minusByConstantValue.updateProperties(minusByConstantValueProperties);
		builder.add(minusByConstantValue);
		
		
//		 Add process
		Process add1 =factory.newProcess(network,
				"au.edu.adelaide.pna.processes.ArithmaticProcessWithToken");
		
		HashMap add1Properties = new HashMap();
		add1Properties.put("operator","+");
		add1.updateProperties(add1Properties);
		builder.add(add1);
		
		
//		print network result

		Process consumer = factory.newProcess(network,
				"au.edu.adelaide.pna.processes.IntegerPrinterWithToken");
		builder.add(consumer);

//		 connect the producer and consumder - this would be done via the builder
		builder.connect(producer, producer.getOutputPort(0),add, add.getInputPort(0));
		builder.connect(producer1, producer1.getOutputPort(0), add, add.getInputPort(1));
		builder.connect(add, add.getOutputPort(0), splitter, splitter.getInputPort(0));
		builder.connect(splitter, splitter.getOutputPort(0), multiplyByConstantValue, multiplyByConstantValue.getInputPort(1));
		builder.connect(splitter, splitter.getOutputPort(1), minusByConstantValue, minusByConstantValue.getInputPort(0));
		builder.connect(multiplyByConstantValue, multiplyByConstantValue.getOutputPort(0), add1, add1.getInputPort(0));
		builder.connect(minusByConstantValue, minusByConstantValue.getOutputPort(0), add1, add1.getInputPort(1));
		builder.connect(add1, add1.getOutputPort(0), consumer, consumer.getInputPort(0));
//---------------------------------
			//Stop Specification
				 System.out.println( " ***    ////////////    "+simpleDateFormat.format(new Date())+"   specificationStoped    /////////"+ " ");
		//Start Execution
				 System.out.println( " ***    ////////////    "+simpleDateFormat.format(new Date())+"   ExecutionStarted    /////////"+ " ");	
			// start computation
		builder.trigger();
try
{
	Thread.sleep(5000);
	System.out.println("Exiting");
	PNAMOPTest.exceutionStop();
	System.exit(0);
	
}
catch (Exception e) {}
}
	public static void exceutionStop()
	{
	System.out.println("++++++++++++++ in stop executionStop");
//	ProvenanceCollector mip = ProvenanceCollector.getInstance();
//	mip.createOPMGraph();
	
//	ProvenanceCollector2 mip =ProvenanceCollector2.getInstance();
//	mip.createOPMGraph();
	
	
	MultipleGrainedPC CPC =MultipleGrainedPC.getInstance();
	CPC.createOPMGraph();
	}
}




