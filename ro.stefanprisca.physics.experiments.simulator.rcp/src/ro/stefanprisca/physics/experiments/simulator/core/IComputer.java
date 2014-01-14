/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.core;

import java.util.List;

import com.google.common.collect.Lists;
/**
 * Interface that defines an experiment Computer.
 * It contains all patterns and special variables required
 */
public interface IComputer {

	public static final String VARIABLE_PATTERN = "(\\{[a-zA-Z]+[0-9]*\\})";
	public static final String NUMBER_PATTERN = "([1-9][0-9]*\\.[0-9]*)";

	public static final String MATHOPERATOR_PATTERN = "[*/+-]";
	public static final String MATHEQUALITY_PATTERN = "=";
	public static final String MATHFUNCTION_PATTERN = "([a-z]+\\(" + "("
			+ NUMBER_PATTERN + "|" + VARIABLE_PATTERN + ")?" + "(\\," + "("
			+ NUMBER_PATTERN + "|" + VARIABLE_PATTERN + ")" + ")*\\))";

	public static final String OPERAND_PATTERN = "(" + NUMBER_PATTERN + "|"
			+ VARIABLE_PATTERN + "|" + MATHFUNCTION_PATTERN + ")";
	public static final String BINARY_OPERATION = OPERAND_PATTERN
			+ MATHOPERATOR_PATTERN + OPERAND_PATTERN;
	public static final String UNARY_OPERATION = "\\(-" + OPERAND_PATTERN
			+ "\\)";

	public static final String NOPARANTH_OPERATION_PATTERN = "("
			+ BINARY_OPERATION + "|" + UNARY_OPERATION + "|"
			+ MATHFUNCTION_PATTERN + ")";
	public static final String PARANTHESISOP_PATTERN = "\\("
			+ NOPARANTH_OPERATION_PATTERN + "\\)";
	public static final String OPERATION_PATTERN = PARANTHESISOP_PATTERN + "|"
			+ NOPARANTH_OPERATION_PATTERN;
	public static final String ASSIGNMENT_PATTERN = VARIABLE_PATTERN
			+ MATHEQUALITY_PATTERN + ".+";

	public static final double G = 9.18;
	
	public static final List<String> SPECIAL_VARS = Lists.newArrayList("PI","G", "E");
	
	/**
	 * Method used to compute experiments.
	 * Implementation depends on what you need to compute.
	 * @param equation A string describing the operation to be computed
	 * @param arguments An array containing parameters for the operation
	 * @return
	 * @throws Exception
	 */
	public double compute(String equation, Object... arguments)
			throws Exception;

}
