package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
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

  public XSViewBuilder(XmlFile file)
  {
    this.file = file;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public StructureViewModel createStructureViewModel()
  {
    return new XSViewTreeModel(file);
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
