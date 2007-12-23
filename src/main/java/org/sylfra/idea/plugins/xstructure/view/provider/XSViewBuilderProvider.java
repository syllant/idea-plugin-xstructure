package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.impl.xml.XmlStructureViewBuilderProvider;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Extension point to inject xStructure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSViewBuilderProvider implements XmlStructureViewBuilderProvider
{
  /**
   * {@inheritDoc}
   */
  @Nullable
  public StructureViewBuilder createStructureViewBuilder(@NotNull final XmlFile file)
  {
    return new XSViewBuilder(file);
  }
}
