package org.sylfra.idea.plugins.xstructure.config.impl.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingException;

/**
 * XStream converter for a XMapping definition
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
class XMappingConverter implements Converter
{
  private static final Logger LOGGER = Logger.getInstance(XMappingConverter.class.getName());

  /**
   * {@inheritDoc}
   */
  public boolean canConvert(Class type)
  {
    return type.equals(IXMapping.class);
  }

  /**
   * {@inheritDoc}
   */
  public void marshal(Object value, HierarchicalStreamWriter writer,
    MarshallingContext context)
  {
    throw new ConversionException("XMapping marshalling is not implemented");
  }

  /**
   * {@inheritDoc}
   */
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    IXMappingResolver xMappingResolver = (IXMappingResolver) context.get("xMappingResolver");
    IXMappingSet xMappingSet = (IXMappingSet) context.get("xMappingSet");
    IXMapping xMapping = xMappingResolver.createMapping();

    xMapping.setMappingSet(xMappingSet);

    xMapping.initLabelExp(reader.getAttribute("label"));
    xMapping.initTooltipExp(reader.getAttribute("tip"));
    xMapping.setMatchString(reader.getAttribute("match"));

    String skipModeValue = reader.getAttribute("skip");
    xMapping.setSkipMode(XMappingFactoryXmlImpl.readSkipMode(skipModeValue));

    try
    {
      xMappingResolver.validateMapping(xMapping);
    }
    catch (XMappingException e)
    {
      LOGGER.warn("Invalid mapping", e);
      return null;
    }

    return xMapping;
  }
}