package ro.stefanprisca.physics.experiments.simulator.tests;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import ro.stefanprisca.physics.experiments.simulator.core.IComputer;

public class PatternMatchTest {

	private final String equation = "{rez}=(max({a},{b})+pow({a},{b})-{as})/(20.30+1000.0)";
	private final String equation2 = "{x}={y}+(10.0-cos({y}))";
	Matcher m;
	@Test
	public void testAssignmentPattern() {
		m = Pattern.compile(IComputer.ASSIGNMENT_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.ASSIGNMENT_PATTERN).matcher(equation2);
		assertTrue(m.find());
		
	}
	
	@Test
	public void testMathFunctionPattern() {
		m = Pattern.compile(IComputer.MATHFUNCTION_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.MATHFUNCTION_PATTERN).matcher(equation);
		assertTrue(m.find());
	}
	
	@Test
	public void testVariablePattern() {
		m = Pattern.compile(IComputer.VARIABLE_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.VARIABLE_PATTERN).matcher(equation);
		assertTrue(m.find());
	}
	@Test
	public void testNumberPattern() {
		m = Pattern.compile(IComputer.NUMBER_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.NUMBER_PATTERN).matcher(equation);
		assertTrue(m.find());
	}
	@Test
	public void testMathOperatorPattern() {
		m = Pattern.compile(IComputer.MATHOPERATOR_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.MATHOPERATOR_PATTERN).matcher(equation);
		assertTrue(m.find());
		
	}
	@Test
	public void testOperandPattern() {
		m = Pattern.compile(IComputer.OPERAND_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.OPERAND_PATTERN).matcher(equation);
		assertTrue(m.find());
	}
	@Test
	public void testNoParanthOperationPattern() {
		m = Pattern.compile(IComputer.NOPARANTH_OPERATION_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.NOPARANTH_OPERATION_PATTERN).matcher(equation);
		assertTrue(m.find());
		
	}
	@Test
	public void testParanthOperationPattern() {
		m = Pattern.compile(IComputer.PARANTHESISOP_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.PARANTHESISOP_PATTERN).matcher(equation);
		assertTrue(m.find());
	}
	@Test
	public void testOperationPattern() {
		m = Pattern.compile(IComputer.OPERATION_PATTERN).matcher(equation2);
		assertTrue(m.find());
		m = Pattern.compile(IComputer.OPERATION_PATTERN).matcher(equation);
		assertTrue(m.find());
		
	}
}
