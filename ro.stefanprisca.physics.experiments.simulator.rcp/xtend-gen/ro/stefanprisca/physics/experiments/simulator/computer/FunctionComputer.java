package ro.stefanprisca.physics.experiments.simulator.computer;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import ro.stefanprisca.physics.experiments.simulator.computer.DelegatingMathComputer;
import ro.stefanprisca.physics.experiments.simulator.computer.SimpleComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger;

@SuppressWarnings("all")
public class FunctionComputer implements IComputer {
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
  
  private final static Logger LOGGER = new Function0<Logger>() {
    public Logger apply() {
      Logger _instance = ExperimentLogger.getInstance();
      return _instance;
    }
  }.apply();
  
  public double computeFunction(final Function f, final List<Variable> variables) {
    double _xblockexpression = (double) 0;
    {
      String _equation = f.getEquation();
      String equation = _equation.replaceAll("\\s+", "");
      Object[] _array = variables.toArray();
      double _computeEquationWithAssignment = this.computeEquationWithAssignment(equation, _array);
      _xblockexpression = (_computeEquationWithAssignment);
    }
    return _xblockexpression;
  }
  
  public double computeEquationWithAssignment(final String equationFinal, final Object... arguments) {
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
      String _get_3 = assignment[1];
      double resultValue = this.compute(_get_3, arguments);
      result.setValue(resultValue);
      return result.getValue();
    } else {
      return this.compute(equationFinal, arguments);
    }
  }
  
  public double compute(final String equationFinal, final Object... arguments) {
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
            String _plus = ("The operation is: " + operation);
            FunctionComputer.LOGGER.info(_plus);
            final Function1<String,String> _function = new Function1<String,String>() {
              public String apply(final String eq) {
                String _xblockexpression = null;
                {
                  Pattern _compile = Pattern.compile(IComputer.NOPARANTH_OPERATION_PATTERN);
                  Matcher matcherInternal = _compile.matcher(eq);
                  String _xifexpression = null;
                  boolean _find = matcherInternal.find();
                  if (_find) {
                    return matcherInternal.group();
                  }
                  _xblockexpression = (_xifexpression);
                }
                return _xblockexpression;
              }
            };
            String compOperation = _function.apply(operation);
            double intermediateResult = 0;
            boolean _matches = operation.matches(IComputer.UNARY_OPERATION);
            if (_matches) {
              String operand = operation.replaceAll("[()-]", "");
              String _plus_1 = ("Operand to be negated is: " + operand);
              FunctionComputer.LOGGER.info(_plus_1);
              Double _computeOperand = this.computeOperand(operand, arguments);
              double _minus = DoubleExtensions.operator_minus(_computeOperand);
              intermediateResult = _minus;
            } else {
              int nextInter = 0;
              double[] _newDoubleArrayOfSize = new double[2];
              intermediates = _newDoubleArrayOfSize;
              String[] _split = compOperation.split(IComputer.MATHOPERATOR_PATTERN);
              for (final String operand_1 : _split) {
                {
                  String _plus_2 = ("Operand is: " + operand_1);
                  FunctionComputer.LOGGER.info(_plus_2);
                  Double _computeOperand_1 = this.computeOperand(operand_1, arguments);
                  intermediates[nextInter] = (_computeOperand_1).doubleValue();
                  String _plus_3 = ("The result of the intermediate operation " + operand_1);
                  String _plus_4 = (_plus_3 + " is: ");
                  double _get = intermediates[nextInter];
                  String _plus_5 = (_plus_4 + Double.valueOf(_get));
                  FunctionComputer.LOGGER.info(_plus_5);
                  int _plus_6 = (nextInter + 1);
                  nextInter = _plus_6;
                }
              }
              final double[] _converted_intermediates = (double[])intermediates;
              String _string = ((List<Double>)Conversions.doWrapArray(_converted_intermediates)).toString();
              String _plus_2 = ("The intermediate results are " + _string);
              FunctionComputer.LOGGER.info(_plus_2);
              try {
                double _get = intermediates[0];
                double _get_1 = intermediates[1];
                double _compute = this.simpleComputer.compute(compOperation, Double.valueOf(_get), Double.valueOf(_get_1));
                intermediateResult = _compute;
              } catch (final Throwable _t) {
                if (_t instanceof IllegalArgumentException) {
                  final IllegalArgumentException ila = (IllegalArgumentException)_t;
                  String _plus_3 = ("Division by zero occurred in operation " + compOperation);
                  String _plus_4 = (_plus_3 + "!");
                  FunctionComputer.LOGGER.severe(_plus_4);
                  String _message = ila.getMessage();
                  FunctionComputer.LOGGER.severe(_message);
                } else {
                  throw Exceptions.sneakyThrow(_t);
                }
              }
            }
            String _plus_5 = ("The result of operation " + compOperation);
            String _plus_6 = (_plus_5 + " is: ");
            String _plus_7 = (_plus_6 + Double.valueOf(intermediateResult));
            FunctionComputer.LOGGER.info(_plus_7);
            String _plus_8 = ("" + Double.valueOf(intermediateResult));
            String _replace = equation.replace(operation, _plus_8);
            equation = _replace;
            String _plus_9 = ("The equation now is: " + equation);
            FunctionComputer.LOGGER.info(_plus_9);
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
      FunctionComputer.LOGGER.severe("Something very wrong happened! Please check the numbers and the console log");
    }
    return result;
  }
  
  private Double computeOperand(final String operand, final Object... arguments) {
    Double _xifexpression = null;
    boolean _matches = operand.matches(IComputer.MATHFUNCTION_PATTERN);
    if (_matches) {
      Double _xblockexpression = null;
      {
        int _indexOf = operand.indexOf("(");
        final String method = operand.substring(0, _indexOf);
        Pattern _compile = Pattern.compile(method);
        String _mathMethods = DelegatingMathComputer.getMathMethods();
        Matcher m = _compile.matcher(_mathMethods);
        double[] arg = this.getVariables(operand, arguments);
        final double[] _converted_arg = (double[])arg;
        String _string = ((List<Double>)Conversions.doWrapArray(_converted_arg)).toString();
        String _plus = ("Arguments for the math function are: " + _string);
        FunctionComputer.LOGGER.info(_plus);
        Double _xifexpression_1 = null;
        boolean _find = m.find();
        if (_find) {
          final double[] _converted_arg_1 = (double[])arg;
          Object[] _array = ((List<Double>)Conversions.doWrapArray(_converted_arg_1)).toArray();
          return this.mathComputer.compute(method, _array);
        }
        _xblockexpression = (_xifexpression_1);
      }
      _xifexpression = _xblockexpression;
    } else {
      Double _xifexpression_1 = null;
      boolean _matches_1 = operand.matches(IComputer.VARIABLE_PATTERN);
      if (_matches_1) {
        int _indexOf = operand.indexOf("{");
        int _plus = (_indexOf + 1);
        int _indexOf_1 = operand.indexOf("}");
        String vari = operand.substring(_plus, _indexOf_1);
        return this.getVariableValue(vari, arguments);
      } else {
        Double _xifexpression_2 = null;
        boolean _matches_2 = operand.matches(IComputer.NUMBER_PATTERN);
        if (_matches_2) {
          return Double.parseDouble(operand);
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  private double[] getVariables(final String s, final Object... arguments) {
    int _indexOf = s.indexOf("(");
    int _plus = (_indexOf + 1);
    int _indexOf_1 = s.indexOf(")");
    String ss = s.substring(_plus, _indexOf_1);
    int _length = arguments.length;
    double[] rez = new double[_length];
    int i = (-1);
    String[] _split = ss.split(",");
    for (final String sVar : _split) {
      {
        int _plus_1 = (i + 1);
        i = _plus_1;
        boolean _matches = sVar.matches(IComputer.VARIABLE_PATTERN);
        if (_matches) {
          int _indexOf_2 = sVar.indexOf("{");
          int _plus_2 = (_indexOf_2 + 1);
          int _indexOf_3 = sVar.indexOf("}");
          String _substring = sVar.substring(_plus_2, _indexOf_3);
          double _variableValue = this.getVariableValue(_substring, arguments);
          rez[i] = _variableValue;
        } else {
          double _parseDouble = Double.parseDouble(sVar);
          rez[i] = _parseDouble;
        }
      }
    }
    int _plus_1 = (i + 1);
    double[] finalResult = new double[_plus_1];
    boolean _greaterEqualsThan = (i >= 0);
    boolean _while = _greaterEqualsThan;
    while (_while) {
      {
        double _get = rez[i];
        finalResult[i] = _get;
        int _minus = (i - 1);
        i = _minus;
      }
      boolean _greaterEqualsThan_1 = (i >= 0);
      _while = _greaterEqualsThan_1;
    }
    return finalResult;
  }
  
  private double getVariableValue(final String id, final Object... variables) {
    for (final Object variable : variables) {
      String _id = ((Variable) variable).getId();
      boolean _equals = _id.equals(id);
      if (_equals) {
        return ((Variable) variable).getValue();
      }
    }
    return 0.0;
  }
  
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
}
