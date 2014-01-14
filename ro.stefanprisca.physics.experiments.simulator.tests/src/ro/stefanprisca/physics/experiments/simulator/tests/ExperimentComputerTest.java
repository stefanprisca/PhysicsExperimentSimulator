package ro.stefanprisca.physics.experiments.simulator.tests;

import static ro.stefanprisca.physics.experiments.simulator.computer.ExperimentComputer.compute;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class ExperimentComputerTest {

	private Experiment expr;
	
	@Before
	public void init(){
		ArrayList<String> functions = new ArrayList<String>();
		functions.add("{rez}=(max({a},{b})+pow({a},{b})-{as})/(20.30+1000.0)");
		functions.add("{x}={y}+(10.0-cos({y}))");
		ArrayList<Variable> vars = new ArrayList<Variable>();
		vars.add(new Variable("rezf", 0.0));
		vars.add(new Variable("rez", 0.0));
		vars.add(new Variable("a", 50.0));
		vars.add(new Variable("b", 3.0));
		vars.add(new Variable("c", 6.0));
		
		expr = new Experiment("test", "test", functions, vars, UUID.randomUUID());
		
	}
	
	
	@Test
	public void testExperiment() {
		compute(expr);
	}

}
