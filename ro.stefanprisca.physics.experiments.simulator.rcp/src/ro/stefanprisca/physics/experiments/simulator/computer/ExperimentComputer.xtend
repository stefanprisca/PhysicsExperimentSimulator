package ro.stefanprisca.physics.experiments.simulator.computer

import java.util.logging.Logger
import ro.stefanprisca.physics.experiments.simulator.core.Experiment
import ro.stefanprisca.physics.experiments.simulator.core.Function

class ExperimentComputer {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static FunctionComputer fComp = new FunctionComputer();
	
	def static compute(Experiment e){
		var double rez
		for(Function f: e.functions)
		{
				rez = fComp.computeFunction(f, e.variables)
				
				LOGGER.info("The result of function "+f.equation+" is:"+ rez)	
		}
		LOGGER.info("The final result of experiment < "+ e.name+" > is: "+rez)
		if (!fComp.compIsOk)
		{
			LOGGER.severe("\n********************\n"+
				"The experiment encountered some issues. Please check the log for messages marked with <SEVERE>!\n"+
			"********************")	
		}
		
	}
	
	
}