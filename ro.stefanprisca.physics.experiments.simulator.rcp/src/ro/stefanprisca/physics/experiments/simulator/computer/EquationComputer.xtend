package ro.stefanprisca.physics.experiments.simulator.computer

import com.google.common.annotations.VisibleForTesting
import java.util.List
import java.util.logging.Logger
import java.util.regex.Pattern
import ro.stefanprisca.physics.experiments.simulator.core.IComputer
import ro.stefanprisca.physics.experiments.simulator.core.Variable
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger

/**
 * Class that computes a single equation from the experiment.<br>
 * Searches the eq for specific patterns and delegates the simple computations to either the
 * math computer or the simple computer delegates.
 */
class EquationComputer implements IComputer {
	private DelegatingMathComputer mathComputer = new DelegatingMathComputer
	private SimpleComputer simpleComputer = new SimpleComputer;
	private static Logger LOGGER = ExperimentLogger.instance

	/**
	 * Starts the computation for an equation, given the experiment variables.
	 * Also eliminates all the ws from the equation
	 */
	def public computeEquation(String f, List<Variable> variables) throws Exception
	{
		var equation = f.replaceAll("\\s+", "");
		computeEquationWithAssignment(equation, variables.toArray)
	}

	/**
	 * Checks for assignments in equations and if there is the case, it will keep the
	 * result of the equation in the right-hand variable
	 */
	def computeEquationWithAssignment(String equationFinal, Object... arguments) throws Exception
	{
		var matcher = Pattern.compile(IComputer.ASSIGNMENT_PATTERN).matcher(equationFinal)
		if (matcher.find) {
			var assignment = equationFinal.split(MATHEQUALITY_PATTERN)
			var vari = assignment.get(0).substring(assignment.get(0).indexOf("{") + 1, assignment.get(0).indexOf("}"))
			var result = vari.getVariable(arguments)
			var resultValue = if(assignment.get(1).matches(IComputer.OPERAND_PATTERN))
									assignment.get(1).computeOperand(arguments)
								else
									assignment.get(1).compute(arguments)
									
			result.setValue(resultValue)
			return result.value
		} else
			return equationFinal.compute(arguments)
	}

	/**
	 * The actual computation of an equation.
	 * Taking terms 2 by 2 and replacing them in the equation with the computation result until
	 * there are no more 2 terms free for taking, and then it evaluates the final result.
	 */
	override compute(String equationFinal, Object... arguments) throws Exception
	{
		var equation = equationFinal
		var double result
		var intermediates = newDoubleArrayOfSize(2)
		var boolean stillFinds = true
		while (stillFinds) {
			var matcher = Pattern.compile(IComputer.OPERATION_PATTERN).matcher(equation)
			stillFinds = false
			while (matcher.find) {
				stillFinds = true
				var operation = matcher.group
				LOGGER.info("The operation is: " + operation)

				//break the operation parenthesis
				var compOperation = [ String eq |
					var matcherInternal = Pattern.compile(IComputer.NOPARANTH_OPERATION_PATTERN).matcher(eq);
					if(matcherInternal.find) return matcherInternal.group
				].apply(operation)

				var double intermediateResult

				if (operation.matches(UNARY_OPERATION)) {

					var operand = operation.replaceAll("[()-]", "");
					LOGGER.info("Operand to be negated is: " + operand)
					intermediateResult = -operand.computeOperand(arguments)
				} else {
					var nextInter = 0
					intermediates = newDoubleArrayOfSize(2)

					for (operand : compOperation.split(MATHOPERATOR_PATTERN)) {
						LOGGER.info("Operand is: " + operand)
						intermediates.set(nextInter, operand.computeOperand(arguments))
						LOGGER.info(
							"The result of the intermediate operation " + operand + " is: " +
								intermediates.get(nextInter))
						nextInter = nextInter + 1
					}
					LOGGER.info("The intermediate results are " + intermediates.toString)
					intermediateResult = simpleComputer.compute(compOperation, intermediates.get(0),
						intermediates.get(1))

				}
				LOGGER.info(
					"The result of operation " + compOperation + " is: " + intermediateResult
				)
				equation = equation.replace(operation, "" + intermediateResult);
				LOGGER.info("The equation now is: " + equation);

			}
		}
		result = Double.parseDouble(equation)
		if (result.equals(Double.NaN)) {
			LOGGER.severe("Something very wrong happened! Please check the numbers and the console log")
		}
		return result
	}

	/**
	 * computes the value of an operand from the equation
	 */
	def private computeOperand(String operand, Object... arguments) {
		if (operand.matches(MATHFUNCTION_PATTERN)) {
			val method = operand.substring(0, operand.indexOf("("))
			var m = Pattern.compile(method).matcher(DelegatingMathComputer.mathMethods)

			var arg = getVariables(operand, arguments)
			LOGGER.info("Arguments for the math function are: " + arg.toString)
			if (m.find())
				return mathComputer.compute(method, arg.toArray)
			else
				throw new IllegalArgumentException("The math function specified does not exist!")
		} else if (operand.matches(VARIABLE_PATTERN)) {
			var vari = operand.substring(operand.indexOf("{") + 1, operand.indexOf("}"))
			return vari.getVariableValue(arguments)
		} else if (operand.matches(NUMBER_PATTERN)) {
			return Double.parseDouble(operand)
		}

	}
	/**
	 * gets the variable values for the math function
	 */
	def private getVariables(String s, Object... arguments) {
		var ss = s.substring(s.indexOf("(") + 1, s.indexOf(")"))
		if(ss.empty){
			return newDoubleArrayOfSize(0)
		}
		var rez = newDoubleArrayOfSize(arguments.length)
		var i = -1

		for (sVar : ss.split(",")) {
			i = i + 1
			if (sVar.matches(VARIABLE_PATTERN))
				rez.set(i, getVariableValue(sVar.substring(sVar.indexOf("{") + 1, sVar.indexOf("}")), arguments))
			else
				rez.set(i, Double.parseDouble(sVar))
		}
		var finalResult = newDoubleArrayOfSize(i + 1)
		while (i >= 0) {
			finalResult.set(i, rez.get(i))
			i = i - 1
		}
		return finalResult
	}

	/**
	 * returns the value of the variable with id
	 */
	def private getVariableValue(String id, Object... variables) {

		if(id.equals("PI")){
			return Math.PI
		}else if(id.equals("E")){
			return Math.E
		}if(id.equals("G")){
			return IComputer.G
		}
		for (variable : variables) {

			if((variable as Variable).id.equals(id)) return (variable as Variable).value
		}
		throw new IllegalArgumentException("Variable " + id+" could not be found! Please ensure it is declared.")
	}

	/**
	 * Returns the Variable object corresponding to the variable with ID = id 
	 */
	def private getVariable(String id, Object... variables) {
		for (variable : variables) {

			if((variable as Variable).id.equals(id)) return (variable as Variable)
		}
	}
	
	/**
	 * Sets the logger to use for testing
	 */
	@VisibleForTesting
	def public setLogger(Logger logger){
		LOGGER = logger
	}

}
