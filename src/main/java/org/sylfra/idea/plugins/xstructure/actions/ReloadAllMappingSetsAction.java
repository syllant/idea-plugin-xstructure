package org.sylfra.idea.plugins.xstructure.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;

/**
 * Reloads all mapping definition files
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
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
  }
}
