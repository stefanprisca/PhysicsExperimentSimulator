package ro.stefanprisca.physics.experiments.simulator.computer;

import java.util.List;
import java.util.logging.Logger;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger;

@SuppressWarnings("all")
public class ExperimentComputer {
  private final static Logger LOGGER = new Function0<Logger>() {
    public Logger apply() {
      Logger _instance = ExperimentLogger.getInstance();
      return _instance;
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
    String _plus = ("\n********************************************" + 
      "\n\tStarted experiment < ");
    String _name = e.getName();
    String _plus_1 = (_plus + _name);
    String _plus_2 = (_plus_1 + " >");
    String _plus_3 = (_plus_2 + 
      "\n********************************************\n");
    ExperimentComputer.LOGGER.fine(_plus_3);
    List<Function> _functions = e.getFunctions();
    for (final Function f : _functions) {
      {
        List<Variable> _variables = e.getVariables();
        double _computeFunction = ExperimentComputer.fComp.computeFunction(f, _variables);
        rez = _computeFunction;
        String _equation = f.getEquation();
        String _plus_4 = ("The result of function " + _equation);
        String _plus_5 = (_plus_4 + " is:");
        String _plus_6 = (_plus_5 + Double.valueOf(rez));
        ExperimentComputer.LOGGER.info(_plus_6);
      }
    }
    String _name_1 = e.getName();
    String _plus_4 = ("The final result of experiment < " + _name_1);
    String _plus_5 = (_plus_4 + " > is: ");
    String _plus_6 = (_plus_5 + Double.valueOf(rez));
    ExperimentComputer.LOGGER.info(_plus_6);
    String _plus_7 = ("\n********************************************" + 
      "\n\tExperiment < ");
    String _name_2 = e.getName();
    String _plus_8 = (_plus_7 + _name_2);
    String _plus_9 = (_plus_8 + " > concluded with result ");
    String _plus_10 = (_plus_9 + Double.valueOf(rez));
    String _plus_11 = (_plus_10 + 
      "\n********************************************\n");
    ExperimentComputer.LOGGER.fine(_plus_11);
  }
  
  public static void compute(final Experiment e, final String period) {
    double rez = 0;
    int t = Integer.parseInt(period);
    boolean _greaterThan = (t > 0);
    boolean _while = _greaterThan;
    while (_while) {
      {
        String _plus = ("\n********************************************" + 
          "\n\tRunning experiment < ");
        String _name = e.getName();
        String _plus_1 = (_plus + _name);
        String _plus_2 = (_plus_1 + " > at T: ");
        String _plus_3 = (_plus_2 + Integer.valueOf(t));
        String _plus_4 = (_plus_3 + 
          "\n********************************************\n");
        ExperimentComputer.LOGGER.fine(_plus_4);
        List<Function> _functions = e.getFunctions();
        for (final Function f : _functions) {
          {
            List<Variable> _variables = e.getVariables();
            double _computeFunction = ExperimentComputer.fComp.computeFunction(f, _variables);
            rez = _computeFunction;
            String _equation = f.getEquation();
            String _plus_5 = ("The result of function " + _equation);
            String _plus_6 = (_plus_5 + " is:");
            String _plus_7 = (_plus_6 + Double.valueOf(rez));
            ExperimentComputer.LOGGER.info(_plus_7);
          }
        }
        String _name_1 = e.getName();
        String _plus_5 = ("The final result of experiment < " + _name_1);
        String _plus_6 = (_plus_5 + " > is: ");
        String _plus_7 = (_plus_6 + Double.valueOf(rez));
        ExperimentComputer.LOGGER.info(_plus_7);
        String _plus_8 = ("\n********************************************" + 
          "\n\tExperiment < ");
        String _name_2 = e.getName();
        String _plus_9 = (_plus_8 + _name_2);
        String _plus_10 = (_plus_9 + " > concluded with result ");
        String _plus_11 = (_plus_10 + Double.valueOf(rez));
        String _plus_12 = (_plus_11 + " at T: ");
        String _plus_13 = (_plus_12 + Integer.valueOf(t));
        String _plus_14 = (_plus_13 + 
          "\n********************************************\n");
        ExperimentComputer.LOGGER.fine(_plus_14);
        int _minus = (t - 1);
        t = _minus;
      }
      boolean _greaterThan_1 = (t > 0);
      _while = _greaterThan_1;
    }
  }
}
