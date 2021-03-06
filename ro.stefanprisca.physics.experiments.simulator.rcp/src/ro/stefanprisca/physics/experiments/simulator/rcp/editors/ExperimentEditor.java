/*******************************************************************************
 * Copyright 2014 Stefan Prisca.
 ******************************************************************************/
package ro.stefanprisca.physics.experiments.simulator.rcp.editors;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.editor.FormEditor;

import ro.stefanprisca.physics.experiments.simulator.core.ExperimentStorage;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class ExperimentEditor extends FormEditor implements
		IResourceChangeListener {

	private static final String ID = "ro.stefanprisca.physics.experiments.simulator.rcp.editors.ExperimentEditor";
	private boolean dirty = false;

	@Override
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof ExperimentFileEditorInput)) {
			throw new PartInitException("Failed to create editor");
		}
		this.setPartName(editorInput.getName());
		super.init(site, editorInput);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addPages() {
		// TODO Auto-generated method stub
		try {
			addPage(new FieldEditorsPage(this));
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		ExperimentFileEditorInput input = (ExperimentFileEditorInput) getEditorInput();
		ExperimentStorage.getInstance().saveExperiment(input.getExperiment());
		this.setDirty(false);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDirty(boolean dirtyS) {
		this.dirty = dirtyS;
		editorDirtyStateChanged();
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public static String getId() {
		return ID;
	}

}
