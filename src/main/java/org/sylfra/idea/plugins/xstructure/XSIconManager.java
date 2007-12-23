package org.sylfra.idea.plugins.xstructure;

import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * Provides an unified way to retrieve an icon defined in plugin resources
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public abstract class XSIconManager
{
  @NonNls
  private static final String PACKAGE_ROOT = "/org/sylfra/idea/plugins/xstructure/resources/icons";

  /**
   * Enumeration for all defined images. Forces callers to use one of these value when retrieving
   * icons
   */
  public static enum IconRef
  {
    xstructure, xstructureLarge, editConfig, reload, check
  }

  /**
   * Retrieve an icon from its reference
   *
   * @param iconRef the icon reference
   *
   * @return the loaded icon
   */
  public static Icon getIcon(IconRef iconRef)
  {
    return IconLoader.getIcon(PACKAGE_ROOT + "/" + iconRef + ".png");
  }
}