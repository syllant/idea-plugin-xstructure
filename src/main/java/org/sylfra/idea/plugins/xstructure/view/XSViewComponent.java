package org.sylfra.idea.plugins.xstructure.view;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.newStructureView.StructureViewComponent;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.NodeRenderer;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.SimpleTextAttributes;
import org.sylfra.idea.plugins.xstructure.actions.EditMappingSetAction;
import org.sylfra.idea.plugins.xstructure.actions.ReloadAllMappingSetsAction;
import org.sylfra.idea.plugins.xstructure.actions.SelectMappingSetAction;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Custom implementation for structure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSViewComponent extends StructureViewComponent
{
  public XSViewComponent(FileEditor editor, StructureViewModel structureViewModel,
    Project project)
  {
    super(editor, structureViewModel, project);
    getTree().setCellRenderer(new XStructureNodeRenderer());
    ToolTipManager.sharedInstance().registerComponent(getTree());
  }

  /**
   * Creates xStructure toolbar
   *
   * @return xStructure toolbar
   */
  @Override
  protected ActionGroup createActionGroup()
  {
    SelectMappingSetAction selectMappingSetAction =
      (SelectMappingSetAction) ActionManager.getInstance()
        .getAction("XStructure.Actions.SelectMappingSet");
    EditMappingSetAction editMappingSetAction =
      (EditMappingSetAction) ActionManager.getInstance().getAction("XStructure.Actions.EditMappingSet");
    ReloadAllMappingSetsAction reloadAllMappingSetsAction =
      (ReloadAllMappingSetsAction) ActionManager.getInstance()
        .getAction("XStructure.Actions.ReloadAllMappingSets");

    XSViewTreeModel xsViewTreeModel = (XSViewTreeModel) getTreeModel();

    selectMappingSetAction.setXmlFile(xsViewTreeModel.getXmlFile());
    editMappingSetAction.getTemplatePresentation().setEnabled(
      (xsViewTreeModel.getXMappingSet() != null));

    DefaultActionGroup actionGroup = (DefaultActionGroup) super.createActionGroup();

    actionGroup.addSeparator();
    actionGroup.add(selectMappingSetAction);
    actionGroup.add(editMappingSetAction);
    actionGroup.add(reloadAllMappingSetsAction);

    return actionGroup;
  }

  /**
   * Custom node renderer to support xStructure features
   */
  private class XStructureNodeRenderer extends NodeRenderer
  {
    public void customizeCellRenderer(JTree tree,
      Object value,
      boolean selected,
      boolean expanded,
      boolean leaf,
      int row,
      boolean hasFocus)
    {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      if (node.getUserObject() instanceof AbstractTreeNode)
      {
        AbstractTreeNode userNode = (AbstractTreeNode) node.getUserObject();
        XSModelTreeElement treeElement = (XSModelTreeElement) userNode.getValue();

        if (treeElement.hasXStructureSupport())
        {
          String tagName = treeElement.getTagName();
          String targetLabel = treeElement.getTargetLabel();
          String targetTooltip = treeElement.getTargetTooltip();

          setIcon(expanded ? userNode.getOpenIcon() : userNode.getClosedIcon());

          setToolTipText(targetTooltip);

          append(tagName, SimpleTextAttributes.GRAYED_ATTRIBUTES);
          if (targetLabel != null)
          {
            append(" : " + targetLabel, SimpleTextAttributes.REGULAR_ATTRIBUTES);
          }

          return;
        }
      }

      // Default rendering if not using xStructure
      super.customizeCellRenderer(tree, value, selected, expanded, leaf, row, hasFocus);
    }
  }
}
