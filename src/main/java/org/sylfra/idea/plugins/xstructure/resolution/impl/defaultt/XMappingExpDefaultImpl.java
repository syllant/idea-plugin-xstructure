package org.sylfra.idea.plugins.xstructure.resolution.impl.defaultt;

import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;

/**
 * Implementation of raw expression for 'default' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XMappingExpDefaultImpl implements IXMappingExp
{
  private IXMapping xMapping;
  private String rawExp;

  public XMappingExpDefaultImpl(IXMapping xMapping, String rawExp)
  {
    this.xMapping = xMapping;
    this.rawExp = rawExp;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public IXMapping getXMapping()
  {
    return xMapping;
  }

  /**
   * {@inheritDoc}
   */
  public void setXMapping(@NotNull IXMapping xMapping)
  {
    this.xMapping = xMapping;
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  public String getRawExp()
  {
    return rawExp;
  }

  /**
   * {@inheritDoc}
   */
  public void setRawExp(@NotNull String rawExp)
  {
    this.rawExp = rawExp;
  }
}