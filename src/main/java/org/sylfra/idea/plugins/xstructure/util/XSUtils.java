/*
 * Copyright 2002-2005 Sascha Weinreuter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sylfra.idea.plugins.xstructure.util;

import com.intellij.ide.impl.StructureViewWrapperImpl;
import com.intellij.ide.structureView.StructureViewFactoryEx;
import com.intellij.ide.structureView.impl.StructureViewFactoryImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Miscelleanous convenient methods
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public abstract class XSUtils
{
  private static final String SCHEMA_INSTANCE_URI = "http://www.w3.org/2001/XMLSchema-instance";

  /**
   * Returns the main XSD Schema URI or the DTD URI declared in this XML file
   *
   * @param uriPattern
   *@param xmlFile the request XML file
   *  @return the main XSD Schema URI or the DTD URI declared in this XML file
   */
  @Nullable
  public static boolean matchUri(Pattern uriPattern, @NotNull XmlFile xmlFile)
  {
    XmlDocument document = xmlFile.getDocument();
    if (document == null)
    {
      return false;
    }
    XmlTag rootTag = document.getRootTag();
    if (rootTag == null)
    {
      return false;
    }

    // Try with DTD
    XmlDoctype doctype = document.getProlog().getDoctype();
    if (doctype != null)
    {
      // Try with Public ID
      if ((doctype.getPublicId() != null) && (uriPattern.matcher(doctype.getPublicId()).matches()))
      {
        return true;
      }

      // Try with DTD URI
      if ((doctype.getDtdUri() != null) && (uriPattern.matcher(doctype.getDtdUri()).matches()))
      {
        return true;
      }

      // Otherwise, use system ID
      return (doctype.getSystemId() != null) && (uriPattern.matcher(doctype.getSystemId()).matches());
    }

    // Try with schema / schemaLocation
    XmlAttribute nsAttribute = rootTag.getAttribute("schemaLocation", SCHEMA_INSTANCE_URI);
    if (nsAttribute != null)
    {
      String rootNamespace = xmlFile.getDocument().getRootTag().getNamespace();
      String namespaceDecl = nsAttribute.getValue().trim();
      int pos = namespaceDecl.indexOf(rootNamespace + " ");
      if (pos > -1)
      {
        namespaceDecl = namespaceDecl.substring(pos + rootNamespace.length() + 1);
        pos = namespaceDecl.indexOf(' ');

        String uri = (pos == -1) ? namespaceDecl : namespaceDecl.substring(0, pos);

        return uriPattern.matcher(uri).matches();
      }
    }

    // Try with schema / noNamespaceSchemaLocation
    nsAttribute = rootTag.getAttribute("noNamespaceSchemaLocation", SCHEMA_INSTANCE_URI);

    return (nsAttribute != null) && uriPattern.matcher(nsAttribute.getValue().trim()).matches();
  }

  /**
   * Reload structure view
   *
   * @param project current project
   */
  public static void reloadStructureView(final Project project)
  {
    // TODO find a better way to reload structure view ;-)
    ApplicationManager.getApplication().invokeLater(new Runnable()
    {
      public void run()
      {
        StructureViewFactoryImpl structureViewFactory =
          (StructureViewFactoryImpl) StructureViewFactoryEx.getInstance(project);
        StructureViewWrapperImpl structureViewWrapper =
          (StructureViewWrapperImpl) structureViewFactory.getStructureViewWrapper();
        structureViewWrapper.rebuild();
      }
    });
  }
}
