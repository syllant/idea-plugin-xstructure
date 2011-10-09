package org.sylfra.idea.plugins.xstructure.registry;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.XStructurePlugin;
import org.sylfra.idea.plugins.xstructure.config.IXMappingFactory;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;
import org.sylfra.idea.plugins.xstructure.config.Schema;
import org.sylfra.idea.plugins.xstructure.util.XSUtils;

import java.util.*;

/**
 * A registry for mapping sets. Stores relations with supported schemas, the edited files, the
 * mapping definition files, ...
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id: XMappingSetRegistry.java 41 2009-03-27 21:15:38Z syllant $
 */
public class XMappingSetRegistry extends VirtualFileAdapter implements ApplicationComponent
{
  /**
   * The last selected mapping set for an edited file
   */
  private final Map<XmlFile, IXMappingSet> byOpenFileRegistry;
  /**
   * Mapping set indexed by their source file
   */
  private final Map<VirtualFile, IXMappingSet> bySourceFileRegistry;
  /**
   * The mapping factory
   */
  private final IXMappingFactory<IXMappingSet> factory;

  public XMappingSetRegistry()
  {
    factory = ApplicationManager.getApplication().getComponent(IXMappingFactory.class);
    byOpenFileRegistry = new HashMap<XmlFile, IXMappingSet>();
    bySourceFileRegistry = new HashMap<VirtualFile, IXMappingSet>();
  }

  /**
   * Loads registry from factory
   */
  public void loadAll()
  {
    byOpenFileRegistry.clear();
    bySourceFileRegistry.clear();

    List<IXMappingSet> xMappingSets = factory.loadAll();
    for (IXMappingSet xMappingSet : xMappingSets)
    {
      registerXMappingSet(xMappingSet);
    }
  }

  /**
   * Reload the mapping set related to the specified file
   *
   * @param file a mapping definition file
   */
  public void reload(@NotNull VirtualFile file)
  {
    IXMappingSet xMappingSet = bySourceFileRegistry.get(file);
    if (xMappingSet == null)
    {
      return;
    }

    IXMappingSet newXMappingSet = factory.reload(xMappingSet);
    if (newXMappingSet == null)
    {
      return;
    }

    // Replace references within open files registry
    for (Map.Entry<XmlFile, IXMappingSet> entry : byOpenFileRegistry.entrySet())
    {
      if (xMappingSet.equals(entry.getValue()))
      {
        entry.setValue(newXMappingSet);
      }
    }

    // Register new mapping set
    registerXMappingSet(newXMappingSet);
  }

  /**
   * Returns true if the specified file is registered as mapping set, false otherwise
   *
   * @param file a mapping definition file
   *
   * @return true if the specified file is registered as mapping set, false otherwise
   */
  public boolean isRegistered(VirtualFile file)
  {
    return bySourceFileRegistry.containsKey(file);
  }

  /**
   * Returns the selected mapping set for the specified XML file
   *
   * @param xmlFile the XML file
   *
   * @return the selected mapping set for the specified XML file
   */
  public IXMappingSet getSelectedXMappingSet(XmlFile xmlFile)
  {
    IXMappingSet result = byOpenFileRegistry.get(xmlFile);

    // /!\ The xml file may be registered with a null IXMappingSet ! 
    if ((result == null) && (!byOpenFileRegistry.containsKey(xmlFile)))
    {
      // Use first eligible mapping set
      Set<IXMappingSet> xMappingSets = getAvailableXMappingSets(xmlFile);

      result = ((xMappingSets == null) || (xMappingSets.isEmpty()))
        ? null : xMappingSets.iterator().next();

      if (result != null)
      {
        byOpenFileRegistry.put(xmlFile, result);
      }
    }

    return result;
  }

  /**
   * Set the specified mapping set for the specified file
   *
   * @param xmlFile     the XML file
   * @param xMappingSet the mapping set to associate to the XML file
   */
  public void setSelectedXMappingSet(@NotNull XmlFile xmlFile, @Nullable IXMappingSet xMappingSet)
  {
    byOpenFileRegistry.put(xmlFile, xMappingSet);
  }

  /**
   * Retrieves all mapping sets available for the specified XML file
   *
   * @param xmlFile the request file
   *
   * @return all mapping sets available for the specified XML file
   */
  @Nullable
  public Set<IXMappingSet> getAvailableXMappingSets(XmlFile xmlFile)
  {
    Set<IXMappingSet> result = new TreeSet<IXMappingSet>(new XMappingSetPriorityComparator());
    for (IXMappingSet mappingSet : bySourceFileRegistry.values())
    {
      for (Schema schema : mappingSet.getSupportedSchemas())
      {
        if (XSUtils.matchUri(schema.getUriPattern(), xmlFile))
        {
          result.add(mappingSet);
        }
      }
    }

    return result;
  }

  /**
   * Registers a mapping set
   *
   * @param xMappingSet the mapping set to register
   */
  private void registerXMappingSet(@NotNull IXMappingSet xMappingSet)
  {
    bySourceFileRegistry.put(xMappingSet.getFile(), xMappingSet);
  }

  /**
   * VirtualFileListener implementation : when a mapping set file is modified, the mapping set
   * is reloaded
   *
   * @param event a file event
   */
  @Override
  public void contentsChanged(VirtualFileEvent event)
  {
    if (event.isFromSave())
    {
      VirtualFile file = event.getFile();
      if (isRegistered(file))
      {
        reload(file);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @NonNls
  @NotNull
  public String getComponentName()
  {
    return XStructurePlugin.COMPONENT_NAME + ".XMappingSetRegistry";
  }

  /**
   * {@inheritDoc}
   */
  public void initComponent()
  {
    ApplicationManager.getApplication().runReadAction(new Runnable()
    {
      public void run()
      {
        loadAll();
      }
    });

    // Register listener for mapping files changes
    VirtualFileManager.getInstance().addVirtualFileListener(this);
  }

  /**
   * {@inheritDoc}
   */
  public void disposeComponent()
  {
    VirtualFileManager.getInstance().removeVirtualFileListener(this);
  }

  /**
   * Compared mapping sets by their priority
   */
  private class XMappingSetPriorityComparator implements Comparator<IXMappingSet>
  {
    /**
     * {@inheritDoc}
     */
    public int compare(IXMappingSet mappingSet1, IXMappingSet mappingSet2)
    {
      if (mappingSet1 == mappingSet2)
      {
        return 0;
      }

      int result = mappingSet1.getPriority() - mappingSet2.getPriority();
      if (result != 0)
      {
        return result;
      }

      // Same priority, use name
      result = mappingSet1.getName().compareTo(mappingSet2.getName());
      if (result != 0)
      {
        return result;
      }

      // Same name, select first one
      return 1;
    }
  }
}
