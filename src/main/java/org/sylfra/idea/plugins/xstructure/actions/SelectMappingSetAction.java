package org.sylfra.idea.plugins.xstructure.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ex.CheckboxAction;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.XSIconManager;
import org.sylfra.idea.plugins.xstructure.XSMessageManager;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.registry.XMappingSetRegistry;
import org.sylfra.idea.plugins.xstructure.util.XSUtils;

import javax.swing.*;
import java.util.Set;

/**
 * Select a mapping set from a combo box for the current edited file
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class SelectMappingSetAction extends ComboBoxAction
{
  /**
   * The file being edited
   */
  private XmlFile xmlFile;

  /**
   * Set the current edited file
   *
   * @param xmlFile the current edited file
   */
  public void setXmlFile(XmlFile xmlFile)
  {
    this.xmlFile = xmlFile;
  }

  /**
   * Creates the combo box items, from all eligible mapping sets for the current edited file
   */
  @NotNull
  protected DefaultActionGroup createPopupActionGroup(JComponent button)
  {
    XMappingSetRegistry mappingSetRegistry =
      XStructurePlugin.getInstance().getXMappingSetRegistry();

    Set<IXMappingSet> xMappingSets = mappingSetRegistry.getAvailableXMappingSets(xmlFile);
    IXMappingSet selectedXMappingSet = mappingSetRegistry.getSelectedXMappingSet(xmlFile);

    DefaultActionGroup actionGroup = new DefaultActionGroup();

    // An item to select no mapping set
    actionGroup.add(new XMappingSetChoiceAction(xmlFile, null,
      (selectedXMappingSet == null)));

    if ((xMappingSets == null) || (xMappingSets.isEmpty()))
    {
      actionGroup.add(new NoMappingSetConfigAction());
    }
    else
    {
      for (IXMappingSet xMappingSet : xMappingSets)
      {
        actionGroup.add(new XMappingSetChoiceAction(xmlFile, xMappingSet,
          (selectedXMappingSet == xMappingSet)));
      }
    }
    return actionGroup;
  }

  /**
   * A checkbox related to an eligible mapping set
   */
  private static class XMappingSetChoiceAction extends CheckboxAction
  {
    private XmlFile xmlFile;
    private IXMappingSet xMappingSet;
    private boolean selected;

    public XMappingSetChoiceAction(XmlFile xmlFile, IXMappingSet xMappingSet, boolean selected)
    {
      super((xMappingSet == null)
        ? XSMessageManager.message("action.XStructure.Actions.SelectNoMappingSet.text")
        : (xMappingSet.getName() +
        ((xMappingSet.getVersion() == null) ? "" : " - " + xMappingSet.getVersion())),
        (xMappingSet == null)
          ? XSMessageManager.message("action.XStructure.Actions.SelectNoMappingSet.description")
          : xMappingSet.getFile().getName(),
        selected ? XSIconManager.getIcon(XSIconManager.IconRef.check) : null);
      this.xmlFile = xmlFile;
      this.xMappingSet = xMappingSet;
      this.selected = selected;
    }

    /**
     * Returns true if the item is the selected mapping set, false otherwise
     *
     * @param e the action event
     *
     * @return true if the item is the selected mapping set, false otherwise
     */
    public boolean isSelected(AnActionEvent e)
    {
      return selected;
    }

    /**
     * Select the related mapping set
     *
     * @param e     an action event
     * @param state selection state
     */
    public void setSelected(AnActionEvent e, boolean state)
    {
      final Project project = DataKeys.PROJECT.getData(e.getDataContext());

      XMappingSetRegistry mappingSetRegistry =
        XStructurePlugin.getInstance().getXMappingSetRegistry();

      if (mappingSetRegistry.getSelectedXMappingSet(xmlFile) == xMappingSet)
      {
        return;
      }

      mappingSetRegistry.setSelectedXMappingSet(xmlFile, xMappingSet);

      XSUtils.reloadStructureView(project);
    }
  }

  /**
   * Action used to display a combo box item which notifies than no mapping set exists for
   * the edited file
   */
  private class NoMappingSetConfigAction extends AnAction
  {
    private NoMappingSetConfigAction()
    {
      super(XSMessageManager.message("action.XStructure.Actions.NoMappingSet.text"),
        XSMessageManager.message("action.XStructure.Actions.NoMappingSet.description"),
        null);
    }

    public void actionPerformed(AnActionEvent e)
    {
    }
  }
}