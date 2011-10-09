package org.sylfra.idea.plugins.xstructure.resolution;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Factory which provides mapping resolvers
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingResolverFactory.java 31 2007-12-23 11:23:10Z syllant $
 */
public class XMappingResolverFactory implements ApplicationComponent
{
  private Map<String, IXMappingResolver> mappingResolvers;

  public XMappingResolverFactory()
  {
    mappingResolvers = new HashMap<String, IXMappingResolver>();
  }

  /**
   * Registers a mapping resolver
   *
   * @param mappingResolver a mapping resolver
   */
  public void register(IXMappingResolver mappingResolver)
  {
    mappingResolvers.put(mappingResolver.getType(), mappingResolver);
  }

  /**
   * Provides the resolver instance for the specified implementation type
   *
   * @param type the implementation type
   *
   * @return a mapping resolver
   */
  public IXMappingResolver getMappingResolver(String type)
  {
    return mappingResolvers.get(type);
  }

  /**
   * Returns all registered implementation types
   *
   * @return all registered implementation types
   */
  public Set<String> getRegisterTypes()
  {
    return mappingResolvers.keySet();
  }

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".XMappingResolverFactory";
  }

  /**
   * {@inheritDoc}
   */
  public void initComponent()
  {
  }

  /**
   * {@inheritDoc}
   */
  public void disposeComponent()
  {
  }
}