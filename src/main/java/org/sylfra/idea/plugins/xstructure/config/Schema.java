package org.sylfra.idea.plugins.xstructure.config;

/**
 * A XML model definition.
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class Schema
{
  private String uri;

  /**
   * Returns the schema URI
   *
   * @return the schema URI
   */
  public String getUri()
  {
    return uri;
  }

  /**
   * Sets the schema URI
   *
   * @param uri the schema URI
   */
  public void setUri(String uri)
  {
    this.uri = uri;
  }
}
