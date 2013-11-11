package ro.stefanprisca.physics.experiments.simulator.rcp;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ExperimentsView extends ViewPart {
	public static final String ID = "ro.stefanprisca.physics.experiments.simulator.view";

	private ListViewer experiments;
	
	public void createPartControl(Composite parent) {
		
		Composite contents = new Composite(parent, SWT.None);
		contents.setLayout(new GridLayout(2, false));
		
		Text searchExpr=new Text(contents, SWT.BORDER|SWT.SEARCH);
		searchExpr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		searchExpr.setMessage("Search for experiments!");
		
		experiments = new ListViewer(contents);
		List experList=experiments.getList();
		experList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3));
		experiments.add("First experiment");
		experiments.add("second expr");
		
		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		Button addNew = new Button (contents, SWT.PUSH);
		addNew.setText("Add a new experiment");
		addNew.setLayoutData(buttonData);
		
		addNew.setEnabled(false);
		
		Button edit = new Button (contents, SWT.PUSH);
		edit.setText("Edit");
		edit.setLayoutData(buttonData);
		
		Button remove = new Button (contents, SWT.PUSH);
		remove.setText("Remove");
		remove.setLayoutData(buttonData);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}