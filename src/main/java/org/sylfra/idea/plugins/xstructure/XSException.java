package org.sylfra.idea.plugins.xstructure;

/**
 * General exception related to XStructure error
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XSException.java 31 2007-12-23 11:23:10Z syllant $
 */
public class XSException extends Exception
{
  public XSException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public XSException(String message)
  {
    super(message);
  }
}
