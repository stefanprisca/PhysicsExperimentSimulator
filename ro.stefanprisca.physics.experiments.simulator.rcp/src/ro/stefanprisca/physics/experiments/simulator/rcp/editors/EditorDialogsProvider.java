package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

import ro.stefanprisca.physics.experiments.simulator.core.Variable;

public class EditorDialogsProvider {

	private IEditorPart editor;
	private ExperimentFileEditorInput input;
	
	public EditorDialogsProvider(FormPage parent){
		editor = parent.getEditor();
		input = (ExperimentFileEditorInput) editor.getEditorInput();

	}
	
	public InputDialog createInputDialogEqAddEditor(Composite body, final List eqList){
		return new InputDialog(body.getShell(), "Add an equation",
				"Add a new equation to the experiment", "", null) {
			@Override
			protected void okPressed() {
				// TODO Auto-generated method stub
				eqList.add(getValue());
				input.getEquations().add(getValue());
				((ExperimentEditor) editor).setDirty(true);
				super.okPressed();
			}
		};
	}
	
	public InputDialog createInputDialogEqEditEditor(Composite body, String initialValue, final List eqList){
		return new InputDialog(body.getShell(), "Edit Equation",
				"Edit the selected equation", initialValue,
				null) {
			@Override
			protected void okPressed() {
				// TODO Auto-generated method stub
				eqList.setItem(eqList.getSelectionIndex(), getValue());
				input.setEquations(eqList.getItems());
				((ExperimentEditor) editor).setDirty(true);
				super.okPressed();
			}
		};
	}
	
	public Dialog createNewVariableEditor(final Shell shell, final Variable var, final Viewer variableTableViewer, final FormToolkit toolkit) {
		// TODO Auto-generated method stub
		return new Dialog(shell){
			
			private Text idTxt;
			private Text valueTxt;
			@Override
			protected Control createDialogArea(Composite parent) {
				// TODO Auto-generated method stub
				Composite cont = (Composite) super.createDialogArea(parent);
				cont.setLayout(new GridLayout(2, false));
				toolkit.createLabel(cont, "ID: ");
				idTxt = toolkit.createText(cont, var.getId(), SWT.BORDER);
				idTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,true,false));
	
				toolkit.createLabel(cont, "Value: ");
				valueTxt = toolkit.createText(cont, String.valueOf(var.getValue()), SWT.BORDER);
				valueTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,true,false));
				
				
				return cont;
			}
			
			@Override
			protected void okPressed() {
				String id = idTxt.getText();
				if(id != ""){
					try{
						
						input.setVariable(var, id, valueTxt.getText());
						variableTableViewer.refresh();
						((ExperimentEditor) editor).setDirty(true);
						super.okPressed();
					}catch (Exception e){
						MessageDialog.openError(shell, "Wrong input", "Please input corect values! \n" + e.getMessage());
					}
				}
				
			}
		};
	}
	
	public Dialog createNewVariableEditor(final Shell shell, final Viewer variableTableViewer, final FormToolkit toolkit) {
		// TODO Auto-generated method stub
		return new Dialog(shell){
			
			private Text idTxt;
			private Text valueTxt;
			@Override
			protected Control createDialogArea(Composite parent) {
				// TODO Auto-generated method stub
				Composite cont = (Composite) super.createDialogArea(parent);
				cont.setLayout(new GridLayout(2, false));
				toolkit.createLabel(cont, "ID: ");
				idTxt = toolkit.createText(cont, "", SWT.BORDER);
				idTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,true,false));
	
				toolkit.createLabel(cont, "Value: ");
				valueTxt = toolkit.createText(cont, "", SWT.BORDER);
				valueTxt.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,true,false));
				
				
				return cont;
			}
			
			@Override
			protected void okPressed() {
				String id = idTxt.getText();
				if(id != ""){
					try{
						input.addVariable(id, valueTxt.getText());
						variableTableViewer.refresh();
						((ExperimentEditor) editor).setDirty(true);
						super.okPressed();
					}catch (Exception e){
						MessageDialog.openError(shell, "Wrong input", "Please input corect values! \n" + e.getMessage());
					}
				}
				
			}
		};
	}

}
