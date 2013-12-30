package ro.stefanprisca.physics.experiments.simulator.core;

public interface IComputer {

	
	public static final String MATHOPERATOR_PATTERN = "[*/+-]";
	public static final String MATHEQUALITY_PATTERN = "=";
	public static final String VARIABLE_PATTERN = "(\\{[a-z]+\\})";
	public static final String NUMBER_PATTERN = "([0-9][0-9]*\\.[0-9]*)";
	public static final String MATHFUNCTION_PATTERN = "([a-z]+\\("+"("+NUMBER_PATTERN+"|"+VARIABLE_PATTERN+")"+"(\\,"+
														"("+NUMBER_PATTERN+"|"+VARIABLE_PATTERN+")"+")*\\))";

	public static final String OPERAND_PATTERN = "("+NUMBER_PATTERN+"|"+VARIABLE_PATTERN+"|"+MATHFUNCTION_PATTERN+")" ;
	public static final String BINARY_OPERATION = OPERAND_PATTERN + MATHOPERATOR_PATTERN + OPERAND_PATTERN;
	public static final String UNARY_OPERATION = "\\(-"+OPERAND_PATTERN+"\\)";
	
	
	public static final String NOPARANTH_OPERATION_PATTERN = "("+BINARY_OPERATION+"|"+UNARY_OPERATION+"|"+MATHFUNCTION_PATTERN+")";
	public static final String PARANTHESISOP_PATTERN = "\\("+NOPARANTH_OPERATION_PATTERN+"\\)";
	public static final String OPERATION_PATTERN= PARANTHESISOP_PATTERN + "|" +NOPARANTH_OPERATION_PATTERN;
	public static final String ASSIGNMENT_PATTERN = VARIABLE_PATTERN + MATHEQUALITY_PATTERN + ".+";
	
	public double compute(String equation, Object...arguments) throws Exception;
	
}
