package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.view.XSViewComponent;
import org.sylfra.idea.plugins.xstructure.view.XSViewTreeModel;

/**
 * Extension point to inject xStructure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSViewBuilder extends TreeBasedStructureViewBuilder
{
  private final XmlFile file;
  private final TreeBasedStructureViewBuilder nestedViewBuilder;

  public XSViewBuilder(XmlFile file, TreeBasedStructureViewBuilder nestedViewBuilder)
  {
    this.file = file;
    this.nestedViewBuilder = nestedViewBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public StructureViewModel createStructureViewModel()
  {
    IXMappingSet xMappingSet = XStructurePlugin.getInstance().getXMappingSetRegistry().getSelectedXMappingSet(file);
    if ((xMappingSet == null) && (nestedViewBuilder != null))
    {
      return nestedViewBuilder.createStructureViewModel();
    }
    
    return new XSViewTreeModel(file, xMappingSet);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRootNodeShown()
  {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public StructureView createStructureView(final FileEditor fileEditor, final Project project)
  {
    return new XSViewComponent(fileEditor, createStructureViewModel(), project);
  }
}
