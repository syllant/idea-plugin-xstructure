package org.sylfra.idea.plugins.xstructure.resolution.impl.defaultt;

import org.sylfra.idea.plugins.xstructure.config.AbstractXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;

import java.util.regex.Pattern;

/**
 * Implementation of mapping for 'default' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XMappingDefaultImpl extends AbstractXMapping
{
  private Pattern matchPattern;

  /**
   * Returns match value as regexp pattern
   *
   * @return match value as regexp pattern
   */
  public Pattern getMatchPattern()
  {
    return matchPattern;
  }

  /**
   * Sets regexp pattern for match value
   *
   * @param matchPattern regexp pattern for match value
   */
  public void setMatchPattern(Pattern matchPattern)
  {
    this.matchPattern = matchPattern;
  }

  /**
   * {@inheritDoc}
   */
  protected IXMappingExp createXMappingExp(IXMapping xMapping, String rawExp)
  {
    return new XMappingExpDefaultImpl(xMapping, rawExp);
  }
}