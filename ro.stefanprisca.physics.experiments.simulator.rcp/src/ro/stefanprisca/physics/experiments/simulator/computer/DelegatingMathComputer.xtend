package ro.stefanprisca.physics.experiments.simulator.computer

import com.google.inject.Inject
import java.lang.reflect.Method
import ro.stefanprisca.physics.experiments.simulator.core.IComputer

import static org.eclipse.recommenders.utils.Checks.*

/**
 * Class delegating methods to the java.lang.Math class to compute all sorts of operations
 */
class DelegatingMathComputer implements IComputer {
	
	private Math delegate;
	/**
	 * Returns a String containing the names of all Math methods.
	 * Use this when checking for methods inside the java.lang.Math class
	 */
	def static getMathMethods(){
		var s = new String("")
		for(m:Math.methods){
			s=s+m.name+" "
		}
		return s
	}	
	/**
	 * Returns a list of Strings containing all the java.lang.Math methods
	 */
	def  static getPrettyMathMethodsArray(){
		var s = newArrayList()
		for(m : Math.methods){
			var name = m.name + "(" + [|
				var ss = "" 
				for(par: m.parameterTypes){
					ss = ss + ", " + par.name 
				}
				return ss.replaceFirst(", ", "")
			].apply() + ")"
			
			s.add(name)
		}
		return s.toArray
	}
	
	
	@Inject
	new(){
		
	}
	
	/**
	 * Computes the equation by delegating to the proper Math method
	 */
	override compute(String equation, Object... args) {
		ensureIsNotNull(args)
		val method=if(args.length==1) Math.getMethod(equation, double) 
					else if(args.length==2) Math.getMethod(equation, double, double) 
					else Math.getMethod(equation)
		var result = method.invoke(delegate, (method.getParameters(args) as double[]).toArray)
		return result as Double
	}
	/**
	 * Obtain the parameters for the method
	 */
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