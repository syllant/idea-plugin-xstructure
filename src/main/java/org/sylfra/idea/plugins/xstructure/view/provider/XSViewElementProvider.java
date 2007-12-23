package org.sylfra.idea.plugins.xstructure.view.provider;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.xml.XmlStructureViewElementProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.view.XSModelTreeElement;

/**
 * Extension point to inject xStructure view
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSViewElementProvider implements XmlStructureViewElementProvider
{
  /**
   * {@inheritDoc}
   */
  @Nullable
  public StructureViewTreeElement createCustomXmlTagTreeElement(@NotNull final XmlTag tag)
  {
    final PsiFile psiFile = tag.getContainingFile();
    if (psiFile instanceof XmlFile)
    {
      return new XSModelTreeElement((XmlFile) psiFile);
    }

    return null;
  }
}
