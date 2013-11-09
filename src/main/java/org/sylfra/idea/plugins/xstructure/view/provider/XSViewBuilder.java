package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.view.XSViewComponent;
import org.sylfra.idea.plugins.xstructure.view.XSViewTreeModel;

/**
 * Extension point to inject xStructure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XSViewBuilder.java 55 2009-09-08 17:56:46Z syllant $
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

  @NotNull
  @Override
  public StructureViewModel createStructureViewModel(@Nullable Editor editor)
  {
    IXMappingSet xMappingSet = XStructurePlugin.getInstance().getXMappingSetRegistry().getSelectedXMappingSet(file);
    if ((xMappingSet == null) && (nestedViewBuilder != null))
    {
      return nestedViewBuilder.createStructureViewModel(editor);
    }
    
    return new XSViewTreeModel(editor, file, xMappingSet);
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
    return new XSViewComponent(fileEditor, createStructureViewModel(null), project);
  }
}
