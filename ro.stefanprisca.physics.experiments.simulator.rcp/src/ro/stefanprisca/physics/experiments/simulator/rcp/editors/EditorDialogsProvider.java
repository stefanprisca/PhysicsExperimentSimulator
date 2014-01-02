package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;



import ro.stefanprisca.physics.experiments.simulator.computer.DelegatingMathComputer;
import ro.stefanprisca.physics.experiments.simulator.core.IComputer;
import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class EditorDialogsProvider {
	private class ExperimentInputDialogEditor extends InputDialog {

		private ElementListSelectionDialog formulaSel;

		public ExperimentInputDialogEditor(Shell parentShell, String dialogTitle,
				String dialogMessage, String initialValue,
				IInputValidator validator) {
			super(parentShell, dialogTitle, dialogMessage, initialValue,
					validator);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			// TODO Auto-generated method stub
			Composite contents = (Composite) super.createDialogArea(parent);
			Button insertFormula = new Button(contents, SWT.PUSH);
			insertFormula.setText("Insert Math Formula");
			insertFormula.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					formulaSel = new ElementListSelectionDialog(getShell(),
							new LabelProvider());
					formulaSel.setTitle("Select Formula");
					formulaSel.setElements(DelegatingMathComputer
							.getPrettyMathMethodsArray());
					formulaSel.open();
					String formula = parseFormula(formulaSel.getFirstResult());
					getText().append(formula);
				
				}

				
			});

			return contents;
		}
	}

	private IEditorPart editor;
	private ExperimentFileEditorInput input;
	private FormPage parent;
	
	public EditorDialogsProvider(FormPage parent) {
		this.parent=parent;
		editor = parent.getEditor();
		input = (ExperimentFileEditorInput) editor.getEditorInput();

	}

	public InputDialog createInputDialogEqAddEditor(Composite body,
			final List eqList) {
		return new ExperimentInputDialogEditor(body.getShell(), "Add an equation",
				"Add a new equation to the experiment", "", null) {
			@Override
			protected void okPressed() {
				ensureExistingVariables(getText().getText());
				eqList.add(getValue());
				input.getEquations().add(getValue());
				((ExperimentEditor) editor).setDirty(true);
				super.okPressed();
			}

		};
	}

	public InputDialog createInputDialogEqEditEditor(Composite body,
			String initialValue, final List eqList) {
		return new ExperimentInputDialogEditor(body.getShell(), "Edit Equation",
				"Edit the selected equation", initialValue, null) {
			@Override
			protected void okPressed() {
				
				ensureExistingVariables(getText().getText());
				
				eqList.setItem(eqList.getSelectionIndex(), getValue());
				input.setEquations(eqList.getItems());
				((ExperimentEditor) editor).setDirty(true);
				super.okPressed();
			}

			

		};
	}

	public Dialog createNewVariableEditor(final Shell shell,
			final Variable var, final Viewer variableTableViewer,
			final FormToolkit toolkit) {
		// TODO Auto-generated method stub
		return new Dialog(shell) {

			private Text idTxt;
			private Text valueTxt;

			@Override
			protected Control createDialogArea(Composite parent) {
				// TODO Auto-generated method stub
				Composite cont = (Composite) super.createDialogArea(parent);
				cont.setLayout(new GridLayout(2, false));
				toolkit.createLabel(cont, "ID: ");
				idTxt = toolkit.createText(cont, var.getId(), SWT.BORDER);
				idTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));

				toolkit.createLabel(cont, "Value: ");
				valueTxt = toolkit.createText(cont,
						String.valueOf(var.getValue()), SWT.BORDER);
				valueTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
						true, false));

				return cont;
			}

			@Override
			protected void okPressed() {
				String id = idTxt.getText();
				if (id != "") {
					try {

						input.setVariable(var, id, valueTxt.getText());
						variableTableViewer.refresh();
						((ExperimentEditor) editor).setDirty(true);
						super.okPressed();
					} catch (Exception e) {
						MessageDialog.openError(
								shell,
								"Wrong input",
								"Please input corect values! \n"
										+ e.getMessage());
					}
				}

			}
		};
	}

	public Dialog createNewVariableEditor(final Shell shell,
			final Viewer variableTableViewer, final FormToolkit toolkit) {
		// TODO Auto-generated method stub
		return new Dialog(shell) {

			private Text idTxt;
			private Text valueTxt;

			@Override
			protected Control createDialogArea(Composite parent) {
				// TODO Auto-generated method stub
				Composite cont = (Composite) super.createDialogArea(parent);
				cont.setLayout(new GridLayout(2, false));
				toolkit.createLabel(cont, "ID: ");
				idTxt = toolkit.createText(cont, "", SWT.BORDER);
				idTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));

				toolkit.createLabel(cont, "Value: ");
				valueTxt = toolkit.createText(cont, "", SWT.BORDER);
				valueTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
						true, false));

				return cont;
			}

			@Override
			protected void okPressed() {
				String id = idTxt.getText();
				if (id != "") {
					try {
						input.addVariable(id, valueTxt.getText());
						variableTableViewer.refresh();
						((ExperimentEditor) editor).setDirty(true);
						super.okPressed();
					} catch (Exception e) {
						MessageDialog.openError(
								shell,
								"Wrong input",
								"Please input corect values! \n"
										+ e.getMessage());
					}
				}

			}
		};
	}

	private String parseFormula(Object firstResult) {
		String result = (String)firstResult;
		String parameters = result.substring(result.indexOf('(')+1, result.indexOf(')'));
		for(String s : parameters.split(",")){
			result = result.replaceFirst(s, "{}");
		}
		return result;
	}
	
	private void ensureExistingVariables(String equation) {
		// TODO Auto-generated method stub
		Matcher m = Pattern.compile(IComputer.VARIABLE_PATTERN).matcher(equation);
		Variable v = new Variable("", 0);
		String group;
		while(m.find()){
			group = m.group();
			v.setId(group.substring(group.indexOf('{')+1, group.indexOf('}')));
			if(!input.getVariables().contains(v)){
				if(MessageDialog.openQuestion(parent.getPartControl().getShell(), "Non Existing Variable", 
												"The variable {"+ v.getId()+ "} does not exist. Would you like to add it?")){
					input.addVariable(v.getId(), "0");
					((ExperimentEditor) editor).setDirty(true);
				};
			}
		}
	}
}
