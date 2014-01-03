package ro.stefanprisca.physics.experiments.simulator.rcp.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import ro.stefanprisca.physics.experiments.simulator.rcp.logging.ConsoleFactory;


/**
 *  This class is meant to serve as an example for how various contributions 
 *  are made to a perspective. Note that some of the extension point id's are
 *  referred to as API constants while others are hardcoded and may be subject 
 *  to change. 
 */
public class RuntimePerspective implements IPerspectiveFactory {

	private static String ID = "ro.stefanprisca.physics.experiments.simulator.rcp.enviroment.RuntimePerspective";
	
	private IPageLayout factory;

	public RuntimePerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		
		this.factory = factory;
		addViews();

	}

	private void addViews() {
		// Creates the overall folder layout. 
		// Note that each new Folder uses a percentage of the remaining EditorArea.
		IFolderLayout bottom =
			factory.createFolder(
				"bottomRight", //NON-NLS-1
				IPageLayout.BOTTOM,
				0.75f,
				factory.getEditorArea());
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		ConsoleFactory cs = new ConsoleFactory();
		cs.openConsole();
	
	}

	public static String getID(){
		return ID;
	}

}
