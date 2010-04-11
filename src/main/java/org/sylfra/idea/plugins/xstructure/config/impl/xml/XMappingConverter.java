package org.sylfra.idea.plugins.xstructure.config.impl.xml;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.resolution.IXMappingResolver;
import org.sylfra.idea.plugins.xstructure.resolution.XMappingException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * XStream converter for a XMapping definition
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
class XMappingConverter implements Converter
{
  private static final Logger LOGGER = Logger.getInstance(XMappingConverter.class.getName());
  private static final String ICON_DIRNAME = "_icons";

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

    String maxLength = reader.getAttribute("maxlength");
    if (maxLength != null)
    {
      xMapping.setMaxLength(Integer.parseInt(maxLength));
    }

    // Icon
    String iconPath = reader.getAttribute("icon");
    if (iconPath != null)
    {
      xMapping.setIcon(resolveIcon(iconPath));
    }

    // Skip mode
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

  @Nullable
  private Icon resolveIcon(@NotNull String iconPath)
  {
    // 1 - Try with user icons (from directory)
    File configDirectory = new File(XStructurePlugin.getInstance()
      .getSettingsComponent().getState().getMappingsStorageDir());
    File iconDirectory = new File(configDirectory, ICON_DIRNAME);

    if (iconDirectory.isDirectory())
    {
      File iconFile = new File(iconDirectory, iconPath);
      if (iconFile.isFile())
      {
        try
        {
          BufferedImage image = ImageIO.read(iconFile);
          return new ImageIcon(image);
        }
        catch (IOException e)
        {
          LOGGER.warn("Failed to load icon:" + iconFile, e);
        }
      }
    }

    // 2 - Try with bundled icons (from classpath)
    Icon icon = IconLoader.findIcon(iconPath);

    if ((LOGGER.isDebugEnabled()) && (icon == null))
    {
      LOGGER.debug("Can't find icon: " + iconPath);
    }

    return icon;
  }
}