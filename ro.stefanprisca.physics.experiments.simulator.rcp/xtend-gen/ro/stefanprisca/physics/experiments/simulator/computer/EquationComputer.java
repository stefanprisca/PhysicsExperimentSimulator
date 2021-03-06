package ro.stefanprisca.physics.experiments.simulator.computer;

import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import ro.stefanprisca.physics.experiments.simulator.computer.DelegatingMathComputer;
import ro.stefanprisca.physics.experiments.simulator.computer.SimpleComputer;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger;

/**
 * Class that computes a single equation from the experiment.<br>
 * Searches the eq for specific patterns and delegates the simple computations to either the
 * math computer or the simple computer delegates.
 */
@SuppressWarnings("all")
public class EquationComputer implements IComputer {
  private DelegatingMathComputer mathComputer = new Function0<DelegatingMathComputer>() {
    public DelegatingMathComputer apply() {
      DelegatingMathComputer _delegatingMathComputer = new DelegatingMathComputer();
      return _delegatingMathComputer;
    }
  }.apply();
  
  private SimpleComputer simpleComputer = new Function0<SimpleComputer>() {
    public SimpleComputer apply() {
      SimpleComputer _simpleComputer = new SimpleComputer();
      return _simpleComputer;
    }
  }.apply();
  
  private static Logger LOGGER = ExperimentLogger.getInstance();
  
  /**
   * Starts the computation for an equation, given the experiment variables.
   * Also eliminates all the ws from the equation
   */
  public double computeEquation(final String f, final List<Variable> variables) throws Exception {
    double _xblockexpression = (double) 0;
    {
      String equation = f.replaceAll("\\s+", "");
      Object[] _array = variables.toArray();
      double _computeEquationWithAssignment = this.computeEquationWithAssignment(equation, _array);
      _xblockexpression = (_computeEquationWithAssignment);
    }
    return _xblockexpression;
  }
  
  /**
   * Checks for assignments in equations and if there is the case, it will keep the
   * result of the equation in the right-hand variable
   */
  public double computeEquationWithAssignment(final String equationFinal, final Object... arguments) throws Exception {
    Pattern _compile = Pattern.compile(IComputer.ASSIGNMENT_PATTERN);
    Matcher matcher = _compile.matcher(equationFinal);
    boolean _find = matcher.find();
    if (_find) {
      String[] assignment = equationFinal.split(IComputer.MATHEQUALITY_PATTERN);
      String _get = assignment[0];
      String _get_1 = assignment[0];
      int _indexOf = _get_1.indexOf("{");
      int _plus = (_indexOf + 1);
      String _get_2 = assignment[0];
      int _indexOf_1 = _get_2.indexOf("}");
      String vari = _get.substring(_plus, _indexOf_1);
      Variable result = this.getVariable(vari, arguments);
      double _xifexpression = (double) 0;
      String _get_3 = assignment[1];
      boolean _matches = _get_3.matches(IComputer.OPERAND_PATTERN);
      if (_matches) {
        String _get_4 = assignment[1];
        double _computeOperand = this.computeOperand(_get_4, arguments);
        _xifexpression = _computeOperand;
      } else {
        String _get_5 = assignment[1];
        double _compute = this.compute(_get_5, arguments);
        _xifexpression = _compute;
      }
      double resultValue = _xifexpression;
      result.setValue(resultValue);
      return result.getValue();
    } else {
      return this.compute(equationFinal, arguments);
    }
  }
  
