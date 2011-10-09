package org.sylfra.idea.plugins.xstructure.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Factory in charge of loading mapping sets
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: IXMappingFactory.java 41 2009-03-27 21:15:38Z syllant $
 */
public interface IXMappingFactory<T extends IXMappingSet>
{
  /**
   * Loads all mapping sets
   *
   * @return the mapping set list
   */
  @NotNull
  List<T> loadAll();

  /**
   * Reload one mapping set. Useful when user updates a mapping set from IDEA
   *
   * @param xMappingSet the mapping set to remove
   *
   * @return the new mapping set
   */
  @Nullable
  T reload(@NotNull T xMappingSet);
}
