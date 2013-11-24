package ro.stefanprisca.physics.experiments.simulator.computer;

import com.google.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;
import org.eclipse.recommenders.utils.Checks;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
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
        Method _method_1 = Math.class.getMethod(equation, double.class, double.class);
        _xifexpression = _method_1;
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
        int _plus = (i + 1);
        i = _plus;
      }
    }
    return parameters;
  }
}
