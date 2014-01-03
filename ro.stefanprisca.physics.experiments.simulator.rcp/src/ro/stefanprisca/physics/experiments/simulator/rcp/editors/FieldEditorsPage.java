/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class FieldEditorsPage extends FormPage {

	private EditorDialogsProvider edp;

	private ExperimentFileEditorInput input;

	private ListViewer equationsListViewer;
	private List eqList;

	private TableViewer variableTableViewer;
	private Table varTable;

	private FormToolkit toolkit;

	public FieldEditorsPage(FormEditor editor) {
		// TODO Auto-generated constructor stub
		super(editor, "r.s.p.e.s.e.fep", "Field Editors");
		input = (ExperimentFileEditorInput) editor.getEditorInput();
		edp = new EditorDialogsProvider(this);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// TODO Auto-generated method stub
		toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText("Experiment Fields");
		final Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		body.setLayout(new GridLayout(4, false));

		ExperimentFileEditorInput editorInput = (ExperimentFileEditorInput) getEditorInput();

		toolkit.createLabel(body, "Experiment Name: ");
		final Text exprNameTxt = toolkit
				.createText(body, editorInput.getName());

		exprNameTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				input.setExperimentName(exprNameTxt.getText());
				((ExperimentEditor) getEditor()).setDirty(true);
			}
		});

		GridData txtData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		txtData.widthHint = 200;

		exprNameTxt.setLayoutData(txtData);

		createEquationSection(body, toolkit);

		toolkit.createLabel(body, "Experiment Description: ", SWT.None);
		final Text descTxt = toolkit.createText(body,
				editorInput.getDescription(), SWT.MULTI | SWT.V_SCROLL
						| SWT.H_SCROLL);
		txtData = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		txtData.widthHint = 400;
		txtData.heightHint = 200;
		txtData.horizontalSpan = 2;
		txtData.verticalSpan = 2;
		descTxt.setLayoutData(txtData);

		descTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				input.setExperimentDesc(descTxt.getText());
				((ExperimentEditor) getEditor()).setDirty(true);
			}
		});

		createVariableSection(body);

		form.pack();
	}

	private void createEquationSection(final Composite body, FormToolkit toolkit) {
		final Section equationsSection = toolkit.createSection(body,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
		equationsSection.setText("Experiment Equations");
		equationsSection.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false, 2, 3));
		toolkit.paintBordersFor(equationsSection);

		Composite eqCont = toolkit.createComposite(equationsSection, SWT.FILL);
		toolkit.paintBordersFor(eqCont);
		toolkit.adapt(eqCont);
		equationsSection.setClient(eqCont);

		eqCont.setLayout(new GridLayout(3, false));

		equationsListViewer = new ListViewer(eqCont);
		equationsListViewer.setContentProvider(new ArrayContentProvider());
		equationsListViewer.setInput(input.getEquations());
		eqList = equationsListViewer.getList();
		eqList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		GridData eqBtnData = new GridData(SWT.FILL, SWT.BEGINNING, false,
				false, 1, 1);

		Button addEq = toolkit.createButton(eqCont, "Add Equation", SWT.PUSH);
		addEq.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				edp.createInputDialogEqAddEditor(body, eqList).open();
			}

		});

		addEq.setLayoutData(eqBtnData);

		final Button editEq = toolkit.createButton(eqCont, "Edit Equation",
				SWT.PUSH);
		editEq.setEnabled(false);
		editEq.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				edp.createInputDialogEqEditEditor(body,
						eqList.getSelection()[0], eqList).open();
			}

		});

		editEq.setLayoutData(eqBtnData);

		final Button removeEq = toolkit.createButton(eqCont, "Remove Equation",
				SWT.PUSH);
		removeEq.setEnabled(false);
		removeEq.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				input.getEquations().remove(eqList.getSelection()[0]);
				eqList.remove(eqList.getSelection()[0]);
				((ExperimentEditor) getEditor()).setDirty(true);
				removeEq.setEnabled(false);
			}

		});

		removeEq.setLayoutData(eqBtnData);

		eqBtnData = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1);
		// eqBtnData.horizontalIndent = 200;

		final Button moveEqUp = toolkit.createButton(eqCont, "Move Eq Up",
				SWT.PUSH);
		moveEqUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String selection = eqList.getSelection()[0];
				int index = eqList.getSelectionIndex();

				eqList.setItem(index, eqList.getItem(index - 1));
				eqList.setItem(index - 1, selection);
				eqList.setSelection(index - 1);
				input.setEquations(eqList.getItems());
				((ExperimentEditor) getEditor()).setDirty(true);
				removeEq.setEnabled(false);
			}

		});
		moveEqUp.setLayoutData(eqBtnData);
		moveEqUp.setEnabled(false);

		final Button moveEqDw = toolkit.createButton(eqCont, "Move Eq Down",
				SWT.PUSH);
		moveEqDw.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String selection = eqList.getSelection()[0];
				int index = eqList.getSelectionIndex();

				eqList.setItem(index, eqList.getItem(index + 1));
				eqList.setItem(index + 1, selection);
				eqList.setSelection(index + 1);
				input.setEquations(eqList.getItems());
				((ExperimentEditor) getEditor()).setDirty(true);
				removeEq.setEnabled(false);
			}

		});
		moveEqDw.setLayoutData(eqBtnData);
		moveEqDw.setEnabled(false);

		eqList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editEq.setEnabled(true);
				removeEq.setEnabled(true);
				if (eqList.getSelectionIndex() != 0)
					moveEqUp.setEnabled(true);
				else
					moveEqUp.setEnabled(false);
				if (eqList.getSelectionIndex() < eqList.getItemCount() - 1)
					moveEqDw.setEnabled(true);
				else
					moveEqDw.setEnabled(false);
			}
		});

		equationsSection.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getState()) {
					eqList.deselectAll();
					editEq.setEnabled(false);
					removeEq.setEnabled(false);
					moveEqUp.setEnabled(false);
					moveEqDw.setEnabled(false);
				}
			}
		});

	}

	private void createVariableSection(final Composite body) {
		Section varSection = toolkit.createSection(body,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
		varSection.setText("Experiment Variables");
		varSection.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
				false, 2, 2));

		toolkit.paintBordersFor(varSection);

		Composite varCont = toolkit.createComposite(varSection, SWT.FILL);
		toolkit.paintBordersFor(varCont);
		toolkit.adapt(varCont);
		varSection.setClient(varCont);

		varCont.setLayout(new GridLayout(2, true));
		variableTableViewer = new TableViewer(varCont, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(varCont, variableTableViewer);

		varTable = variableTableViewer.getTable();
		varTable.setHeaderVisible(true);

		varTable.setLinesVisible(true);

		varTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				3));

		variableTableViewer.setContentProvider(new ArrayContentProvider());
		variableTableViewer.setInput(input.getVariables());

		GridData varButtonData = new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 1);

		Button addVar = toolkit.createButton(varCont, "Add New", SWT.PUSH);
		addVar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				edp.createNewVariableEditor(body.getShell(),
						variableTableViewer, toolkit).open();

			}

		});
		addVar.setLayoutData(varButtonData);

		final Button editVar = toolkit.createButton(varCont, "Edit", SWT.PUSH);
		editVar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Variable var = (Variable) varTable.getSelection()[0].getData();
				edp.createNewVariableEditor(body.getShell(), var,
						variableTableViewer, toolkit).open();
				editVar.setEnabled(false);
			}

		});
		editVar.setLayoutData(varButtonData);
		editVar.setEnabled(false);

		final Button removeVar = toolkit.createButton(varCont, "Remove",
				SWT.PUSH);
		removeVar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Variable var = (Variable) varTable.getSelection()[0].getData();
				input.getVariables().remove(var);
				variableTableViewer.refresh();
				removeVar.setEnabled(false);
				((ExperimentEditor) getEditor()).setDirty(true);

			}

		});
		removeVar.setLayoutData(varButtonData);
		removeVar.setEnabled(false);

		varTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editVar.setEnabled(true);
				removeVar.setEnabled(true);
			}
		});

		varSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getState()) {
					editVar.setEnabled(false);
					removeVar.setEnabled(false);
				} else {
					variableTableViewer.refresh();
				}
			}
		});
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "ID", "Value" };
		int[] bounds = { 100, 100, 100, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Variable v = (Variable) element;
				return v.getId();
			}
		});

		// second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Variable v = (Variable) element;
				return String.valueOf(v.getValue());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				variableTableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
