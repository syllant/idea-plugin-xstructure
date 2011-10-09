package org.sylfra.idea.plugins.xstructure.resolution;

/**
 * When a implementation type is not supported
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: ImplNotSupportedException.java 31 2007-12-23 11:23:10Z syllant $
 */
public class ImplNotSupportedException extends Exception
{
  public ImplNotSupportedException(String message)
  {
    super(message);
  }
}