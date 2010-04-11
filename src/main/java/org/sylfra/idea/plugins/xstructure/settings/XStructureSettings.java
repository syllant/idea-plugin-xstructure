package org.sylfra.idea.plugins.xstructure.settings;

/**
 * General settings bean for plugin
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XStructureSettings
{
  private String mappingsStorageDir;
  private boolean syncMappingsAtStartup;
  private boolean overwriteWhenSyncMappings;

  /**
   * Returns the mappings storage dir
   *
   * @return the mappings storage dir
   */
  public String getMappingsStorageDir()
  {
    return mappingsStorageDir;
  }

  /**
   * Sets the mappings storage dir
   *
   * @param mappingsStorageDir the mappings storage dir
   */
  public void setMappingsStorageDir(String mappingsStorageDir)
  {
    this.mappingsStorageDir = mappingsStorageDir;
  }

  /**
   * Returns true if mappings are synchronized at startup, false otherwise
   *
   * @return true if mappings are synchronized at startup, false otherwise
   */
  public boolean isSyncMappingsAtStartup()
  {
    return syncMappingsAtStartup;
  }

  /**
   * Set true if mappings should be synchronized at startup, false otherwise
   *
   * @param syncMappingsAtStartup true if mappings should be synchronized at startup, false otherwise
   */
  public void setSyncMappingsAtStartup(boolean syncMappingsAtStartup)
  {
    this.syncMappingsAtStartup = syncMappingsAtStartup;
  }

  /**
   * Returns true if mappings are overriden when synchronized, false otherwise
   *
   * @return true if mappings are overriden when synchronized, false otherwise
   */
  public boolean isOverwriteWhenSyncMappings()
  {
    return overwriteWhenSyncMappings;
  }

  /**
   * Sets true if mappings should be overriden when synchronized, false otherwise
   *
   * @param overwriteWhenSyncMappings true if mappings should be overriden when synchronized, false otherwise
   */
  public void setOverwriteWhenSyncMappings(boolean overwriteWhenSyncMappings)
  {
    this.overwriteWhenSyncMappings = overwriteWhenSyncMappings;
  }
}
