package org.sylfra.idea.plugins.xstructure.config;

import org.jetbrains.annotations.NotNull;

/**
 * An expression defined in a mapping which has to be resolved, e.g. : label or tip
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: IXMappingExp.java 31 2007-12-23 11:23:10Z syllant $
 */
public interface IXMappingExp
{
  /**
   * Returns the mapping in which this expression is defined
   *
   * @return the mapping in which this expression is defined
   */
  @NotNull
  IXMapping getXMapping();

  /**
   * Sets the mapping in which this expression is defined
   *
   * @param xMapping the mapping in which this expression is defined
   */
  void setXMapping(@NotNull IXMapping xMapping);

  /**
   * Returns the raw expression, as it was written
   *
   * @return the raw expression
   */
  @NotNull
  String getRawExp();

  /**
   * Sets the raw expression
   *
   * @param rawExp the raw expression
   */
  void setRawExp(@NotNull String rawExp);
}