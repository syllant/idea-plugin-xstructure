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

import com.intellij.psi.xml.*;
import org.jetbrains.annotations.Nullable;

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
   * @param xmlFile the request XML file
   *
   * @return the main XSD Schema URI or the DTD URI declared in this XML file
   */
  @Nullable
  public static String getSchemaUri(XmlFile xmlFile)
  {
    XmlDocument document = xmlFile.getDocument();
    if (document == null)
    {
      return null;
    }
    XmlTag rootTag = document.getRootTag();
    if (rootTag == null)
    {
      return null;
    }

    // Try with DTD
    XmlDoctype doctype = document.getProlog().getDoctype();
    if (doctype != null)
    {
      return doctype.getDtdUri();
    }

    // Try with schema
    XmlAttribute nsAttribute =
      rootTag.getAttribute("schemaLocation", SCHEMA_INSTANCE_URI);
    if (nsAttribute != null)
    {
      String rootNamespace = xmlFile.getDocument().getRootTag().getNamespace();
      String namespaceDecl = nsAttribute.getValue().trim();
      int pos = namespaceDecl.indexOf(rootNamespace + " ");
      if (pos > -1)
      {
        namespaceDecl = namespaceDecl.substring(pos + rootNamespace.length() + 1);
        pos = namespaceDecl.indexOf(' ');
        return (pos == -1) ? namespaceDecl : namespaceDecl.substring(0, pos);
      }
    }

    return null;
  }
}
