package ro.stefanprisca.physics.experiments.simulator.computer

import java.util.logging.Logger
import ro.stefanprisca.physics.experiments.simulator.core.Experiment
import ro.stefanprisca.physics.experiments.simulator.core.Function
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger

class ExperimentComputer {
	private final static Logger LOGGER = ExperimentLogger.instance;
	
	private static FunctionComputer fComp = new FunctionComputer();
	
	def static compute(Experiment e){
		
		var double rez
		LOGGER.fine("\n********************************************"+
							"\n\tStarted experiment < "+e.name+" >"+
					"\n********************************************\n");
		for(Function f: e.functions)
		{
				rez = fComp.computeFunction(f, e.variables)
				
				LOGGER.info("The result of function "+f.equation+" is:"+ rez)
					
		}
		LOGGER.info("The final result of experiment < "+ e.name+" > is: "+rez)
		LOGGER.fine("\n********************************************"+
							"\n\tExperiment < "+e.name+" > concluded with result "+ rez +
					"\n********************************************\n");		
	}
	
	def static compute(Experiment e, String period){
		var double rez
		var t = Integer.parseInt(period)
		while(t>0){
			LOGGER.fine("\n********************************************"+
							"\n\tRunning experiment < "+e.name+" > at T: "+t+
					"\n********************************************\n");
		for(Function f: e.functions)
		{
				rez = fComp.computeFunction(f, e.variables)
				
				LOGGER.info("The result of function "+f.equation+" is:"+ rez)
					
		}
		LOGGER.info("The final result of experiment < "+ e.name+" > is: "+rez)
		LOGGER.fine("\n********************************************"+
							"\n\tExperiment < "+e.name+" > concluded with result "+ rez +" at T: "+t+
					"\n********************************************\n");		
		t=t-1
		}
	}
	
	
}