  /**
   * The actual computation of an equation.
   * Taking terms 2 by 2 and replacing them in the equation with the computation result until
   * there are no more 2 terms free for taking, and then it evaluates the final result.
   */
  public double compute(final String equationFinal, final Object... arguments) throws Exception {
    String equation = equationFinal;
    double result = 0;
    double[] intermediates = new double[2];
    boolean stillFinds = true;
    boolean _while = stillFinds;
    while (_while) {
      {
        Pattern _compile = Pattern.compile(IComputer.OPERATION_PATTERN);
        Matcher matcher = _compile.matcher(equation);
        stillFinds = false;
        boolean _find = matcher.find();
        boolean _while_1 = _find;
        while (_while_1) {
          {
            stillFinds = true;
            String operation = matcher.group();
            EquationComputer.LOGGER.info(("The operation is: " + operation));
            final Function1<String,String> _function = new Function1<String,String>() {
              public String apply(final String eq) {
                Pattern _compile = Pattern.compile(IComputer.NOPARANTH_OPERATION_PATTERN);
                Matcher matcherInternal = _compile.matcher(eq);
                boolean _find = matcherInternal.find();
                if (_find) {
                  return matcherInternal.group();
                }
                return null;
              }
            };
            String compOperation = _function.apply(operation);
            double intermediateResult = 0;
            boolean _matches = operation.matches(IComputer.UNARY_OPERATION);
            if (_matches) {
              String operand = operation.replaceAll("[()-]", "");
              EquationComputer.LOGGER.info(("Operand to be negated is: " + operand));
              double _computeOperand = this.computeOperand(operand, arguments);
              double _minus = (-_computeOperand);
              intermediateResult = _minus;
            } else {
              int nextInter = 0;
              double[] _newDoubleArrayOfSize = new double[2];
              intermediates = _newDoubleArrayOfSize;
              String[] _split = compOperation.split(IComputer.MATHOPERATOR_PATTERN);
              for (final String operand_1 : _split) {
                {
                  EquationComputer.LOGGER.info(("Operand is: " + operand_1));
                  double _computeOperand_1 = this.computeOperand(operand_1, arguments);
                  intermediates[nextInter] = _computeOperand_1;
                  double _get = intermediates[nextInter];
                  String _plus = ((("The result of the intermediate operation " + operand_1) + " is: ") + Double.valueOf(_get));
                  EquationComputer.LOGGER.info(_plus);
                  nextInter = (nextInter + 1);
                }
              }
              final double[] _converted_intermediates = (double[])intermediates;
              String _string = ((List<Double>)Conversions.doWrapArray(_converted_intermediates)).toString();
              String _plus = ("The intermediate results are " + _string);
              EquationComputer.LOGGER.info(_plus);
              double _get = intermediates[0];
              double _get_1 = intermediates[1];
              double _compute = this.simpleComputer.compute(compOperation, Double.valueOf(_get), Double.valueOf(_get_1));
              intermediateResult = _compute;
            }
            EquationComputer.LOGGER.info(
              ((("The result of operation " + compOperation) + " is: ") + Double.valueOf(intermediateResult)));
            String _replace = equation.replace(operation, ("" + Double.valueOf(intermediateResult)));
            equation = _replace;
            EquationComputer.LOGGER.info(("The equation now is: " + equation));
          }
          boolean _find_1 = matcher.find();
          _while_1 = _find_1;
        }
      }
      _while = stillFinds;
    }
    double _parseDouble = Double.parseDouble(equation);
    result = _parseDouble;
    boolean _equals = Double.valueOf(result).equals(Double.valueOf(Double.NaN));
    if (_equals) {
      EquationComputer.LOGGER.severe("Something very wrong happened! Please check the numbers and the console log");
    }
    return result;
  }
  
