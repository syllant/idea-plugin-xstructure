package org.sylfra.idea.plugins.xstructure.view;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Iconable;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Overrides default StructureViewTreeElement to build nodes according to xStructure features
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSModelTreeElement implements StructureViewTreeElement, ItemPresentation
{
  private static final Logger LOGGER =
    Logger.getInstance(XSModelTreeElement.class.getName());

  @Nullable
  private XmlTag xmlTag;
  @Nullable
  private IXMapping xMapping;
  @Nullable
  private IXMappingSet xMappingSet;

  /**
   * Constructor which should be used to build tree from root
   *
   * @param xmlFile the current XML file
   */
  public XSModelTreeElement(XmlFile xmlFile)
  {
    xmlTag = (xmlFile.getDocument() == null) ? null : xmlFile.getDocument().getRootTag();

    xMappingSet =
      XStructurePlugin.getInstance().getXMappingSetRegistry().getSelectedXMappingSet(xmlFile);

    if ((xMappingSet != null) && (xmlTag != null))
    {
      xMapping = xMappingSet.getMappingResolver().findMatchingMapping(xMappingSet, xmlTag);
    }
  }

  /**
   * Internal constructor to build nested nodes
   *
   * @param xmlTag      the associated XML tag
   * @param xMappingSet the associated mapping set
   * @param xMapping    the associated mapping
   */
  protected XSModelTreeElement(XmlTag xmlTag, IXMappingSet xMappingSet, IXMapping xMapping)
  {
    this.xmlTag = xmlTag;
    this.xMappingSet = xMappingSet;
    this.xMapping = xMapping;
  }

  /**
   * Returns the XML tag associated to this node
   *
   * @return the XML tag associated to this node if valid, null otherwise
   */
  private XmlTag getElement()
  {
    return ((xmlTag == null) || (!xmlTag.isValid())) ? null : xmlTag;
  }

  /**
   * Returns true if node is managed through a mapping set, false otherwise
   *
   * @return true if node is managed through a mapping set, false otherwise
   */
  public boolean hasXStructureSupport()
  {
    return xMappingSet != null;
  }

  /**
   * {@inheritDoc}
   */
  public Object getValue()
  {
    return xmlTag;
  }

  /**
   * {@inheritDoc}
   */
  public ItemPresentation getPresentation()
  {
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public TreeElement[] getChildren()
  {
    // Filter children according to skip mode

    IXMapping.SkipMode currentSkipMode = findSkipMode(xMapping);

    List<TreeElement> treeElements = new ArrayList<TreeElement>();
    if (xmlTag != null)
    {
      for (XmlTag subTag : xmlTag.getSubTags())
      {
        IXMapping childXMapping = (xMappingSet == null)
          ? null : xMappingSet.getMappingResolver().findMatchingMapping(xMappingSet, subTag);

        IXMapping.SkipMode childSkipMode = findSkipMode(childXMapping);
        switch (childSkipMode)
        {
          // No skip
          case NONE:
            // If current node is defined with 'children' skip mode, children which override
            // explicitely this property will be displayed
            boolean childrenSkipped = IXMapping.SkipMode.CHILDREN.equals(currentSkipMode)
              || IXMapping.SkipMode.ALL.equals(currentSkipMode);
            if ((!childrenSkipped) || ((childXMapping != null)))
            {
              treeElements.add(
                new XSModelTreeElement(subTag, xMappingSet, childXMapping));
            }
            break;

            // Skip only this node, but show its children
          case THIS:
            XSModelTreeElement subTreeElement =
              new XSModelTreeElement(subTag, xMappingSet, childXMapping);
            TreeElement[] subChildrenTreeElements = subTreeElement.getChildren();
            treeElements.addAll(Arrays.<TreeElement>asList(subChildrenTreeElements));
            break;

            // Show this node but skip its children
          case CHILDREN:
            treeElements.add(
              new XSModelTreeElement(subTag, xMappingSet, childXMapping));
            break;

            // Skip this node and its children
          case ALL:
            // Stop building branch
            break;
        }
      }
    }

    return treeElements.toArray(new TreeElement[treeElements.size()]);
  }

  /**
   * Find the skip mode for this mapping, using default skip mode defined on mapping set if
   * no skip mode was provided for mapping
   *
   * @param childXMapping the request mapping
   *
   * @return skip mode for this mapping
   */
  @NotNull
  private IXMapping.SkipMode findSkipMode(@Nullable IXMapping childXMapping)
  {
    IXMapping.SkipMode skipMode;
    if (childXMapping == null)
    {
      skipMode = ((xMappingSet == null) || (xMappingSet.getDefaultSkipMode() == null))
        ? IXMapping.SkipMode.NONE : xMappingSet.getDefaultSkipMode();
    }
    else
    {
      skipMode = (childXMapping.getSkipMode() == null)
        ? IXMapping.SkipMode.NONE : childXMapping.getSkipMode();
    }
    return skipMode;
  }

  /**
   * {@inheritDoc}
   */
  public void navigate(boolean flag)
  {
    PsiElement psielement = getElement();
    if (psielement != null)
    {
      ((Navigatable) psielement).navigate(flag);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean canNavigate()
  {
    PsiElement psielement = getElement();
    return (psielement instanceof Navigatable) && ((Navigatable) psielement).canNavigate();
  }

  /**
   * {@inheritDoc}
   */
  public boolean canNavigateToSource()
  {
    return canNavigate();
  }

  /**
   * {@inheritDoc}
   */
  public String getPresentableText()
  {
    return (xmlTag == null) ? null : xmlTag.getName();
  }

  /**
   * {@inheritDoc}
   */
  public String getLocationString()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Icon getIcon(boolean open)
  {
    if ((xMapping != null) && (xMapping.getIcon() != null))
    {
      return xMapping.getIcon();
    }

    PsiElement psielement = getElement();
    return psielement != null ? psielement.getIcon(Iconable.ICON_FLAG_READ_STATUS) : null;
  }

  /**
   * {@inheritDoc}
   */
  public TextAttributesKey getTextAttributesKey()
  {
    return null;
  }

  /**
   * Returns tag name
   *
   * @return tag name
   */
  public String getTagName()
  {
    return (xmlTag == null) ? null : xmlTag.getName();
  }

  /**
   * Return target formatted label
   *
   * @return target formatted label
   */
  public String getTargetLabel()
  {
    if (xMapping == null)
    {
      return null;
    }

    String label = getTargetText(xMapping.getLabelExp());
    int maxLength = xMapping.getMaxLength();
    if ((maxLength > 0) && (maxLength < label.length()))
    {
      label = label.substring(0, maxLength) + "[...]";
    }
    
    return label;
  }

  /**
   * Returns target formatted tooltip
   *
   * @return target formatted tooltip
   */
  public String getTargetTooltip()
  {
    return (xMapping == null) ? null : getTargetText(xMapping.getTooltipExp());
  }

  /**
   * Returns target formatted text
   *
   * @param mappingExp a mapping expression (label/tooltip)
   *
   * @return target formatted text
   */
  private String getTargetText(IXMappingExp mappingExp)
  {
    if ((xmlTag == null) || (mappingExp == null) || (xMappingSet == null))
    {
      return null;
    }

    try
    {
      String text = xMappingSet.getMappingResolver().resolveTargetText(xmlTag, mappingExp);
      String tip = text.trim();

      return (tip.length() == 0) ? null : tip;
    }
    catch (XMappingException e)
    {
      LOGGER.warn("Invalid mapping, remove it", e);
      xMappingSet.removeMapping(mappingExp.getXMapping());
      return (xmlTag == null) ? null : xmlTag.getName();
    }
  }
}
