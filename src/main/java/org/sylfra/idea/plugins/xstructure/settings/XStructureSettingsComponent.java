package org.sylfra.idea.plugins.xstructure.settings;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.util.io.FileUtil;
import org.sylfra.idea.plugins.xstructure.XSException;

import java.io.File;
import java.io.IOException;

/**
 * Manage plugin settings
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
@State(
  name = "XStructureSettings",
  storages = {
  @Storage(
    id = "XStructureSettings",
    file = "$APP_CONFIG$/xStructure.xml"
  )}
)
public class XStructureSettingsComponent
  implements PersistentStateComponent<XStructureSettings>
{
  private XStructureSettings settings;

  public XStructureSettingsComponent()
  {
    settings = getDefaultSettings();
  }

  /**
   * Provided a settings bean with default values
   *
   * @return a settings bean with default values
   */
  public XStructureSettings getDefaultSettings()
  {
    XStructureSettings defaultSettings = new XStructureSettings();
    defaultSettings.setMappingsStorageDir(getDefaultConfigPath());
    defaultSettings.setSyncMappingsAtStartup(true);
    defaultSettings.setOverwriteWhenSyncMappings(true);

    return defaultSettings;
  }

  /**
   * {@inheritDoc}
   */
  public XStructureSettings getState()
  {
    return settings;
  }

  /**
   * {@inheritDoc}
   */
  public void loadState(XStructureSettings object)
  {
    settings = object;

    if (settings.getMappingsStorageDir() == null)
    {
      settings.setMappingsStorageDir(getDefaultConfigPath());
    }
  }

  /**
   * Install default mappings according to general settings
   *
   * @throws XSException if any error occurs
   */
  public void installDefautMappings() throws XSException
  {
    File configDir = new File(settings.getMappingsStorageDir());
    boolean freshInstall = (!configDir.exists());
    if (freshInstall)
    {
      if (!configDir.mkdir())
      {
        throw new XSException("Can't create directory to store mapping files : "
          + configDir.getAbsolutePath());
      }
    }

    if ((freshInstall) || (settings.isSyncMappingsAtStartup()))
    {
      File destDir = new File(settings.getMappingsStorageDir(), "default");
      if ((!destDir.isDirectory()) && (!destDir.mkdir()))
      {
        throw new XSException("Can't create directory to store DEFAULT mapping files : "
          + configDir.getAbsolutePath());
      }

      for (File file : DefaultMappingsRegistry.getDefaultMappings())
      {
        File destFile = new File(destDir, file.getName());
        if ((!destFile.exists()) || (settings.isOverwriteWhenSyncMappings()))
        {
          try
          {
            FileUtil.copy(file, destFile);
          }
          catch (IOException e)
          {
            throw new XSException("Can't copy configuration file to "
              + destFile.getAbsolutePath(), e);
          }
        }
      }
    }
  }

  private String getDefaultConfigPath()
  {
    return PathManager.getConfigPath() + File.separatorChar + "xstructure";
  }
}
