package org.sylfra.idea.plugins.xstructure.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public abstract class AbstractXMapping implements IXMapping
{
  private IXMappingSet mappingSet;
  private String matchString;
  private int maxLength;
  private IXMappingExp labelExp;
  private IXMappingExp tooltipExp;
  private SkipMode skipMode;
  private Icon icon;

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMappingSet getMappingSet()
  {
    return mappingSet;
  }

  /**
   * {@inheritDoc}
   */
  public void setMappingSet(@NotNull IXMappingSet mappingSet)
  {
    this.mappingSet = mappingSet;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public String getMatchString()
  {
    return matchString;
  }

  /**
   * {@inheritDoc}
   */
  public void setMatchString(@NotNull String matchString)
  {
    this.matchString = matchString;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaxLength()
  {
    return maxLength;
  }

  /**
   * {@inheritDoc}
   */
  public void setMaxLength(int maxLength)
  {
    this.maxLength = maxLength;
  }

  /**
   * {@inheritDoc}
   */
  public IXMappingExp getLabelExp()
  {
    return labelExp;
  }

  /**
   * {@inheritDoc}
   */
  public IXMappingExp getTooltipExp()
  {
    return tooltipExp;
  }

  /**
   * {@inheritDoc}
   */
  public SkipMode getSkipMode()
  {
    return skipMode;
  }

  /**
   * {@inheritDoc}
   */
  public void setSkipMode(@NotNull SkipMode skipMode)
  {
    this.skipMode = skipMode;
  }

  /**
   * {@inheritDoc}
   */
  public void initLabelExp(@Nullable String rawExp)
  {
    if (rawExp != null)
    {
      labelExp = createXMappingExp(this, rawExp);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void initTooltipExp(@Nullable String rawExp)
  {
    if (rawExp != null)
    {
      tooltipExp = createXMappingExp(this, rawExp);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  public Icon getIcon()
  {
    return icon;
  }

  /**
   * {@inheritDoc}
   */
  public void setIcon(@Nullable Icon icon)
  {
    this.icon = icon;
  }

  /**
   * Creates a new expression from raw expression
   *
   * @param xMapping the current mapping
   * @param rawExp   the raw value
   *
   * @return a expression depending on the implementation type
   */
  protected abstract IXMappingExp createXMappingExp(IXMapping xMapping, String rawExp);
}
