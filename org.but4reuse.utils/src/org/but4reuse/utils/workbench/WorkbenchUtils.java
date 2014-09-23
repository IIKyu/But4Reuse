package org.but4reuse.utils.workbench;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Workbench Utils
 * 
 * @author jabier.martinez
 */
public class WorkbenchUtils {

	public static IEditorPart getActiveEditorOfAGivenId(String editorId) {
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorReferences.length; i++) {
			IEditorReference iEditorReference = editorReferences[i];
			if (iEditorReference.getId().equals(editorId)) {
				IEditorPart editorPart = iEditorReference.getEditor(true);
				IWorkbenchPage iwpage = editorPart.getSite().getPage();
				if (iwpage.isPartVisible(editorPart)) {
					return iEditorReference.getEditor(true);
				}
			}
		}
		return null;
	}
	
	public static IViewPart forceShowView(String viewId){
		try {
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
		} catch (PartInitException e) {
			return null;
		}
	}
	
	public static void refreshAllWorkspace(){
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public static void openInEditor(IFile file){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		try {
			page.openEditor(new FileEditorInput(file), desc.getId());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	public static IFile getIFileFromFile(File file){
		IWorkspace workspace= ResourcesPlugin.getWorkspace();    
		IPath location= Path.fromOSString(file.getAbsolutePath()); 
		IFile ifile= workspace.getRoot().getFileForLocation(location);
		return ifile;
	}

}
