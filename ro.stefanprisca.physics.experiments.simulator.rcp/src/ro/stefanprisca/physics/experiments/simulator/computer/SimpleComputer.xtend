package ro.stefanprisca.physics.experiments.simulator.computer

import java.math.BigDecimal
import java.util.regex.Pattern
import ro.stefanprisca.physics.experiments.simulator.core.IComputer

/**
 * Class used for simple computations like additions, etc
 */
class SimpleComputer implements IComputer {
	
	var private static scale = 12;
	
	def public static void setScale(int sc){
		scale = sc
	}
	
	override compute(String equation, Object... arguments) throws IllegalArgumentException{
		
		
		var result= new BigDecimal(arguments.get(0) as Double).setScale(scale, BigDecimal.ROUND_HALF_UP)
		var arg2 = new BigDecimal(arguments.get(1) as Double).setScale(scale, BigDecimal.ROUND_HALF_UP)
		var operator = [String eq | 
						var matcher = Pattern.compile(MATHOPERATOR_PATTERN).matcher(eq)
						if(matcher.find)return matcher.group
		].apply(equation)
		switch (operator){
			case "+":result=result.add(arg2) 
			case "-":result=result.subtract(arg2)
			case "/":{
				if(arg2.doubleValue != 0)
				{
					result=result.divide(arg2, BigDecimal.ROUND_HALF_UP)
					
				}else{
					throw new IllegalArgumentException();
				}
				
			}
			case "*":result=result.multiply(arg2)
			
		}
		return result.doubleValue
	}
	
}