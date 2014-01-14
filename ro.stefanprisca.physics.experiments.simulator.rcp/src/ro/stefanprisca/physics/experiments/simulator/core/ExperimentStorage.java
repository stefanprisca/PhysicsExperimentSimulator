/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.core;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.recommenders.utils.gson.GsonUtil;

import com.google.common.io.Files;

import ro.stefanprisca.physics.experiments.simulator.rcp.Activator;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ExperimentLogger;
import ro.stefanprisca.physics.experiments.simulator.rcp.preferences.PreferenceConstants;

/**
 * Storage class. It provides methods to fetch experiments from the file system and to save experiments.
 * This class is a singleton, use the getInstance() method to access it.
 * @author Stefan
 *
 */

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
	/**
	 * Returns the experiments from the file system
	 * @return a list containing the experiments
	 */
	public Set<Experiment> getExperiments() {
		TreeSet<Experiment> result = new TreeSet<Experiment>();

		if (location.getAbsolutePath() != Activator.getDefault()
				.getPreferenceStore().getString(PreferenceConstants.P_PATH)) {
			this.location = new File(Activator.getDefault()
					.getPreferenceStore().getString(PreferenceConstants.P_PATH));
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

	/**
	 * Returns only those experiments that match a given pattern
	 * @param filter the pattern experiments need to match
	 * @return a list of experiments that match the given pattern
	 */
	public Set<Experiment> getExperiments(String filter) {
		TreeSet<Experiment> result = new TreeSet<Experiment>();
		Experiment exp;
		if (location.exists()) {

			for (File jsonFile : location.listFiles()) {
				exp = GsonUtil.deserialize(jsonFile, Experiment.class);
				if (exp.getName().contains(filter)
						|| exp.getDescription().contains(filter)) {
					exp.setLocation(jsonFile);
					result.add(exp);
				}
			}

		}
		return result;
	}
	
	/**
	 * Saves an experiment in it location
	 * @param newExpr the experiment to save
	 */
	public void saveExperiment(Experiment newExpr){
		String contents = GsonUtil.serialize(newExpr);

		File json = newExpr.getLocation();
		try {
			Files.write(contents.getBytes(), json);
		} catch (Exception e) {
			ExperimentLogger.getInstance().severe(
					"Failed to save file! \n" + e.getMessage());
		}
	}

}
