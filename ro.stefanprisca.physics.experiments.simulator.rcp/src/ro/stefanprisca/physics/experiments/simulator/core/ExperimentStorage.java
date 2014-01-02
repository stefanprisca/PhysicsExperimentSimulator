package ro.stefanprisca.physics.experiments.simulator.core;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.recommenders.utils.gson.GsonUtil;

import ro.stefanprisca.physics.experiments.simulator.rcp.Activator;
import ro.stefanprisca.physics.experiments.simulator.rcp.preferences.PreferenceConstants;

public class ExperimentStorage {

	// private static final IOFileFilter EXPERIMENTS_FILTER = new
	// RegexFileFilter("^.+?\\.json");
	private static ExperimentStorage storage;

	private File location;

	public static ExperimentStorage getInstance() {
		if (storage == null) {
			storage = new ExperimentStorage();
		}
		return storage;
	}

	private ExperimentStorage() {
		this.location = new File(Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.P_PATH));
	}

	private ExperimentStorage(String location) {
		this.location = new File(location);
		// findOrCreateProject(this.location);
	}

	/*
	 * private void findOrCreateProject(File location) { // TODO Auto-generated
	 * method stub location.g }
	 */

	public Set<Experiment> getExperiments() {
		TreeSet<Experiment> result = new TreeSet<Experiment>();
		
		if(location.getAbsolutePath() != Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.P_PATH)){
			this.location = new File(Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.P_PATH));
		}
		
		
		if (location.exists()) {

			for (File jsonFile : location.listFiles()) {
				Experiment exp = GsonUtil.deserialize(jsonFile,
						Experiment.class);
				exp.setLocation(jsonFile);
				result.add(exp);
			}

		}
		return result;
	}

	public Set<Experiment> getExperiments(String filter) {
		TreeSet<Experiment> result = new TreeSet<Experiment>();
		Experiment exp;
		if (location.exists()) {

			for (File jsonFile : location.listFiles()) {
				exp = GsonUtil.deserialize(jsonFile, Experiment.class);
				if (exp.getName().contains(filter) || exp.getDescription().contains(filter)) {
					exp.setLocation(jsonFile);
					result.add(exp);
				}
			}

		}
		return result;
	}

}
