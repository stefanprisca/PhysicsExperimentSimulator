package ro.stefanprisca.physics.experiments.simulator.core;

import static org.eclipse.recommenders.utils.Checks.ensureIsNotNull;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.eclipse.recommenders.utils.Uuidable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("rawtypes")
public class Experiment implements Uuidable, Comparable{

	@SerializedName("uuid")
	private UUID uuid;
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;

	@SerializedName("functions")
	private List<String> functions;
	
	@SerializedName("variables")
	private List<Variable> variables;
	
	private File location;

		
	public Experiment (String name, String description, List<String> functions, List<Variable> variables, UUID uuid){
		ensureIsNotNull(name);
		ensureIsNotNull(description);
		ensureIsNotNull(functions);
		ensureIsNotNull(uuid);
		ensureIsNotNull(variables);
		this.uuid=uuid;
		this.name=name;
		this.description=description;
		this.functions=functions;
		this.variables=variables;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	@Override
	public UUID getUuid() {
		// TODO Auto-generated method stub
		return uuid;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		if(this == arg0)
			return 0;
		
		if(this.hashCode()<arg0.hashCode())
			return -1;
		return 1;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}
	public List<String> getFunctions() {
		return functions;
	}

	public void setFunctions(List<String> functions) {
		this.functions = functions;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String toString(){
		return name+":~   "+description.substring(0, Math.min(description.length(), 40));
	}
	
}
