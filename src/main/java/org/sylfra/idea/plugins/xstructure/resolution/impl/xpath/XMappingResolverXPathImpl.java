package org.sylfra.idea.plugins.xstructure.resolution.impl.xpath;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.intellij.plugins.xpathView.support.jaxen.PsiXPath;
import org.jaxen.JaxenException;
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
 * Implementation of resolver for 'xpath' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingResolverXPathImpl.java 31 2007-12-23 11:23:10Z syllant $
 */
public class XMappingResolverXPathImpl implements IXMappingResolver, ApplicationComponent
{
  private static final String TYPE = "xpath";

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
    return new XMappingSetXPathImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMapping createMapping()
  {
    return new XMappingXPathImpl();
  }

  /**
   * {@inheritDoc}
   */
  public void validateMapping(@NotNull IXMapping xMapping) throws XMappingException
  {
    XMappingXPathImpl xpathXMapping = (XMappingXPathImpl) xMapping;
    try
    {
      xpathXMapping.setMatchPattern(Pattern.compile(xMapping.getMatchString()));
    }
    catch (Exception e)
    {
      throw new XMappingException("Failed to compile regexp : " + xMapping.getMatchString());
    }
  }

  /**
   * {@inheritDoc}
   */
  public void checkSupported() throws ImplNotSupportedException
  {
    IdeaPluginDescriptor xPathViewPlugin = PluginManager.getPlugin(PluginId.getId("XPathView"));
    if (xPathViewPlugin == null)
    {
      throw new ImplNotSupportedException("XPathView plugin is required");
    }
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public String resolveTargetText(@NotNull XmlTag xmlTag, @NotNull IXMappingExp mappingExp)
    throws XMappingException
  {
    PsiXPath xPath;
    try
    {
      xPath = new PsiXPath((XmlFile) xmlTag.getContainingFile(), mappingExp.getRawExp());
    }
    catch (JaxenException e)
    {
      throw new XMappingException("Error while parsing resolution expression : <" +
        mappingExp + ">", e);
    }

    try
    {
      return xPath.stringValueOf(xmlTag);
    }
    catch (JaxenException e)
    {
      throw new XMappingException("Error while evaluating value. Mapping expression : <"
        + mappingExp + ">. Xml content : <" + xmlTag.getText() + ">", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public IXMapping findMatchingMapping(@NotNull IXMappingSet xMappingSet, @NotNull XmlTag xmlTag)
  {
    String path = buildXmlTagPath(xmlTag);
    for (IXMapping xMapping : xMappingSet.getMappings())
    {
      XMappingXPathImpl xpathXMapping = (XMappingXPathImpl) xMapping;

      if (xpathXMapping.getMatchPattern().matcher(path).matches())
      {
        return xMapping;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".XMappingResolverXPathImpl";
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
   * Build a path from parent hierarchy
   *
   * @param xmlTag the request XML tag
   *
   * @return a path which identifies this tag
   */
  private String buildXmlTagPath(XmlTag xmlTag)
  {
    StringBuffer result = new StringBuffer();

    while (xmlTag != null)
    {
      result.insert(0, xmlTag.getName());
      result.insert(0, '/');

      xmlTag = xmlTag.getParentTag();
    }
    return result.toString();
  }
}
