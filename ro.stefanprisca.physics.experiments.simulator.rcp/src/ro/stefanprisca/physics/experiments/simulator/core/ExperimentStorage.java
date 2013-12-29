package ro.stefanprisca.physics.experiments.simulator.core;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.recommenders.utils.gson.GsonUtil;

import ro.stefanprisca.physics.experiments.simulator.rcp.Activator;
import ro.stefanprisca.physics.experiments.simulator.rcp.preferences.PreferenceConstants;

import com.google.inject.Inject;


public class ExperimentStorage {

	private static final IOFileFilter EXPERIMENTS_FILTER = new RegexFileFilter("^.+?\\.json");
	private static final String PROJECT_NAME = ".experiments";
	private static ExperimentStorage storage;
	
	private File location;
	private IProject project;
	
	public static ExperimentStorage getInstance(){
		if(storage == null ){
			storage = new ExperimentStorage();
		}
		return storage;
	}
	
	
	private ExperimentStorage(){
		this.location=new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH));
	}
	
	
	private ExperimentStorage(String location){
		this.location=new File(location);
		//findOrCreateProject(this.location);
	}
	
	/*private void findOrCreateProject(File location) {
		// TODO Auto-generated method stub
		location.g
	}*/

	public Set<Experiment> getExperiments(){
		TreeSet<Experiment> result=new TreeSet<Experiment>();
		if(location.exists()){
		
		for (File jsonFile : FileUtils.listFiles(location, EXPERIMENTS_FILTER, TrueFileFilter.INSTANCE)) {
           Experiment exp=GsonUtil.deserialize(jsonFile, Experiment.class);
           exp.setLocation(jsonFile);
           result.add(exp);
        }
		
		}
		return result;
	}


}
