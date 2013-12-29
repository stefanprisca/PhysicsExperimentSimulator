package ro.stefanprisca.physics.experiments.simulator.rcp.logging;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsoleStream;

public class ExperimentLogger extends Logger {

	private static ExperimentLogger logger;
	
	public static Logger getInstance(){
		if(logger == null){
			logger = new ExperimentLogger("Experiment Logger", null);
		}
		
		return logger;
	}
	
	protected ExperimentLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		
		
		MessageConsoleStream output = ConsoleFactory.getConsole().newMessageStream();
		
		try {
			
			output.write(msg+'\n');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void severe(String msg) {
		
		IWorkbenchPage wb = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart problems =wb.findView(IPageLayout.ID_PROBLEM_VIEW);
		wb.activate(problems);
		IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
		IResource resource = workspace.getRoot(); 

		
		MessageConsoleStream output = ConsoleFactory.getConsole().newMessageStream();
		output.setColor(new Color(null, 255, 0, 0));
		
		IMarker marker;
		try {
			marker = (IMarker) resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE,"Experiment ecountered problems: "+msg); 
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR); 
			
			output.write(msg+'\n');
		} catch (CoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	@Override
	public void fine(String msg) {
		// TODO Auto-generated method stub
		MessageConsoleStream output = ConsoleFactory.getConsole().newMessageStream();
		output.setColor(new Color(null, 0, 200, 50));
		try {
			output.write(msg+'\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
