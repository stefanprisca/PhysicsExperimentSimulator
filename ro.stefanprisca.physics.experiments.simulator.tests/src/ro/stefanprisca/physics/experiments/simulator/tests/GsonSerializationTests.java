package ro.stefanprisca.physics.experiments.simulator.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.eclipse.recommenders.utils.gson.GsonUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class GsonSerializationTests {

	private static final String JSON_EXTENSION = "json";

	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
	File location;
	
	Experiment expr;
	
	@Before
	public void init(){
		expr=new Experiment("newExperiment","Some test experiment", new ArrayList<Function>(), new ArrayList<Variable>(), UUID.randomUUID());
		location=tempFolder.getRoot();
	}
	private File addFileToLocationWithContents(String fileName, File parent, String contents) throws
	 IOException {
	        File newFile = new File(parent, fileName.concat("." + JSON_EXTENSION));
	        if (!newFile.exists()) {	
	            newFile.createNewFile();	
	        }	
	        FileWriter wr = new FileWriter(newFile);	
	        BufferedWriter bwr = new BufferedWriter(wr);	
	        bwr.write(contents);	
	        bwr.close();	
	        return newFile;	
	    }
	@Test
	public void test() throws IOException {
		String contents=GsonUtil.serialize(expr);
		File newExperiment = addFileToLocationWithContents("NewExperiment", location, contents);
		
		Experiment expr2= GsonUtil.deserialize(newExperiment, Experiment.class);
		assertTrue(expr2.getUuid().equals(expr.getUuid()));
	}

}
