package org.sylfra.idea.plugins.xstructure.resolution.impl.defaultt;

import org.sylfra.idea.plugins.xstructure.config.AbstractXMappingSet;

/**
 * Implementation of mapping set for 'default' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingSetDefaultImpl.java 31 2007-12-23 11:23:10Z syllant $
 */
public class XMappingSetDefaultImpl extends AbstractXMappingSet
{
  public XMappingSetDefaultImpl(XMappingResolverDefaultImpl xMappingResolver)
  {
    setMappingResolver(xMappingResolver);
  }
}
