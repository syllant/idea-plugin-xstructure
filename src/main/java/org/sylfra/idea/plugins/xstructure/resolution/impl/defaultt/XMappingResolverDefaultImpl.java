package org.sylfra.idea.plugins.xstructure.resolution.impl.defaultt;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;
import org.sylfra.idea.plugins.xstructure.resolution.ImplNotSupportedException;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingException;

import java.util.regex.Pattern;

/**
 * Implementation of resolver for 'default' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingResolverDefaultImpl.java 75 2011-09-03 08:42:20Z syllant $
 */
public class XMappingResolverDefaultImpl implements IXMappingResolver, ApplicationComponent
{
  private static final String TYPE = "default";

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".XMappingResolverDefaultImpl";
  }

  /**
   * {@inheritDoc}
   */
  public void initComponent()
  {
    XStructurePlugin.getInstance().getXMappingResolverFactory().register(this);
  }

  /**
   * {@inheritDoc}
   */
  public void disposeComponent()
  {
  }

  /**
   * {@inheritDoc}
   */
  public String getType()
  {
    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMappingSet createMappingSet()
  {
    return new XMappingSetDefaultImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMapping createMapping()
  {
    return new XMappingDefaultImpl();
  }

  /**
   * {@inheritDoc}
   */
  public void validateMapping(@NotNull IXMapping xMapping) throws XMappingException
  {
    XMappingDefaultImpl regexpXMapping = (XMappingDefaultImpl) xMapping;
    Pattern pattern;
    try
    {
      pattern = Pattern.compile(xMapping.getMatchString());
    }
    catch (Exception e)
    {
      throw new XMappingException("Failed to compile regexp : " + xMapping.getMatchString());
    }
    regexpXMapping.setMatchPattern(pattern);
  }

  /**
   * {@inheritDoc}
   */
  public void checkSupported() throws ImplNotSupportedException
  {
    // Always supported
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public String resolveTargetText(@NotNull XmlTag xmlTag, @NotNull IXMappingExp mappingExp)
    throws XMappingException
  {
    StringBuilder result = new StringBuilder();

    XMappingExpDefaultImpl regexpMappingExp = (XMappingExpDefaultImpl) mappingExp;
    String rawExp = regexpMappingExp.getRawExp();

    // Parse multi exp : xxx{PATH}xxx{PATH}...
    int startGroup;
    int endGroup;

    while (rawExp.length() > 0)
    {
      startGroup = rawExp.indexOf('{');

      // Raw exp ends with "{.". These chars are interpeted as raw text
      if ((startGroup == -1) || (startGroup >= rawExp.length() - 2))
      {
        result.append(rawExp);
        break;
      }

      // Previous raw text
      if (startGroup > 0)
      {
        result.append(rawExp.substring(0, startGroup));
        rawExp = rawExp.substring(startGroup);
      }

      // Localize next group
      endGroup = rawExp.indexOf('}');
      if (endGroup == -1)
      {
        result.append(rawExp);
        break;
      }

      result.append(internalResolveSingleExp(xmlTag, rawExp.substring(1, endGroup)));

      rawExp = (endGroup == rawExp.length() - 1) ? "" : rawExp.substring(endGroup + 1);
    }

    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  public IXMapping findMatchingMapping(@NotNull IXMappingSet xMappingSet, @NotNull XmlTag xmlTag)
  {
    String path = buildXmlTagPath(xmlTag);
    for (IXMapping xMapping : xMappingSet.getMappings())
    {
      XMappingDefaultImpl regexpXMapping = (XMappingDefaultImpl) xMapping;

      // Matcher may be null when pattern string is invalid
      Pattern pattern = regexpXMapping.getMatchPattern();
      if ((pattern != null) && (pattern.matcher(path).matches()))
      {
        return xMapping;
      }
    }
    return null;
  }

  private String internalResolveSingleExp(XmlTag xmlTag, String rawExp)
    throws XMappingException
  {
    if (rawExp.startsWith("/"))
    {
      xmlTag = ((XmlFile) xmlTag.getContainingFile()).getDocument().getRootTag();
      rawExp = rawExp.substring(1);
    }

    return internalResolveSingleExpPart(xmlTag, rawExp);
  }

  private String internalResolveSingleExpPart(XmlTag xmlTag, String mappingExp)
    throws XMappingException
  {
    int startNode = mappingExp.indexOf('/');
    int startAtt = mappingExp.indexOf('@');

    if ((startAtt > -1) && (startAtt < startNode))
    {
      throw new XMappingException("Bad expression, attribute may only be declared on last node : "
        + mappingExp);
    }

    // Related to this tag
    // Text or attribute ?
    if (startNode == -1)
    {
      if (startAtt == 0)
      {
        mappingExp = mappingExp.substring(1);
        XmlAttribute attribute = xmlTag.getAttribute(mappingExp);
        if (attribute == null)
        {
          return "";
        }

        return attribute.getValue();
      }

      if (startAtt > 0)
      {
        throw new XMappingException("Bad expression, attribute should be declared as @att : "
          + mappingExp);
      }
    }

    // This tag text ?
    if ((".".equals(mappingExp)) || ("".equals(mappingExp)))
    {
      return xmlTag.getValue().getText();
    }

    // Loooking for matching child
    String nextTagName = (startNode == -1) ? mappingExp : mappingExp.substring(0, startNode);
    for (XmlTag childTag : xmlTag.getSubTags())
    {
      if (mappingExp.equals(childTag.getName()))
      {
        return childTag.getValue().getText();
      }
      if (nextTagName.equals(childTag.getName()))
      {
        return internalResolveSingleExpPart(childTag, mappingExp.substring(startNode + 1));
      }
    }

    return "";
  }

  /**
   * Build a path from parent hierarchy
   *
   * @param xmlTag the request XML tag
   *
   * @return a path which identifies this tag
   */
  private String buildXmlTagPath(XmlTag xmlTag)
  {
    StringBuilder result = new StringBuilder();

    while (xmlTag != null)
    {
      result.insert(0, xmlTag.getName());
      result.insert(0, '/');

      xmlTag = xmlTag.getParentTag();
    }
    return result.toString();
  }
}