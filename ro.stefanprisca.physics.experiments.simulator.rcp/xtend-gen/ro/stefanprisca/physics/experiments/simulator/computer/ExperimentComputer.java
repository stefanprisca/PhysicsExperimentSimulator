package ro.stefanprisca.physics.experiments.simulator.computer;

import java.util.List;
import java.util.logging.Logger;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

@SuppressWarnings("all")
public class ExperimentComputer {
  private final static Logger LOGGER = new Function0<Logger>() {
    public Logger apply() {
      Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
      return _logger;
    }
  }.apply();
  
  private static FunctionComputer fComp = new Function0<FunctionComputer>() {
    public FunctionComputer apply() {
      FunctionComputer _functionComputer = new FunctionComputer();
      return _functionComputer;
    }
  }.apply();
  
  public static void compute(final Experiment e) {
    double rez = 0;
    List<Function> _functions = e.getFunctions();
    for (final Function f : _functions) {
      {
        List<Variable> _variables = e.getVariables();
        double _computeFunction = ExperimentComputer.fComp.computeFunction(f, _variables);
        rez = _computeFunction;
        String _equation = f.getEquation();
        String _plus = ("The result of function " + _equation);
        String _plus_1 = (_plus + " is:");
        String _plus_2 = (_plus_1 + Double.valueOf(rez));
        ExperimentComputer.LOGGER.info(_plus_2);
      }
    }
    String _name = e.getName();
    String _plus = ("The final result of experiment < " + _name);
    String _plus_1 = (_plus + " > is: ");
    String _plus_2 = (_plus_1 + Double.valueOf(rez));
    ExperimentComputer.LOGGER.info(_plus_2);
    boolean _compIsOk = ExperimentComputer.fComp.compIsOk();
    boolean _not = (!_compIsOk);
    if (_not) {
      String _plus_3 = ("\n********************\n" + 
        "The experiment encountered some issues. Please check the log for messages marked with <SEVERE>!\n");
      String _plus_4 = (_plus_3 + 
        "********************");
      ExperimentComputer.LOGGER.severe(_plus_4);
    }
  }
}
