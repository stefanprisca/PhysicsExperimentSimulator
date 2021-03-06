package ro.stefanprisca.physics.experiments.simulator.computer;

import com.google.common.base.Objects;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;

/**
 * Class used for simple computations like additions, etc
 */
@SuppressWarnings("all")
public class SimpleComputer implements IComputer {
  private static int scale = 12;
  
  public static void setScale(final int sc) {
    SimpleComputer.scale = sc;
  }
  
  public double compute(final String equation, final Object... arguments) throws IllegalArgumentException {
    Object _get = arguments[0];
    BigDecimal _bigDecimal = new BigDecimal((((Double) _get)).doubleValue());
    BigDecimal result = _bigDecimal.setScale(SimpleComputer.scale, BigDecimal.ROUND_HALF_UP);
    Object _get_1 = arguments[1];
    BigDecimal _bigDecimal_1 = new BigDecimal((((Double) _get_1)).doubleValue());
    BigDecimal arg2 = _bigDecimal_1.setScale(SimpleComputer.scale, BigDecimal.ROUND_HALF_UP);
    final Function1<String,String> _function = new Function1<String,String>() {
      public String apply(final String eq) {
        Pattern _compile = Pattern.compile(IComputer.MATHOPERATOR_PATTERN);
        Matcher matcher = _compile.matcher(eq);
        boolean _find = matcher.find();
        if (_find) {
          return matcher.group();
        }
        return null;
      }
    };
    String operator = _function.apply(equation);
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(operator,"+")) {
        _matched=true;
        BigDecimal _add = result.add(arg2);
        result = _add;
      }
    }
    if (!_matched) {
      if (Objects.equal(operator,"-")) {
        _matched=true;
        BigDecimal _subtract = result.subtract(arg2);
        result = _subtract;
      }
    }
    if (!_matched) {
      if (Objects.equal(operator,"/")) {
        _matched=true;
        double _doubleValue = arg2.doubleValue();
        boolean _notEquals = (_doubleValue != 0);
        if (_notEquals) {
          BigDecimal _divide = result.divide(arg2, BigDecimal.ROUND_HALF_UP);
          result = _divide;
        } else {
          IllegalArgumentException _illegalArgumentException = new IllegalArgumentException();
          throw _illegalArgumentException;
        }
      }
    }
    if (!_matched) {
      if (Objects.equal(operator,"*")) {
        _matched=true;
        BigDecimal _multiply = result.multiply(arg2);
        result = _multiply;
      }
    }
    return result.doubleValue();
  }
}
