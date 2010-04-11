package org.sylfra.idea.plugins.xstructure.resolution;

/**
 * For any error related to mapping resolution
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XMappingException extends Exception
{
  public XMappingException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public XMappingException(String message)
  {
    super(message);
  }
}
