/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.core;

import java.util.List;
import java.util.UUID;

import org.eclipse.recommenders.utils.Uuidable;

import com.google.gson.annotations.SerializedName;

public class Function implements Uuidable, Comparable<Object> {
	/*
	 * @SerializedName("fvars") private List<Variable> variables;
	 */
	@SerializedName("equation")
	private String equation;

	public Function(List<Variable> variables, String equation) {
		// this.variables=variables;
		this.equation = equation;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UUID getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}
}
