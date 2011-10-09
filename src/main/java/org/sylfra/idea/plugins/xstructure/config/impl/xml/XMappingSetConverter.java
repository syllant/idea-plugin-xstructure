package org.sylfra.idea.plugins.xstructure.config.impl.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;
import org.sylfra.idea.plugins.xstructure.resolution.ImplNotSupportedException;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingResolverFactory;

import java.io.File;
import java.util.List;

/**
 * XStream converter for a whole mapping set definition
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingSetConverter.java 33 2009-03-22 17:43:10Z syllant $
 */
class XMappingSetConverter implements Converter
{
  private static final Logger LOGGER = Logger.getInstance(XMappingSetConverter.class.getName());

  private File srcFile;

  /**
   * Sets the file from which the mapping set is loaded
   *
   * @param srcFile the file from which the mapping set is loaded
   */
  public void setSrcFile(File srcFile)
  {
    this.srcFile = srcFile;
  }

  /**
   * {@inheritDoc}
   */
  public boolean canConvert(Class type)
  {
    return type.equals(IXMappingSet.class);
  }

  /**
   * {@inheritDoc}
   */
  public void marshal(Object value, HierarchicalStreamWriter writer,
    MarshallingContext context)
  {
    throw new ConversionException("XMappingSet marshalling is not implemented");
  }

  /**
   * {@inheritDoc}
   */
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    String implType = reader.getAttribute("implType");

    IXMappingResolver xMappingResolver = readXMappingResolver(implType);
    if (xMappingResolver == null)
    {
      return null;
    }

    try
    {
      xMappingResolver.checkSupported();
    }
    catch (ImplNotSupportedException e)
    {
      LOGGER.warn("Skip mapping definition file since implementation '" + implType +
        "' is not supported in your environment : " + e.getMessage());
      return null;
    }

    // Will be needed by XMappingConverter
    context.put("xMappingResolver", xMappingResolver);

    IXMappingSet xMappingSet = xMappingResolver.createMappingSet();
    xMappingSet.setName(reader.getAttribute("name"));
    xMappingSet.setFile(LocalFileSystem.getInstance().refreshAndFindFileByIoFile(srcFile));
    xMappingSet.setVersion(reader.getAttribute("version"));

    String priorityValue = reader.getAttribute("priority");
    if (priorityValue != null)
    {
      try
      {
        xMappingSet.setPriority(Integer.parseInt(priorityValue));
      }
      catch (NumberFormatException e)
      {
        LOGGER.warn("Priority value should be an integer : <" + priorityValue + ">, in "
          + srcFile.getName());
      }
    }

    context.put("xMappingSet", xMappingSet);

    while (reader.hasMoreChildren())
    {
      reader.moveDown();
      if ("supported-schemas".equals(reader.getNodeName()))
      {
        List supportedSchemas = (List) context.convertAnother(xMappingSet, List.class);
        xMappingSet.setSupportedSchemas(supportedSchemas);
      }
      else if ("mappings".equals(reader.getNodeName()))
      {
        String skipValue = reader.getAttribute("defaultSkip");
        xMappingSet.setDefaultSkipMode(XMappingFactoryXmlImpl.readSkipMode(skipValue));

        List<IXMapping> mappings =
          (List<IXMapping>) context.convertAnother(xMappingSet, List.class);
        xMappingSet.setMappings(mappings);
      }
      reader.moveUp();
    }
    return xMappingSet;
  }

  /**
   * Retrieve a Mapping resolver form the specified implementation type reference
   *
   * @param implType the implementation type reference
   *
   * @return the related mapping resolver implementation
   */
  public IXMappingResolver readXMappingResolver(String implType)
  {
    XMappingResolverFactory xMappingResolverFactory = XStructurePlugin.getInstance()
      .getXMappingResolverFactory();
    IXMappingResolver xMappingResolver = xMappingResolverFactory.getMappingResolver(implType);
    if (xMappingResolver == null)
    {
      // TODO display message to user
      // TODO check if implementation is supported
      throw new ConversionException("Unkown implementation type : " + implType
        + ". Use one of these : " + xMappingResolverFactory.getRegisterTypes());
    }
    return xMappingResolver;
  }
}
