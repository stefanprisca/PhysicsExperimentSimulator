package ro.stefanprisca.physics.experiments.simulator.computer

import com.google.common.collect.Lists
import java.util.logging.Logger
import ro.stefanprisca.physics.experiments.simulator.core.Experiment
import ro.stefanprisca.physics.experiments.simulator.core.Variable
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger
import com.google.common.annotations.VisibleForTesting

class ExperimentComputer {
	private static Logger LOGGER = ExperimentLogger.instance;
	
	private static FunctionComputer fComp = new FunctionComputer();
	
	
	
	
	def static compute(Experiment e){
		
		var double rez
		LOGGER.fine("\n********************************************"+
							"\n\tStarted experiment < "+e.name+" >"+
					"\n********************************************\n");
		for(String f: e.functions)
		{
				var args = Lists.newArrayList
				for(vari: e.variables){
					args.add(new Variable(vari.id, vari.value))
				}
				try{
				rez = fComp.computeFunction(f, args)
				
				LOGGER.info("The result of equation "+f+" is:"+ rez)
				}catch(IllegalArgumentException ilae){
					LOGGER.severe("There were argument exceptions in equation "+f+" !\n"+
						"Please check for divisions by zero, invalid math operations, or undeclared variables! \n"+ilae.message)
					return
				}catch (NumberFormatException nfe){
					LOGGER.severe("There were number format issues in equation "+f+" !\n"+
					"Make sure all numbers are in < x.x > decimal format!(e.g. 10.0).\n"+nfe.message)
					return
				}catch(Exception ex){
					LOGGER.severe("Something went wrong in equation "+f+" !\n"+
						"Please check the following message and the console output! \n"+ex.message)
					return
				}
		}
		LOGGER.info("The final result of experiment < "+ e.name+" > is: "+rez)
		LOGGER.fine("\n********************************************"+
							"\n\tExperiment < "+e.name+" > concluded with result "+ rez +
					"\n********************************************\n");		
	}
	
	def static compute(Experiment e, String period){
		var double rez
		var t = Integer.parseInt(period)
		var args = Lists.newArrayList
		for(vari: e.variables){
			args.add(new Variable(vari.id, vari.value))
		}
		while(t>0){
			LOGGER.fine("\n********************************************"+
							"\n\tRunning experiment < "+e.name+" > at T: "+t+
					"\n********************************************\n");
		for(String f: e.functions)
		{
				try{
				rez = fComp.computeFunction(f, args)
				
				LOGGER.info("The result of equation "+f+" is:"+ rez)
				}catch (NumberFormatException nfe){
					LOGGER.severe("There were number format issues in equation "+f+" !\n"+
					"Make sure all numbers are in < x.x > decimal format!(e.g. 10.0).\n"+nfe.message)
					return
				}catch(IllegalArgumentException ilae){
					LOGGER.severe("There were argument exceptions in equation "+f+" !\n"+
						"This may be due to a division by zero")
					return
				}catch(Exception ex){
					LOGGER.severe("Something went wrong in equation "+f+" !\n"+
						"Please check the following message and the console output! \n"+ex.message)
					return
				}
					
		}
		LOGGER.info("The final result of experiment < "+ e.name+" > is: "+rez)
		LOGGER.fine("\n********************************************"+
							"\n\tExperiment < "+e.name+" > concluded with result "+ rez +" at T: "+t+
					"\n********************************************\n");		
		t=t-1
		}
		
	}
	
	
	@VisibleForTesting
	def public setLogger(Logger logger){
		LOGGER = logger
	}
}