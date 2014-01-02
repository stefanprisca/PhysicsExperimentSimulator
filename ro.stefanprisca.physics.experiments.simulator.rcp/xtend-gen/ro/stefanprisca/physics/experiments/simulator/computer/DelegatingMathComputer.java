package ro.stefanprisca.physics.experiments.simulator.computer;

import com.google.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.recommenders.utils.Checks;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;

@SuppressWarnings("all")
public class DelegatingMathComputer implements IComputer {
  private Math delegate;
  
  public static String getMathMethods() {
    String _string = new String("");
    String s = _string;
    Method[] _methods = Math.class.getMethods();
    for (final Method m : _methods) {
      String _name = m.getName();
      String _plus = (s + _name);
      String _plus_1 = (_plus + " ");
      s = _plus_1;
    }
    return s;
  }
  
  public static Object[] getPrettyMathMethodsArray() {
    ArrayList<String> s = CollectionLiterals.<String>newArrayList();
    Method[] _methods = Math.class.getMethods();
    for (final Method m : _methods) {
      {
        String _name = m.getName();
        String _plus = (_name + "(");
        final Function0<String> _function = new Function0<String>() {
          public String apply() {
            String ss = "";
            Class<? extends Object>[] _parameterTypes = m.getParameterTypes();
            for (final Class<? extends Object> par : _parameterTypes) {
              String _name = par.getName();
              String _plus = ((ss + ", ") + _name);
              ss = _plus;
            }
            return ss.replaceFirst(", ", "");
          }
        };
        String _apply = _function.apply();
        String _plus_1 = (_plus + _apply);
        String name = (_plus_1 + ")");
        s.add(name);
      }
    }
    return s.toArray();
  }
  
  @Inject
  public DelegatingMathComputer() {
  }
  
  public double compute(final String equation, final Object... args) {
    double _delegateEquation = this.delegateEquation(equation, args);
    return _delegateEquation;
  }
  
  public double delegateEquation(final String equation, final Object... args) {
    try {
      Checks.<Object[]>ensureIsNotNull(args);
      Method _xifexpression = null;
      int _length = args.length;
      boolean _equals = (_length == 1);
      if (_equals) {
        Method _method = Math.class.getMethod(equation, double.class);
        _xifexpression = _method;
      } else {
        Method _xifexpression_1 = null;
        int _length_1 = args.length;
        boolean _equals_1 = (_length_1 == 2);
        if (_equals_1) {
          Method _method_1 = Math.class.getMethod(equation, double.class, double.class);
          _xifexpression_1 = _method_1;
        } else {
          Method _method_2 = Math.class.getMethod(equation);
          _xifexpression_1 = _method_2;
        }
        _xifexpression = _xifexpression_1;
      }
      final Method method = _xifexpression;
      double[] _parameters = this.getParameters(method, args);
      Object[] _array = ((List<Double>)Conversions.doWrapArray(((double[]) _parameters))).toArray();
      Object result = method.invoke(this.delegate, _array);
      return (((Double) result)).doubleValue();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public double[] getParameters(final Method m, final Object... args) {
    Checks.<Object[]>ensureIsNotNull(args);
    Class<? extends Object>[] _parameterTypes = m.getParameterTypes();
    int _length = _parameterTypes.length;
    int _length_1 = args.length;
    boolean _equals = Integer.valueOf(_length).equals(Integer.valueOf(_length_1));
    Checks.ensureIsTrue(_equals);
    int _length_2 = args.length;
    double[] parameters = new double[_length_2];
    int i = 0;
    for (final Object arg : args) {
      {
        parameters[i] = (((Double) arg)).doubleValue();
        i = (i + 1);
      }
    }
    return parameters;
  }
}
