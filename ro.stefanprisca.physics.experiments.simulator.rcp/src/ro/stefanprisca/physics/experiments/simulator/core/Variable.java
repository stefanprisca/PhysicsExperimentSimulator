/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.core;

import java.util.UUID;

import org.eclipse.recommenders.utils.Uuidable;

import com.google.gson.annotations.SerializedName;

public class Variable implements Comparable<Variable>, Uuidable {

	@SerializedName("id")
	private String id;

	@SerializedName("val")
	private double value = 1.0;

	public Variable(String id, double value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public UUID getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(Variable o) {
		// TODO Auto-generated method stub
		return this.id.compareTo(o.id);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return this.id.equals(((Variable) obj).getId());
	}
}
