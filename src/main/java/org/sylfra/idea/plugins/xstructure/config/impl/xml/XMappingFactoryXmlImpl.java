package org.sylfra.idea.plugins.xstructure.config.impl.xml;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * XML implementation for mapping factory. Loads all XML files from a configuration directory
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingFactoryXmlImpl.java 60 2010-04-11 08:03:27Z syllant $
 */
public class XMappingFactoryXmlImpl
  implements IXMappingFactory<AbstractXMappingSet>, ApplicationComponent
{
  private static final Logger LOGGER = Logger.getInstance(XMappingFactoryXmlImpl.class.getName());

  // Base on XStream library
  private XStream xstream;
  private XMappingSetConverter xMappingSetConverter;
  private Validator xsdValidator;

  // File filter for mapping definition files
  private final static FileFilter MAPPING_FILENAME_FILTER = new FileFilter()
  {
    public boolean accept(File file)
    {
      return (file.isDirectory() || file.getName().endsWith(".xml"));
    }
  };
  private XMappingFactoryXmlImpl.CustomSchemaErrorHandler xsdErrorHandler;

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".XMappingFactory";
  }

  /**
   * {@inheritDoc}
   */
  public void initComponent()
  {
    // Initialize XSD validator
    try
    {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      InputStream xsdStream = getClass().getClassLoader().getResourceAsStream(
        "/org/sylfra/idea/plugins/xstructure/resources/schemas/xstructure_1_1.xsd");
      xsdValidator = factory.newSchema(new StreamSource(xsdStream)).newValidator();
      xsdErrorHandler = new CustomSchemaErrorHandler();
      xsdValidator.setErrorHandler(xsdErrorHandler);
    }
    catch (Exception e)
    {
      LOGGER.error("Failed to create a validating sax parser !", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void disposeComponent()
  {
  }

  /**
   * {@inheritDoc}
   */
  public List<AbstractXMappingSet> loadAll()
  {
    checkXStream();

    File configDirectory = new File(XStructurePlugin.getInstance()
      .getSettingsComponent().getState().getMappingsStorageDir());

    List<AbstractXMappingSet> result = new ArrayList<AbstractXMappingSet>();
    parseConfigDir(configDirectory, result);

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  public AbstractXMappingSet reload(AbstractXMappingSet xMappingSet)
  {
    return loadMappingSetFromXml(new File(xMappingSet.getFile().getPath()));
  }

  /**
   * Parse a directory and fills mapping set list
   *
   * @param directory    the mapping set directory
   * @param xMappingSets the current list of mapping sets to fill
   */
  private void parseConfigDir(File directory, List<AbstractXMappingSet> xMappingSets)
  {
    File[] childFiles = directory.listFiles(MAPPING_FILENAME_FILTER);
    if (childFiles != null)
    {
      for (File childFile : childFiles)
      {
        if (childFile.isDirectory())
        {
          parseConfigDir(childFile, xMappingSets);
        }
        else
        {
          if (validateAgainstSchema(childFile))
          {
            AbstractXMappingSet xMappingSet = loadMappingSetFromXml(childFile);
            if (xMappingSet != null)
            {
              xMappingSets.add(xMappingSet);
            }
          }
          else
          {
            // TODO report errors to user
            LOGGER.warn("Mapping definition file is invalid, it will be ignored : " + childFile);
          }
        }
      }
    }
  }

  /**
   * Checks if mapping definition file is valid against xStructure XSD schema
   *
   * @param f
   *
   * @return
   */
  private boolean validateAgainstSchema(File f)
  {
    try
    {
      xsdErrorHandler.reset();
      xsdErrorHandler.setCurrentFile(f);

      // TODO : choose proper XSDvalidator according to current XSD
      xsdValidator.validate(new StreamSource(f));
      return !xsdErrorHandler.hasError();
    }
    catch (Exception e)
    {
      LOGGER.warn("Error while validating mapping file", e);
      return false;
    }
  }

  /**
   * Loads a mapping set from a XML file
   *
   * @param file the XML file
   *
   * @return the mapoing set
   */
  @Nullable
  private AbstractXMappingSet loadMappingSetFromXml(File file)
  {
    AbstractXMappingSet xMappingSet;
    try
    {
      xMappingSetConverter.setSrcFile(file);
      xMappingSet = (AbstractXMappingSet) xstream.fromXML(new FileInputStream(file));
    }
    catch (Exception e)
    {
      LOGGER.warn("Failed to load mappings from file : " + file, e);
      return null;
    }

    return xMappingSet;
  }

  /**
   * Initializes XStream environment
   */
  private void checkXStream()
  {
    if (xstream == null)
    {
      xstream = new XStream(new DomDriver());

      xstream.setClassLoader(getClass().getClassLoader());

      xstream.alias("xstructure", IXMappingSet.class);
      xstream.alias("mapping", IXMapping.class);
      xstream.alias("schema", Schema.class);

      xstream.useAttributeFor(String.class);

      xMappingSetConverter = new XMappingSetConverter();
      xstream.registerConverter(xMappingSetConverter);
      xstream.registerConverter(new XMappingConverter());
      xstream.registerConverter(new SchemaConverter());
    }
  }

  /**
   * Convert a skip attribute to an enumeration value
   *
   * @param skipMode the skip mode as string
   *
   * @return the skip mode as enumeration
   */
  public static IXMapping.SkipMode readSkipMode(String skipMode)
  {
    if (skipMode == null)
    {
      return IXMapping.SkipMode.NONE;
    }

    try
    {
      return IXMapping.SkipMode.valueOf(skipMode.toUpperCase());
    }
    catch (Exception e)
    {
      LOGGER.warn("Invalid value for 'skip' attribute : " + skipMode + ". Use one of these : "
        + Arrays.toString(IXMapping.SkipMode.values()).toLowerCase());
      return IXMapping.SkipMode.NONE;
    }
  }

  /**
   * In charge of reporting XSD validation errors
   */
  private class CustomSchemaErrorHandler implements ErrorHandler
  {
    private File currentFile;
    private List<SAXParseException> errors;
    private List<SAXParseException> warnings;

    private CustomSchemaErrorHandler()
    {
      errors = new ArrayList<SAXParseException>();
      warnings = new ArrayList<SAXParseException>();
    }

    public void warning(SAXParseException exception) throws SAXException
    {
      LOGGER.warn("Schema validation warning : " + getErrorMessage(exception));
      errors.add(exception);
    }

    public void error(SAXParseException exception) throws SAXException
    {
      LOGGER.warn("Schema validation error : " + getErrorMessage(exception));
      errors.add(exception);
    }

    public void fatalError(SAXParseException exception) throws SAXException
    {
      LOGGER.warn("Schema validation fatal error : " + getErrorMessage(exception));
      errors.add(exception);
    }

    private String getErrorMessage(SAXParseException exception)
    {
      return currentFile.getPath()
        + " [line:" + exception.getLineNumber()
        + ",col:" + exception.getColumnNumber()
        + "] : " + exception.getMessage();
    }

    public void setCurrentFile(File currentFile)
    {
      this.currentFile = currentFile;
    }

    public void reset()
    {
      errors.clear();
      warnings.clear();
    }

    public boolean hasError()
    {
      return (errors.size() > 0);
    }

    public List<SAXParseException> getErrors()
    {
      return errors;
    }

    public List<SAXParseException> getWarnings()
    {
      return warnings;
    }
  }
}
