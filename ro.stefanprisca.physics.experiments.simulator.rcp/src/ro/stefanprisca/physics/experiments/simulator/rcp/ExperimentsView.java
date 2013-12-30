package ro.stefanprisca.physics.experiments.simulator.rcp;

import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import static ro.stefanprisca.physics.experiments.simulator.computer.ExperimentComputer.compute;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.ExperimentStorage;
import ro.stefanprisca.physics.experiments.simulator.rcp.editors.ExperimentFileEditorInput;

public class ExperimentsView extends ViewPart {
	
	
	public static final String ID = "ro.stefanprisca.physics.experiments.simulator.view";
	
	
	private ExperimentStorage storage = ExperimentStorage.getInstance();

	private Group details;
	private ListViewer exViewer;
    private IViewerObservableValue selection;

	public void createPartControl(Composite parent) {
		
		final ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		final Composite contents = new Composite(sc, SWT.None);
		
		sc.setContent(contents);
		contents.setLayout(new GridLayout(2, false));
		
		Text searchExpr=new Text(contents, SWT.BORDER|SWT.SEARCH);
		searchExpr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		searchExpr.setMessage("Search for experiments!");
		
		exViewer = new ListViewer(contents);
		List experList=exViewer.getList();
		experList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 4));
		
		
		
		
		
		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		Button index = new Button (contents, SWT.PUSH);
		index.setText("Re-index");
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
		edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					page.openEditor(new ExperimentFileEditorInput((Experiment)selection.getValue()), "ro.stefanprisca.physics.experiments.simulator.rcp.editors.ExperimentEditor");
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
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
		
		contents.setSize(contents.computeSize(500, 600));
	}
	private void doSetUpDetails() {
		for(Control c:details.getChildren()){
			c.dispose();
		}
		details.setLayout(new GridLayout(1, false));

		details.setFocus();
		final Experiment expr = (Experiment)selection.getValue();
		
		Label name = new Label(details, SWT.None);
		name.setText("~Name: "+expr.getName());
		Label description = new Label(details, SWT.None);
		description.setText("~Description: "+expr.getDescription());
		Label formulasTxt = new Label(details, SWT.None);
		formulasTxt.setText("Functions: ");
		List formulas = new List(details, SWT.None);
		
		formulas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		for(String f: expr.getFunctions())
			formulas.add(f);

		Button run = new Button(details, SWT.PUSH);
		run.setText("Run the experiment");
		run.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		run.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				
				compute(expr);
						
			}
		});
		
		Button runFor = new Button(details, SWT.PUSH);
		runFor.setText("Run for T");
		runFor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		final Text tPeriod = new Text (details, SWT.BORDER);
		tPeriod.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		tPeriod.setMessage("period T");
		runFor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				compute(expr, tPeriod.getText());
						
			}
		});
		
		details.setVisible(true);
		details.layout();
	}

	private void doGetExperiments() {
		// TODO Auto-generated method stub
		exViewer.setInput(storage.getExperiments());
		exViewer.refresh();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}