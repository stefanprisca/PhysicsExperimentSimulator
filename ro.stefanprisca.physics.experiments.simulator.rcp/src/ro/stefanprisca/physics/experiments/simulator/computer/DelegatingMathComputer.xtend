package ro.stefanprisca.physics.experiments.simulator.computer

import com.google.inject.Inject
import java.lang.reflect.Method
import ro.stefanprisca.physics.experiments.simulator.core.IComputer

import static org.eclipse.recommenders.utils.Checks.*

class DelegatingMathComputer implements IComputer {
	
	private Math delegate;
	def static getMathMethods(){
		var s = new String("")
		for(m:Math.methods){
			s=s+m.name+" "
		}
		return s
	}	
	
	@Inject
	new(){
		
	}
	
	override compute(String equation, Object... args) {
		delegateEquation(equation, args)
	}
	
	def double delegateEquation(String equation, Object... args){
		ensureIsNotNull(args)
		val method=if(args.length==1) Math.getMethod(equation, double) else Math.getMethod(equation, double, double) 
		var result = method.invoke(delegate, (method.getParameters(args) as double[]).toArray)
		return result as Double
	}
	
	def getParameters(Method m, Object... args){
		ensureIsNotNull(args)
		ensureIsTrue(m.parameterTypes.length.equals(args.length))
		//for the moment assume all arguments are doubles
		
		var parameters = newDoubleArrayOfSize(args.length)
		var i =0;
		for(arg : args)
		{
			parameters.set(i, (arg as Double))
			i = i+1
		}
		return parameters
		
	}
	
}