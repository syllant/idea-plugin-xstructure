package org.sylfra.idea.plugins.xstructure.settings;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XSIconManager;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;

import javax.swing.*;

/**
 * Used to interface settings inside Settings panel
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XStructureSettingsConfigurable implements ApplicationComponent, Configurable
{
  private XStructureSettingsPane settingsPane;

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".SettingsConfigurable";
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
    if (settingsPane != null)
    {
      settingsPane.dispose();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Nls
  public String getDisplayName()
  {
    return "xStructure";
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  public Icon getIcon()
  {
    return XSIconManager.getIcon(XSIconManager.IconRef.xstructureLarge);
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  @NonNls
  public String getHelpTopic()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public JComponent createComponent()
  {
    if (settingsPane == null)
    {
      settingsPane = new XStructureSettingsPane();
    }

    return settingsPane.getContentPane();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isModified()
  {
    return settingsPane
      .isModified(XStructurePlugin.getInstance().getSettingsComponent().getState());
  }

  /**
   * {@inheritDoc}
   */
  public void apply() throws ConfigurationException
  {
    XStructureSettings settings = XStructurePlugin.getInstance().getSettingsComponent().getState();
    settingsPane.getData(settings);
  }

  /**
   * {@inheritDoc}
   */
  public void reset()
  {
    settingsPane.setData(XStructurePlugin.getInstance().getSettingsComponent().getState());
  }

  /**
   * {@inheritDoc}
   */
  public void disposeUIResources()
  {
  }
}