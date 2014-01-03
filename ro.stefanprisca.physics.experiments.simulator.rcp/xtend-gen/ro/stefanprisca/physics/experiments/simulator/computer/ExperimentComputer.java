package ro.stefanprisca.physics.experiments.simulator.computer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.ArrayList;
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
  private static Logger LOGGER = ExperimentLogger.getInstance();
  
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
    ArrayList<Variable> args = Lists.<Variable>newArrayList();
    List<Variable> _variables = e.getVariables();
    for (final Variable vari : _variables) {
      String _id = vari.getId();
      double _value = vari.getValue();
      Variable _variable = new Variable(_id, _value);
      args.add(_variable);
    }
    List<String> _functions = e.getFunctions();
    for (final String f : _functions) {
      try {
        double _computeFunction = ExperimentComputer.fComp.computeFunction(f, args);
        rez = _computeFunction;
        ExperimentComputer.LOGGER.info(((("The result of equation " + f) + " is:") + Double.valueOf(rez)));
      } catch (final Throwable _t) {
        if (_t instanceof IllegalArgumentException) {
          final IllegalArgumentException ilae = (IllegalArgumentException)_t;
          String _message = ilae.getMessage();
          String _plus_3 = (((("There were argument exceptions in equation " + f) + " !\n") + 
            "Please check for divisions by zero, invalid math operations, or undeclared variables! \n") + _message);
          ExperimentComputer.LOGGER.severe(_plus_3);
          return;
        } else if (_t instanceof NumberFormatException) {
          final NumberFormatException nfe = (NumberFormatException)_t;
          String _message_1 = nfe.getMessage();
          String _plus_4 = (((("There were number format issues in equation " + f) + " !\n") + 
            "Make sure all numbers are in < x.x > decimal format!(e.g. 10.0).\n") + _message_1);
          ExperimentComputer.LOGGER.severe(_plus_4);
          return;
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message_2 = ex.getMessage();
          String _plus_5 = (((("Something went wrong in equation " + f) + " !\n") + 
            "Please check the following message and the console output! \n") + _message_2);
          ExperimentComputer.LOGGER.severe(_plus_5);
          return;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    String _name_1 = e.getName();
    String _plus_6 = ("The final result of experiment < " + _name_1);
    String _plus_7 = (_plus_6 + " > is: ");
    String _plus_8 = (_plus_7 + Double.valueOf(rez));
    ExperimentComputer.LOGGER.info(_plus_8);
    String _name_2 = e.getName();
    String _plus_9 = (("\n********************************************" + 
      "\n\tExperiment < ") + _name_2);
    String _plus_10 = (_plus_9 + " > concluded with result ");
    String _plus_11 = (_plus_10 + Double.valueOf(rez));
    String _plus_12 = (_plus_11 + 
      "\n********************************************\n");
    ExperimentComputer.LOGGER.fine(_plus_12);
  }
  
  public static void compute(final Experiment e, final String period) {
    double rez = 0;
    int t = Integer.parseInt(period);
    ArrayList<Variable> args = Lists.<Variable>newArrayList();
    List<Variable> _variables = e.getVariables();
    for (final Variable vari : _variables) {
      String _id = vari.getId();
      double _value = vari.getValue();
      Variable _variable = new Variable(_id, _value);
      args.add(_variable);
    }
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
          try {
            double _computeFunction = ExperimentComputer.fComp.computeFunction(f, args);
            rez = _computeFunction;
            ExperimentComputer.LOGGER.info(((("The result of equation " + f) + " is:") + Double.valueOf(rez)));
          } catch (final Throwable _t) {
            if (_t instanceof NumberFormatException) {
              final NumberFormatException nfe = (NumberFormatException)_t;
              String _message = nfe.getMessage();
              String _plus_4 = (((("There were number format issues in equation " + f) + " !\n") + 
                "Make sure all numbers are in < x.x > decimal format!(e.g. 10.0).\n") + _message);
              ExperimentComputer.LOGGER.severe(_plus_4);
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
              String _plus_5 = (((("Something went wrong in equation " + f) + " !\n") + 
                "Please check the following message and the console output! \n") + _message_1);
              ExperimentComputer.LOGGER.severe(_plus_5);
              return;
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
        }
        String _name_1 = e.getName();
        String _plus_6 = ("The final result of experiment < " + _name_1);
        String _plus_7 = (_plus_6 + " > is: ");
        String _plus_8 = (_plus_7 + Double.valueOf(rez));
        ExperimentComputer.LOGGER.info(_plus_8);
        String _name_2 = e.getName();
        String _plus_9 = (("\n********************************************" + 
          "\n\tExperiment < ") + _name_2);
        String _plus_10 = (_plus_9 + " > concluded with result ");
        String _plus_11 = (_plus_10 + Double.valueOf(rez));
        String _plus_12 = (_plus_11 + " at T: ");
        String _plus_13 = (_plus_12 + Integer.valueOf(t));
        String _plus_14 = (_plus_13 + 
          "\n********************************************\n");
        ExperimentComputer.LOGGER.fine(_plus_14);
        t = (t - 1);
      }
      _while = (t > 0);
    }
  }
  
  @VisibleForTesting
  public Logger setLogger(final Logger logger) {
    Logger _LOGGER = ExperimentComputer.LOGGER = logger;
    return _LOGGER;
  }
}
