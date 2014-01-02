package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.recommenders.utils.gson.GsonUtil;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.google.common.collect.Lists;

import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class ExperimentFileEditorInput implements IFileEditorInput {

	private Experiment experiment;
	private IFile remote;
	
	public ExperimentFileEditorInput (File json){
		this.experiment = GsonUtil.deserialize(json, Experiment.class);
		experiment.setLocation(json);
		remote = toIFile(json);
	}
	
	public ExperimentFileEditorInput (Experiment expr){
		this.experiment = expr;
		remote = toIFile(expr.getLocation());
	}
	
	private static IFile toIFile(File json) {
        IWorkspaceRoot root2 = ResourcesPlugin.getWorkspace().getRoot();
        IPath root = root2.getLocation();
        IPath location = Path.fromOSString(json.getAbsolutePath());
        IPath makeRelativeTo = location.makeRelativeTo(root);
        IFile file = root2.getFile(makeRelativeTo);
        return file;
    }
	
	
	@Override
	public IStorage getStorage() throws CoreException {
		return remote;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return experiment.getName();
	}
	
	public String getDescription(){
		return experiment.getDescription();
	}

	public List<String> getEquations(){
		return experiment.getFunctions();
	}
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public IFile getFile() {
		return remote;
	}

	public void setExperimentName(String text) {
		// TODO Auto-generated method stub
		experiment.setName(text);
	}

	public void setExperimentDesc(String text) {
		experiment.setDescription(text);
	}

	public Experiment getExperiment() {
		// TODO Auto-generated method stub
		return experiment;
	}

	public void setEquations(String[] items) {
		// TODO Auto-generated method stub
		experiment.setFunctions(Lists.newArrayList(items));
	}

	public List<Variable> getVariables() {
		// TODO Auto-generated method stub
		return experiment.getVariables();
	}

	public void addVariable(String id, String valS) throws NumberFormatException, IllegalArgumentException{
		// TODO Auto-generated method stub
		double val = 0;
		
		if(!valS.isEmpty()){
			val = Double.parseDouble(valS);
		}
		Variable var = new Variable(id, val);
		if(experiment.getVariables().contains(var)){
			throw new IllegalArgumentException("Variable allready exists!");
		}
		experiment.getVariables().add(var);
	}

	public void setVariable(Variable old, String id, String valS) throws NumberFormatException, IllegalArgumentException{
		// TODO Auto-generated method stub
		double val = 0;
		
		if(!valS.isEmpty()){
			val = Double.parseDouble(valS);
		}
		Variable newVar = new Variable(id, val);
		List<Variable> vars = experiment.getVariables();
		int index = vars.indexOf(old);
		vars.set(index, newVar);
		
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (!(obj instanceof IFileEditorInput)) {
            return false;
        }
        IFileEditorInput other = (IFileEditorInput) obj;
        return getFile().equals(other.getFile());
	}
}
