package org.sylfra.idea.plugins.xstructure.settings;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Registry for default mappings bundled in plugin
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public abstract class DefaultMappingsRegistry
{
  // A file filter for mapping definition files
  private final static FilenameFilter MAPPING_FILENAME_FILTER = new FilenameFilter()
  {
    public boolean accept(File dir, String name)
    {
      return name.endsWith(".xml");
    }
  };

  /**
   * Returns all default mapping definition files
   *
   * @return all default mapping definition files
   */
  @NotNull
  public static File[] getDefaultMappings()
  {
    File pluginPath =
      PluginManager.getPlugin(PluginId.getId(XStructurePlugin.PLUGIN_ID)).getPath();
    return new File(pluginPath, "default-mappings").listFiles(MAPPING_FILENAME_FILTER);
  }
}
