package ro.stefanprisca.physics.experiments.simulator.rcp;

import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import static ro.stefanprisca.physics.experiments.simulator.computer.ExperimentComputer.compute;
import ro.stefanprisca.physics.experiments.simulator.ExperimentStorage;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.Function;

import com.google.inject.Inject;

public class ExperimentsView extends ViewPart {
	
	
	public static final String ID = "ro.stefanprisca.physics.experiments.simulator.view";
	
	@Inject
	private ExperimentStorage storage;

	private Group details;
	private ListViewer exViewer;
    private IViewerObservableValue selection;

	public void createPartControl(Composite parent) {
		
		final Composite contents = new Composite(parent, SWT.None);
		contents.setLayout(new GridLayout(2, false));
		
		Text searchExpr=new Text(contents, SWT.BORDER|SWT.SEARCH);
		searchExpr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		searchExpr.setMessage("Search for experiments!");
		
		exViewer = new ListViewer(contents);
		List experList=exViewer.getList();
		experList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 4));
		
		
		
		
		
		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		Button index = new Button (contents, SWT.PUSH);
		index.setText("Re-index Experiments");
		index.setLayoutData(buttonData);
		
		index.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				doGetExperiments();
			}
		});
		
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
		
		details = new Group(contents, SWT.BORDER);
		
		details.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		details.setVisible(false);
		selection=ViewersObservables.observeSingleSelection(exViewer);

		exViewer.setContentProvider(new ArrayContentProvider());
		exViewer.setInput(storage.getExperiments());
		exViewer.addOpenListener(new IOpenListener() {
			
			@Override
			public void open(OpenEvent event) {
				// TODO Auto-generated method stub
				doSetUpDetails();
			}
			
		});
	}
	private void doSetUpDetails() {
		for(Control c:details.getChildren()){
			c.dispose();
		}
		details.setLayout(new GridLayout(1, false));

		details.setFocus();
		final Experiment expr = (Experiment)selection.getValue();
		Label name = new Label(details, SWT.None);
		name.setText("Name: "+expr.getName());
		Label description = new Label(details, SWT.None);
		description.setText("Description: "+expr.getDescription());
		Label formulasTxt = new Label(details, SWT.None);
		formulasTxt.setText("Functions: ");
		List formulas = new List(details, SWT.None);
		for(Function f: expr.getFunctions())
			formulas.add(f.getEquation());

		Button run = new Button(details, SWT.PUSH);
		run.setText("Run the experiment");
		run.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		run.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				compute(expr);
			}
		});
		
		details.setVisible(true);
		details.layout();
	}

	private void doGetExperiments() {
		// TODO Auto-generated method stub
		exViewer.setInput(storage.getExperiments());
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}