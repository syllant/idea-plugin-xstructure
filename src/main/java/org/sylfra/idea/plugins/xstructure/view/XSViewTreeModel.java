package org.sylfra.idea.plugins.xstructure.view;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.xml.XmlStructureViewTreeModel;
import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.Grouper;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.Disposable;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;

/**
 * TreeModel implementation for xStructure
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSViewTreeModel extends XmlStructureViewTreeModel implements Disposable
{
  private final XSModelTreeElement root;
  private IXMappingSet xMappingSet;
  private XmlFile xmlFile;

  public XSViewTreeModel(@NotNull XmlFile xmlFile)
  {
    super(xmlFile);
    this.xmlFile = xmlFile;
    root = new XSModelTreeElement((XmlFile) getPsiFile());

    xMappingSet =
      XStructurePlugin.getInstance().getXMappingSetRegistry().getSelectedXMappingSet(xmlFile);
  }

  /**
   * Returns the associated XML file
   *
   * @return the associated XML file
   */
  @NotNull
  public XmlFile getXmlFile()
  {
    return xmlFile;
  }

  /**
   * Returns the associated mapping set
   *
   * @return the associated mapping set
   */
  @Nullable
  public IXMappingSet getXMappingSet()
  {
    return xMappingSet;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public StructureViewTreeElement getRoot()
  {
    return root;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public Grouper[] getGroupers()
  {
    return Grouper.EMPTY_ARRAY;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public Sorter[] getSorters()
  {
    return new Sorter[]{Sorter.ALPHA_SORTER};
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public Filter[] getFilters()
  {
    return Filter.EMPTY_ARRAY;
  }
}
