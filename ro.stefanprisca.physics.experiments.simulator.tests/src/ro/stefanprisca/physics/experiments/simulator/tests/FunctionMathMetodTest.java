package ro.stefanprisca.physics.experiments.simulator.tests;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import ro.stefanprisca.physics.experiments.simulator.computer.FunctionComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class FunctionMathMetodTest {

	private Logger logger = Logger.getAnonymousLogger();
	ArrayList<Variable> vars;
	FunctionComputer fCp = new FunctionComputer();
	@Before
	public void init(){
		Variable a=new Variable("z",50.0);
		Variable b=new Variable("b",3.0);
		Variable as = new Variable("as1", 20.0);
		Variable rez = new Variable("rez", 20.0);
		vars = new ArrayList<Variable>();
		vars.add(a);
		vars.add(b);
		vars.add(as);
		vars.add(rez);
		fCp.setLogger(logger);
	}
	
	@Test
	public void testRandom() throws Exception {
		
		String f = new String("random()");

		System.out.println(fCp.computeFunction(f, vars));
	}

	@Test
	public void testpow() throws Exception {
		
		String f = new String("pow({z}, {b})");
		System.out.println(fCp.computeFunction(f, vars));
	}
	@Test
	public void testPIEG() throws Exception {
		
		String f = new String("{z} = {PI}");
		System.out.println(fCp.computeFunction(f, vars));
		
		f=new String("{z} + {E}");
		System.out.println(fCp.computeFunction(f, vars));
		
		f=new String("{G} + {E}");
		System.out.println(fCp.computeFunction(f, vars));
	}
	@Test
	public void testNumVar() throws Exception {
		
		String f = new String("pow({as1}, {b})");
		System.out.println(fCp.computeFunction(f, vars));
	}
}
