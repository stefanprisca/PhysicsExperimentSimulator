package ro.stefanprisca.physics.experiments.simulator.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import ro.stefanprisca.physics.experiments.simulator.computer.DelegatingMathComputer;
import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class FunctionMathMetodTest {

	
	@Test
	public void test() {
		Variable a=new Variable("z",50.0);
		Variable b=new Variable("b",3.0);
		Variable as = new Variable("as", 20.0);
		Variable rez = new Variable("rez", 20.0);
		ArrayList<Variable> vars = new ArrayList<Variable>();
		vars.add(a);
		vars.add(b);
		vars.add(as);
		vars.add(rez);
		Function f = new Function(vars, "pow({b},2.0)-0.0");
		FunctionComputer fCp = new FunctionComputer();
		System.out.println(fCp.computeFunction(f, vars));
	}

}
