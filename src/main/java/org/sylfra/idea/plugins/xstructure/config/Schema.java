package org.sylfra.idea.plugins.xstructure.config;

import java.util.regex.Pattern;

/**
 * A XML model definition.
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: Schema.java 34 2009-03-22 19:49:45Z syllant $
 */
public class Schema
{
  private Pattern uriPattern;

  /**
   * Returns the schema URI
   *
   * @return the schema URI
   */
  public Pattern getUriPattern()
  {
    return uriPattern;
  }

  /**
   * Sets the schema URI
   *
   * @param uriPattern the schema URI
   */
  public void setUriPattern(Pattern uriPattern)
  {
    this.uriPattern = uriPattern;
  }
}
