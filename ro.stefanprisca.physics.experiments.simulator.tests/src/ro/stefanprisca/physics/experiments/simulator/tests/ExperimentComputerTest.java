package ro.stefanprisca.physics.experiments.simulator.tests;

import static org.junit.Assert.*;
import static ro.stefanprisca.physics.experiments.simulator.computer.ExperimentComputer.compute;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class ExperimentComputerTest {

	private Experiment expr;
	
	@Before
	public void init(){
		ArrayList<Function> functions = new ArrayList<Function>();
		functions.add(doCreateFunction("rez", "a","b", "c"));
		functions.add(doCreateFunction("rezf", "rez","b", "c"));
		ArrayList<Variable> vars = new ArrayList<Variable>();
		vars.add(new Variable("rezf", 0.0));
		vars.add(new Variable("rez", 0.0));
		vars.add(new Variable("a", 50.0));
		vars.add(new Variable("b", 3.0));
		vars.add(new Variable("c", 6.0));
		
		expr = new Experiment("test", "test", functions, vars, UUID.randomUUID());
		
	}
	
	private Function doCreateFunction(String output, String...input){
		Variable a=new Variable(input[0],50.0);
		Variable b=new Variable(input[1],3.0);
		Variable as = new Variable(input[2], 20.0);
		Variable rez = new Variable(output, 0.0);
		ArrayList<Variable> vars = new ArrayList<Variable>();
		vars.add(a);
		vars.add(b);
		vars.add(as);
		vars.add(rez);
		Function f = new Function(vars, "{"+output+"}=max({"+input[0]+"},{"+input[1]+"})+"
				+ "((pow({"+input[0]+"},{"+input[1]+" } ) - {"+input[2]+"})/(20.30 + 1000.0))");
		return f;
	}
	@Test
	public void testExperiment() {
		compute(expr);
	}

}
