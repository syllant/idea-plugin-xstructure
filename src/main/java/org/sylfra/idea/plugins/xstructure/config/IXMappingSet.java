package org.sylfra.idea.plugins.xstructure.config;

import com.intellij.openapi.vfs.VirtualFile;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;

import java.util.List;

/**
 * A mapping set is the object representation for a mapping definition file
 * The implementation may be different according the implementation type (default, XPath, ...)
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: IXMappingSet.java 31 2007-12-23 11:23:10Z syllant $
 */
public interface IXMappingSet
{
  /**
   * Returns the mapping set name
   *
   * @return the mapping set name
   */
  String getName();

  /**
   * Set the mapping set name
   *
   * @param name the mapping set name
   */
  void setName(String name);

  /**
   * Returns the mapping set version
   *
   * @return the mapping set version
   */
  String getVersion();

  /**
   * Sets the mapping set version
   *
   * @param version the mapping set version
   */
  void setVersion(String version);

  /**
   * Returns the mapping set priority
   *
   * @return the mapping set priority
   */
  int getPriority();

  /**
   * Sets the mapping set priority
   *
   * @param priority the mapping set priority
   */
  void setPriority(int priority);

  /**
   * Returns the file associated to this mapping set
   *
   * @return the file associated to this mapping set
   */
  VirtualFile getFile();

  /**
   * Sets the file associated to this mapping set
   *
   * @param file the file associated to this mapping set
   */
  void setFile(VirtualFile file);

  /**
   * Returns the mapping resolver associated to this mapping set
   *
   * @return the mapping resolver associated to this mapping set
   */
  IXMappingResolver getMappingResolver();

  /**
   * Sets the mapping resolver associated to this mapping set
   *
   * @param mappingResolver the mapping resolver associated to this mapping set
   */
  void setMappingResolver(IXMappingResolver mappingResolver);

  /**
   * Returns all defined mappings
   *
   * @return all defined mappings
   */
  List<IXMapping> getMappings();

  /**
   * Sets the mappings
   *
   * @param mappings the mappings
   */
  void setMappings(List<IXMapping> mappings);

  /**
   * Removes a mapping from mapping set
   *
   * @param mapping the mapping
   */
  void removeMapping(IXMapping mapping);

  /**
   * Returns the list of schema supported by this mapping set
   *
   * @return the list of schema supported by this mapping set
   */
  List<Schema> getSupportedSchemas();

  /**
   * Sets the list of schema supported by this mapping set
   *
   * @param supportedSchemas the list of schema supported by this mapping set
   */
  void setSupportedSchemas(List<Schema> supportedSchemas);

  /**
   * Returns the default skip mode for this mapping set
   *
   * @return the default skip mode for this mapping set
   */
  IXMapping.SkipMode getDefaultSkipMode();

  /**
   * Sets the default skip mode for this mapping set
   *
   * @param skipMode the default skip mode for this mapping set
   */
  void setDefaultSkipMode(IXMapping.SkipMode skipMode);
}
