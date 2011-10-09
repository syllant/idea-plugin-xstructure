package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.ide.structureView.xml.XmlStructureViewBuilderProvider;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Extension point to inject xStructure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XSViewBuilderProvider.java 49 2009-04-09 19:33:43Z syllant $
 */
public class XSViewBuilderProvider implements XmlStructureViewBuilderProvider
{
  /**
   * {@inheritDoc}
   */
  @Nullable
  public StructureViewBuilder createStructureViewBuilder(@NotNull final XmlFile file)
  {
    StructureViewBuilder nestedViewBuilder = null;
    XmlStructureViewBuilderProvider[] viewBuilderProviders = getAllViewBuilderProviders();
    for (XmlStructureViewBuilderProvider viewBuilderProvider : viewBuilderProviders)
    {
      if (!viewBuilderProvider.equals(this))
      {
        nestedViewBuilder = viewBuilderProvider.createStructureViewBuilder(file);
        if (nestedViewBuilder != null)
        {
          if (nestedViewBuilder instanceof TreeBasedStructureViewBuilder)
          {
            break;
          }

          return nestedViewBuilder;
        }
      }
    }

    return new XSViewBuilder(file, (TreeBasedStructureViewBuilder) nestedViewBuilder);
  }

  private XmlStructureViewBuilderProvider[] getAllViewBuilderProviders()
  {
    return (XmlStructureViewBuilderProvider[]) Extensions.getExtensions("com.intellij.xmlStructureViewBuilderProvider");
  }
}
