package org.sylfra.idea.plugins.xstructure.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.Serializable;

/**
 * A unit mapping belonging to a mapping set
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public interface IXMapping extends Serializable
{
  /**
   * Available values for skip mode
   */
  public static enum SkipMode
  {
    NONE, THIS, CHILDREN, ALL
  }

  /**
   * Returns the mapping set containing this mapping
   *
   * @return the mapping set containing this mapping
   */
  @NotNull
  IXMappingSet getMappingSet();

  /**
   * Sets the mapping set containing this mapping
   *
   * @param mappingSet the mapping set containing this mapping
   */
  void setMappingSet(@NotNull IXMappingSet mappingSet);

  /**
   * Returns the match string
   *
   * @return the match string
   */
  @NotNull
  String getMatchString();

  /**
   * Sets the match string
   *
   * @param match the match string
   */
  void setMatchString(@NotNull String match);

  /**
   * Returns the label raw expression
   *
   * @return the label raw expression
   */
  @Nullable
  IXMappingExp getLabelExp();

  /**
   * Returns the tooltip raw expression
   *
   * @return the tooltip raw expression
   */
  @Nullable
  IXMappingExp getTooltipExp();

  /**
   * Returns the skip mode
   *
   * @return the skip mode
   */
  @Nullable
  SkipMode getSkipMode();

  /**
   * Sets the skip mode
   *
   * @param skipMode the skip mode
   */
  void setSkipMode(@Nullable SkipMode skipMode);

  /**
   * Initialize raw expression for label
   *
   * @param rawExp raw expression for label
   */
  void initLabelExp(@Nullable String rawExp);

  /**
   * Initialize raw expression for tooltip
   *
   * @param rawExp raw expression for tooltip
   */
  void initTooltipExp(@Nullable String rawExp);

  /**
   * Returns the icon
   *
   * @return the icon
   */
  @Nullable
  Icon getIcon();

  /**
   * Sets the icon
   *
   * @param icon the icon
   */
  void setIcon(@Nullable Icon icon);
}
