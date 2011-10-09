package org.sylfra.idea.plugins.xstructure.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.util.XSUtils;

/**
 * Reloads all mapping definition files
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: ReloadAllMappingSetsAction.java 41 2009-03-27 21:15:38Z syllant $
 */
public class ReloadAllMappingSetsAction extends AnAction
{
  /**
   * Reloads all mapping definition files
   *
   * @param e the action event
   */
  public void actionPerformed(AnActionEvent e)
  {
    XStructurePlugin.getInstance().getXMappingSetRegistry().loadAll();

    Project project = DataKeys.PROJECT.getData(e.getDataContext());
    XSUtils.reloadStructureView(project);
  }
}