  /**
   * computes the value of an operand from the equation
   */
  private double computeOperand(final String operand, final Object... arguments) {
    boolean _matches = operand.matches(IComputer.MATHFUNCTION_PATTERN);
    if (_matches) {
      int _indexOf = operand.indexOf("(");
      final String method = operand.substring(0, _indexOf);
      Pattern _compile = Pattern.compile(method);
      String _mathMethods = DelegatingMathComputer.getMathMethods();
      Matcher m = _compile.matcher(_mathMethods);
      double[] arg = this.getVariables(operand, arguments);
      final double[] _converted_arg = (double[])arg;
      String _string = ((List<Double>)Conversions.doWrapArray(_converted_arg)).toString();
      String _plus = ("Arguments for the math function are: " + _string);
      EquationComputer.LOGGER.info(_plus);
      boolean _find = m.find();
      if (_find) {
        final double[] _converted_arg_1 = (double[])arg;
        Object[] _array = ((List<Double>)Conversions.doWrapArray(_converted_arg_1)).toArray();
        return this.mathComputer.compute(method, _array);
      } else {
        IllegalArgumentException _illegalArgumentException = new IllegalArgumentException("The math function specified does not exist!");
        throw _illegalArgumentException;
      }
    } else {
      boolean _matches_1 = operand.matches(IComputer.VARIABLE_PATTERN);
      if (_matches_1) {
        int _indexOf_1 = operand.indexOf("{");
        int _plus_1 = (_indexOf_1 + 1);
        int _indexOf_2 = operand.indexOf("}");
        String vari = operand.substring(_plus_1, _indexOf_2);
        return this.getVariableValue(vari, arguments);
      } else {
        boolean _matches_2 = operand.matches(IComputer.NUMBER_PATTERN);
        if (_matches_2) {
          return Double.parseDouble(operand);
        }
      }
    }
    return 0;
  }
  
  /**
   * gets the variable values for the math function
   */
  private double[] getVariables(final String s, final Object... arguments) {
    int _indexOf = s.indexOf("(");
    int _plus = (_indexOf + 1);
    int _indexOf_1 = s.indexOf(")");
    String ss = s.substring(_plus, _indexOf_1);
    boolean _isEmpty = ss.isEmpty();
    if (_isEmpty) {
      return new double[0];
    }
    int _length = arguments.length;
    double[] rez = new double[_length];
    int i = (-1);
    String[] _split = ss.split(",");
    for (final String sVar : _split) {
      {
        i = (i + 1);
        boolean _matches = sVar.matches(IComputer.VARIABLE_PATTERN);
        if (_matches) {
          int _indexOf_2 = sVar.indexOf("{");
          int _plus_1 = (_indexOf_2 + 1);
          int _indexOf_3 = sVar.indexOf("}");
          String _substring = sVar.substring(_plus_1, _indexOf_3);
          double _variableValue = this.getVariableValue(_substring, arguments);
          rez[i] = _variableValue;
        } else {
          double _parseDouble = Double.parseDouble(sVar);
          rez[i] = _parseDouble;
        }
      }
    }
    double[] finalResult = new double[(i + 1)];
    boolean _while = (i >= 0);
    while (_while) {
      {
        double _get = rez[i];
        finalResult[i] = _get;
        i = (i - 1);
      }
      _while = (i >= 0);
    }
    return finalResult;
  }
  
  /**
   * returns the value of the variable with id
   */
  private double getVariableValue(final String id, final Object... variables) {
    boolean _equals = id.equals("PI");
    if (_equals) {
      return Math.PI;
    } else {
      boolean _equals_1 = id.equals("E");
      if (_equals_1) {
        return Math.E;
      }
    }
    boolean _equals_2 = id.equals("G");
    if (_equals_2) {
      return IComputer.G;
    }
    for (final Object variable : variables) {
      String _id = ((Variable) variable).getId();
      boolean _equals_3 = _id.equals(id);
      if (_equals_3) {
        return ((Variable) variable).getValue();
      }
    }
    IllegalArgumentException _illegalArgumentException = new IllegalArgumentException((("Variable " + id) + " could not be found! Please ensure it is declared."));
    throw _illegalArgumentException;
  }
  
  /**
   * Returns the Variable object corresponding to the variable with ID = id
   */
  private Variable getVariable(final String id, final Object... variables) {
    for (final Object variable : variables) {
      String _id = ((Variable) variable).getId();
      boolean _equals = _id.equals(id);
      if (_equals) {
        return ((Variable) variable);
      }
    }
    return null;
  }
  
  /**
   * Sets the logger to use for testing
   */
  @VisibleForTesting
  public Logger setLogger(final Logger logger) {
    Logger _LOGGER = EquationComputer.LOGGER = logger;
    return _LOGGER;
  }
}
