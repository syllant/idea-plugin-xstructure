package org.sylfra.idea.plugins.xstructure.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;

/**
 * Action which loads the current mapping definition file in editor
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class EditMappingSetAction extends AnAction
{
  /**
   * Open mapping definition file in editor if available
   *
   * @param e the action event
   */
  public void actionPerformed(AnActionEvent e)
  {
    Project project = DataKeys.PROJECT.getData(e.getDataContext());
    Document document =
      FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument();

    PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
    if (!(psiFile instanceof XmlFile))
    {
      return;
    }

    XmlFile xmlFile = (XmlFile) psiFile;
    IXMappingSet xMappingSet =
      XStructurePlugin.getInstance().getXMappingSetRegistry().getSelectedXMappingSet(xmlFile);
    if (xMappingSet == null)
    {
      return;
    }

    FileEditorManager.getInstance(project).openFile(xMappingSet.getFile(), true);
  }
}