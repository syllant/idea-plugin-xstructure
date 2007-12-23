package org.sylfra.idea.plugins.xstructure;

/**
 * General exception related to XStructure error
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XSException extends Exception
{
  public XSException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * {@inheritDoc}
   */
  public XSException(String message)
  {
    super(message);
  }
}
