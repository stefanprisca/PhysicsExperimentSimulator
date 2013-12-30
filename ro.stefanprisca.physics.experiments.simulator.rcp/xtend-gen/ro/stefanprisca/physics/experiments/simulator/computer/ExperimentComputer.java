package ro.stefanprisca.physics.experiments.simulator.computer;

import java.util.List;
import java.util.logging.Logger;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger;

@SuppressWarnings("all")
public class ExperimentComputer {
  private final static Logger LOGGER = ExperimentLogger.getInstance();
  
  private static FunctionComputer fComp = new Function0<FunctionComputer>() {
    public FunctionComputer apply() {
      FunctionComputer _functionComputer = new FunctionComputer();
      return _functionComputer;
    }
  }.apply();
  
  public static void compute(final Experiment e) {
    double rez = 0;
    String _name = e.getName();
    String _plus = (("\n********************************************" + 
      "\n\tStarted experiment < ") + _name);
    String _plus_1 = (_plus + " >");
    String _plus_2 = (_plus_1 + 
      "\n********************************************\n");
    ExperimentComputer.LOGGER.fine(_plus_2);
    List<String> _functions = e.getFunctions();
    for (final String f : _functions) {
      try {
        List<Variable> _variables = e.getVariables();
        double _computeFunction = ExperimentComputer.fComp.computeFunction(f, _variables);
        rez = _computeFunction;
        ExperimentComputer.LOGGER.info(((("The result of equation " + f) + " is:") + Double.valueOf(rez)));
      } catch (final Throwable _t) {
        if (_t instanceof NumberFormatException) {
          final NumberFormatException nfe = (NumberFormatException)_t;
          String _message = nfe.getMessage();
          String _plus_3 = (((("There were number format issues in equation " + f) + " !\n") + 
            "Make sure all numbers are in < x.x > decimal format!(e.g. 10.0).\n") + _message);
          ExperimentComputer.LOGGER.severe(_plus_3);
          return;
        } else if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException ilae = (IllegalArgumentException)_t;
          ExperimentComputer.LOGGER.severe(
            ((("There were argument exceptions in equation " + f) + " !\n") + 
              "This may be due to a division by zero"));
          return;
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message_1 = ex.getMessage();
          String _plus_4 = (((("Something went wrong in equation " + f) + " !\n") + 
            "Please check the following message and the console output! \n") + _message_1);
          ExperimentComputer.LOGGER.severe(_plus_4);
          return;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    String _name_1 = e.getName();
    String _plus_5 = ("The final result of experiment < " + _name_1);
    String _plus_6 = (_plus_5 + " > is: ");
    String _plus_7 = (_plus_6 + Double.valueOf(rez));
    ExperimentComputer.LOGGER.info(_plus_7);
    String _name_2 = e.getName();
    String _plus_8 = (("\n********************************************" + 
      "\n\tExperiment < ") + _name_2);
    String _plus_9 = (_plus_8 + " > concluded with result ");
    String _plus_10 = (_plus_9 + Double.valueOf(rez));
    String _plus_11 = (_plus_10 + 
      "\n********************************************\n");
    ExperimentComputer.LOGGER.fine(_plus_11);
  }
  
  public static void compute(final Experiment e, final String period) {
    try {
      double rez = 0;
      int t = Integer.parseInt(period);
      boolean _while = (t > 0);
      while (_while) {
        {
          String _name = e.getName();
          String _plus = (("\n********************************************" + 
            "\n\tRunning experiment < ") + _name);
          String _plus_1 = (_plus + " > at T: ");
          String _plus_2 = (_plus_1 + Integer.valueOf(t));
          String _plus_3 = (_plus_2 + 
            "\n********************************************\n");
          ExperimentComputer.LOGGER.fine(_plus_3);
          List<String> _functions = e.getFunctions();
          for (final String f : _functions) {
            {
              List<Variable> _variables = e.getVariables();
              double _computeFunction = ExperimentComputer.fComp.computeFunction(f, _variables);
              rez = _computeFunction;
              ExperimentComputer.LOGGER.info(((("The result of function " + f) + " is:") + Double.valueOf(rez)));
            }
          }
          String _name_1 = e.getName();
          String _plus_4 = ("The final result of experiment < " + _name_1);
          String _plus_5 = (_plus_4 + " > is: ");
          String _plus_6 = (_plus_5 + Double.valueOf(rez));
          ExperimentComputer.LOGGER.info(_plus_6);
          String _name_2 = e.getName();
          String _plus_7 = (("\n********************************************" + 
            "\n\tExperiment < ") + _name_2);
          String _plus_8 = (_plus_7 + " > concluded with result ");
          String _plus_9 = (_plus_8 + Double.valueOf(rez));
          String _plus_10 = (_plus_9 + " at T: ");
          String _plus_11 = (_plus_10 + Integer.valueOf(t));
          String _plus_12 = (_plus_11 + 
            "\n********************************************\n");
          ExperimentComputer.LOGGER.fine(_plus_12);
          t = (t - 1);
        }
        _while = (t > 0);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
