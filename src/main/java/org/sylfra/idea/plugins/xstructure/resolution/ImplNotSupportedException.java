package org.sylfra.idea.plugins.xstructure.resolution;

/**
 * When a implementation type is not supported
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class ImplNotSupportedException extends Exception
{
  public ImplNotSupportedException(String message)
  {
    super(message);
  }
}