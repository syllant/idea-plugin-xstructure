package org.sylfra.idea.plugins.xstructure.config.impl.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.sylfra.idea.plugins.xstructure.config.Schema;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * XStream converter for a XMapping definition
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
class SchemaConverter implements Converter
{
  private static final Logger LOGGER = Logger.getInstance(SchemaConverter.class.getName());

  /**
   * {@inheritDoc}
   */
  public boolean canConvert(Class type)
  {
    return type.equals(Schema.class);
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
    Schema schema = new Schema();

    String uri = reader.getAttribute("uri");

    try
    {
      schema.setUriPattern(Pattern.compile(uri));
    }
    catch (PatternSyntaxException e)
    {
      LOGGER.warn("Invalid pattern for schema URI:" + uri, e);
    }

    return schema;
  }
}