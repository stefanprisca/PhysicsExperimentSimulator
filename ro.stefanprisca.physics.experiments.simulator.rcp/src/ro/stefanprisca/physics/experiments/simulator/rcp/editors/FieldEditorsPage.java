package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class FieldEditorsPage extends FormPage {

	public FieldEditorsPage(FormEditor editor) {
		// TODO Auto-generated constructor stub
		super(editor,"r.s.p.e.s.e.fep", "Field Editors");
	}
	
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// TODO Auto-generated method stub
		FormToolkit toolkit =  managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
        form.setText("Experiment Fields");
        Composite body = form.getBody();
        toolkit.decorateFormHeading(form.getForm());
        toolkit.paintBordersFor(body);
        body.setLayout(new GridLayout(2, false));

        ExperimentFileEditorInput editorInput = (ExperimentFileEditorInput) getEditorInput();
        
        Label exprName = toolkit.createLabel(body,"Experiment Name: ");
        Text exprNameTxt = toolkit.createText(body, editorInput.getName());
        
        GridData txtData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
        txtData.widthHint = 100;
        
        exprNameTxt.setLayoutData(txtData);
        
        
        Label exprDesc = toolkit.createLabel(body, "Experiment Description: "); 
        
		form.pack();
	}
	
}
