package org.sylfra.idea.plugins.xstructure.config;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;

import java.util.List;

/**
 * Base implementation for a mapping set
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public abstract class AbstractXMappingSet implements IXMappingSet
{
  private String name;
  private String version;
  private int priority;
  private VirtualFile file;
  private IXMapping.SkipMode defaultSkipMode;
  private IXMappingResolver mappingResolver;
  private List<IXMapping> mappings;
  private List<Schema> supportedSchemas;

  protected AbstractXMappingSet()
  {
    defaultSkipMode = IXMapping.SkipMode.NONE;
    priority = Integer.MAX_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public String getName()
  {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  public void setName(@NotNull String name)
  {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  public String getVersion()
  {
    return version;
  }

  /**
   * {@inheritDoc}
   */
  public void setVersion(@Nullable String version)
  {
    this.version = version;
  }

  /**
   * {@inheritDoc}
   */
  public int getPriority()
  {
    return priority;
  }

  /**
   * {@inheritDoc}
   */
  public void setPriority(int priority)
  {
    this.priority = priority;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public VirtualFile getFile()
  {
    return file;
  }

  /**
   * {@inheritDoc}
   */
  public void setFile(@NotNull VirtualFile file)
  {
    this.file = file;
  }

  /**
   * {@inheritDoc}
   */
  public IXMapping.SkipMode getDefaultSkipMode()
  {
    return defaultSkipMode;
  }

  /**
   * {@inheritDoc}
   */
  public void setDefaultSkipMode(IXMapping.SkipMode defaultSkipMode)
  {
    this.defaultSkipMode = defaultSkipMode;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMappingResolver getMappingResolver()
  {
    return mappingResolver;
  }

  /**
   * {@inheritDoc}
   */
  public void setMappingResolver(@NotNull IXMappingResolver mappingResolver)
  {
    this.mappingResolver = mappingResolver;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public List<IXMapping> getMappings()
  {
    return mappings;
  }

  /**
   * {@inheritDoc}
   */
  public void setMappings(@NotNull List<IXMapping> mappings)
  {
    this.mappings = mappings;
  }

  /**
   * {@inheritDoc}
   */
  public void removeMapping(@NotNull IXMapping mapping)
  {
    mappings.remove(mapping);
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public List<Schema> getSupportedSchemas()
  {
    return supportedSchemas;
  }

  /**
   * {@inheritDoc}
   */
  public void setSupportedSchemas(@NotNull List<Schema> supportedSchemas)
  {
    this.supportedSchemas = supportedSchemas;
  }

  /**
   * {@inheritDoc}
   */
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof AbstractXMappingSet))
    {
      return false;
    }

    AbstractXMappingSet that = (AbstractXMappingSet) o;

    if (priority != that.priority)
    {
      return false;
    }
    if (defaultSkipMode != that.defaultSkipMode)
    {
      return false;
    }
    if (!file.equals(that.file))
    {
      return false;
    }
    if (!mappingResolver.equals(that.mappingResolver))
    {
      return false;
    }
    if (mappings != null ? !mappings.equals(that.mappings) : that.mappings != null)
    {
      return false;
    }
    if (!name.equals(that.name))
    {
      return false;
    }
    if (supportedSchemas != null ? !supportedSchemas.equals(that.supportedSchemas) :
      that.supportedSchemas != null)
    {
      return false;
    }
    if (!version.equals(that.version))
    {
      return false;
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public int hashCode()
  {
    int result;
    result = name.hashCode();
    result = 31 * result + version.hashCode();
    result = 31 * result + priority;
    result = 31 * result + file.hashCode();
    result = 31 * result + defaultSkipMode.hashCode();
    result = 31 * result + mappingResolver.hashCode();
    result = 31 * result + (mappings != null ? mappings.hashCode() : 0);
    result = 31 * result + (supportedSchemas != null ? supportedSchemas.hashCode() : 0);
    return result;
  }
}