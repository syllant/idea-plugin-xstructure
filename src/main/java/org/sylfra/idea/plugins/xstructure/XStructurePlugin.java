package org.sylfra.idea.plugins.xstructure;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.registry.XMappingSetRegistry;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingResolverFactory;
import org.sylfra.idea.plugins.xstructure.settings.XStructureSettingsComponent;

/**
 * The main application component available as a singleton and providing convenient methods
 * to access services declared in plugin.xml
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XStructurePlugin implements ApplicationComponent
{
  public static final String COMPONENT_NAME = "xStructure";
  public static final String PLUGIN_ID = "org.sylfra.idea.plugins.xstructure";

  private static final Logger LOGGER =
    Logger.getInstance(XStructurePlugin.class.getName());

  private XMappingResolverFactory xMappingResolverFactory;

  public static XStructurePlugin getInstance()
  {
    return ApplicationManager.getApplication().getComponent(XStructurePlugin.class);
  }

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return COMPONENT_NAME;
  }

  /**
   * {@inheritDoc}
   */
  public void initComponent()
  {
    registerXStructureSchemas();

    // Load configuration
    try
    {
      getSettingsComponent().installDefautMappings();
    }
    catch (XSException e)
    {
      LOGGER.warn(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void disposeComponent()
  {
  }

  private void registerXStructureSchemas()
  {
    ExternalResourceManager.getInstance().addStdResource(
      "http://plugins.intellij.net/xstructure/ns/xstructure_1_0.xsd",
      "/org/sylfra/idea/plugins/xstructure/resources/schemas/xstructure_1_0.xsd",
      XStructurePlugin.class);
  }

  /**
   * Provides mapping set registry component
   *
   * @return mapping set registry component
   */
  public XMappingSetRegistry getXMappingSetRegistry()
  {
    return ApplicationManager.getApplication().getComponent(XMappingSetRegistry.class);
  }

  /**
   * Provides mapping resolver factory
   *
   * @return mapping resolver factory
   */
  public XMappingResolverFactory getXMappingResolverFactory()
  {
    if (xMappingResolverFactory == null)
    {
      xMappingResolverFactory = new XMappingResolverFactory();
    }
    return xMappingResolverFactory;
  }

  /**
   * Provides settings component
   *
   * @return settings component
   */
  public XStructureSettingsComponent getSettingsComponent()
  {
    return ServiceManager.getService(XStructureSettingsComponent.class);
  }
}
