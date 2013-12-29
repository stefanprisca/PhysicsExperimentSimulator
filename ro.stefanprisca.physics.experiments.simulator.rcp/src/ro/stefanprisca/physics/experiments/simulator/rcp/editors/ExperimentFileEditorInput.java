package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import java.io.File;
import java.util.AbstractList;
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
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;

import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;

public class ExperimentFileEditorInput implements IFileEditorInput {

	private Experiment experiment;
	private IFile remote;
	
	
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
		// TODO Auto-generated method stub
		return remote;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return experiment.getName();
	}
	
	public String getDescription(){
		return experiment.getDescription();
	}

	public List<String> getEquations(){
		ArrayList<String> eqs = new ArrayList<String>();
		for(Function eq : experiment.getFunctions()){
			eqs.add(eq.getEquation());
		}
		return eqs;
	}
	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFile getFile() {
		// TODO Auto-generated method stub
		return remote;
	}

}
