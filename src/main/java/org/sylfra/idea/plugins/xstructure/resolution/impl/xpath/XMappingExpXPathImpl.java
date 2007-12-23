package org.sylfra.idea.plugins.xstructure.resolution.impl.xpath;

import org.jetbrains.annotations.NotNull;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;

/**
 * Implementation of raw expression for 'xpath' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XMappingExpXPathImpl implements IXMappingExp
{
  private String rawExp;
  private IXMapping xMapping;

  public XMappingExpXPathImpl(IXMapping xMapping, String rawExp)
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