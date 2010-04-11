package org.sylfra.idea.plugins.xstructure.resolution.impl.xpath;

import org.sylfra.idea.plugins.xstructure.config.AbstractXMappingSet;

/**
 * Implementation of mapping set for 'xpath' implementation type
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XMappingSetXPathImpl extends AbstractXMappingSet
{
  public XMappingSetXPathImpl(XMappingResolverXPathImpl xMappingResolver)
  {
    setMappingResolver(xMappingResolver);
  }
}