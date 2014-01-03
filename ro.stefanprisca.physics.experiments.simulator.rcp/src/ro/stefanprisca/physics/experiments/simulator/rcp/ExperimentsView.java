/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.rcp;

import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

import ro.stefanprisca.physics.experiments.simulator.computer.ExperimentComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Experiment;
import ro.stefanprisca.physics.experiments.simulator.core.ExperimentStorage;
import ro.stefanprisca.physics.experiments.simulator.rcp.editors.ExperimentEditor;
import ro.stefanprisca.physics.experiments.simulator.rcp.editors.ExperimentFileEditorInput;
import ro.stefanprisca.physics.experiments.simulator.rcp.wizards.NewExperimentWizard;

public class ExperimentsView extends ViewPart {

	public static final String ID = "ro.stefanprisca.physics.experiments.simulator.view";

	private ExperimentStorage storage = ExperimentStorage.getInstance();

	private Group details;
	private ListViewer exViewer;
	private IViewerObservableValue selection;

	public void createPartControl(final Composite parent) {

		final ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

		final Composite contents = new Composite(sc, SWT.None);

		sc.setContent(contents);
		contents.setLayout(new GridLayout(2, false));

		final Text searchExpr = new Text(contents, SWT.BORDER | SWT.SEARCH);
		searchExpr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		searchExpr.setMessage("Search for experiments!");
		searchExpr.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				doGetExperiments(searchExpr.getText());
			}
		});

		exViewer = new ListViewer(contents);
		exViewer.setComparator(new ViewerComparator());
		List experList = exViewer.getList();
		GridData listData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 5);
		listData.widthHint = 300;
		listData.heightHint = 200;
		experList.setLayoutData(listData);

		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);

		Button index = new Button(contents, SWT.PUSH);
		index.setText("Re-index");
		index.setLayoutData(buttonData);

		index.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doGetExperiments();
			}
		});

		Button addNew = new Button(contents, SWT.PUSH);
		addNew.setText("Add New");
		addNew.setLayoutData(buttonData);

		// addNew.setEnabled(false);
		addNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WizardDialog wizardDialog = new WizardDialog(parent.getShell(),
						new NewExperimentWizard());
				if (wizardDialog.open() == Window.OK) {
					System.out.println("Ok pressed");
				} else {
					System.out.println("Cancel pressed");
				}
			}
		});

		final Button edit = new Button(contents, SWT.PUSH);
		edit.setText("Edit");
		edit.setLayoutData(buttonData);
		edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				ExperimentFileEditorInput input = new ExperimentFileEditorInput(
						(Experiment) selection.getValue());
				try {
					page.openEditor(input, ExperimentEditor.getId());
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		edit.setEnabled(false);

		final Button remove = new Button(contents, SWT.PUSH);
		remove.setText("Remove");
		remove.setLayoutData(buttonData);

		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (MessageDialog.openConfirm(
						parent.getShell(),
						"Confirm Deletion",
						"Are you sure you want to delete it?\n"
								+ "This will remove the file from the file-system as well.")) {
					Experiment exp = (Experiment) selection.getValue();
					exp.getLocation().delete();
					exViewer.remove(exp);
				}
			}
		});

		remove.setEnabled(false);

		details = new Group(contents, SWT.BORDER);

		details.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 4));
		details.setVisible(true);
		selection = ViewersObservables.observeSingleSelection(exViewer);

		exViewer.setContentProvider(new ArrayContentProvider());
		exViewer.setInput(storage.getExperiments());
		exViewer.addOpenListener(new IOpenListener() {

			@Override
			public void open(OpenEvent event) {
				// TODO Auto-generated method stub
				if (selection.getValue() != null) {
					doSetUpDetails();
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					ExperimentFileEditorInput input = new ExperimentFileEditorInput(
							(Experiment) selection.getValue());
					try {
						page.openEditor(input, ExperimentEditor.getId());
					} catch (PartInitException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		experList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				remove.setEnabled(true);
				edit.setEnabled(true);
			}
		});

		contents.setSize(contents.computeSize(550, 800));
	}

	private void doSetUpDetails() {
		for (Control c : details.getChildren()) {
			c.dispose();
		}
		details.setLayout(new GridLayout(1, false));

		details.setFocus();
		final Experiment expr = (Experiment) selection.getValue();

		Label name = new Label(details, SWT.None);
		name.setText("~Name: " + expr.getName());
		Label description = new Label(details, SWT.None);
		description.setText("~Description: "
				+ expr.getDescription().substring(0,
						Math.min(expr.getDescription().length(), 100)));
		Label formulasTxt = new Label(details, SWT.None);
		formulasTxt.setText("Functions: ");
		List formulas = new List(details, SWT.None);

		formulas.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		for (String f : expr.getFunctions())
			formulas.add(f);

		Button run = new Button(details, SWT.PUSH);
		run.setText("Run the experiment");
		run.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		run.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				ExperimentComputer.compute(expr);

			}
		});

		Button runFor = new Button(details, SWT.PUSH);
		runFor.setText("Run for T");
		runFor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		final Text tPeriod = new Text(details, SWT.BORDER);
		tPeriod.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1));
		tPeriod.setMessage("period T");
		runFor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!tPeriod.getText().isEmpty())
					ExperimentComputer.compute(expr, tPeriod.getText());

			}
		});

		details.setVisible(true);
		details.layout();
	}

	private void doGetExperiments() {
		java.util.Set<Experiment> experiments = storage.getExperiments();
		exViewer.setInput(experiments);
		/*
		 * for(Experiment e : experiments) exViewer.getList().add(e.toString());
		 */
		exViewer.refresh();
	}

	private void doGetExperiments(String filter) {
		java.util.Set<Experiment> experiments = storage.getExperiments(filter);
		exViewer.setInput(experiments);
		/*
		 * for(Experiment e : experiments) exViewer.getList().add(e.toString());
		 */
		exViewer.refresh();

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}
