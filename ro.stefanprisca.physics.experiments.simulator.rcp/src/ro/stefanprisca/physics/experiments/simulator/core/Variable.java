package ro.stefanprisca.physics.experiments.simulator.core;

import java.util.UUID;

import org.eclipse.recommenders.utils.Uuidable;

import com.google.gson.annotations.SerializedName;

public class Variable implements Comparable<Object>, Uuidable {

	@SerializedName("id")
	private String id;
	
	@SerializedName("val")
	private double value=1.0;
	
	public Variable(String id, double value){
		this.id=id;
		this.value = value;
	}
	
	@Override
	public UUID getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
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

}
