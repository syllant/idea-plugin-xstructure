package org.sylfra.idea.plugins.xstructure.resolution;

import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sylfra.idea.plugins.xstructure.config.IXMapping;
import org.sylfra.idea.plugins.xstructure.config.IXMappingExp;
import org.sylfra.idea.plugins.xstructure.config.IXMappingSet;

/**
 * In charge of resolving mappings
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public interface IXMappingResolver
{
  /**
   * Returns the resolver implementation type
   *
   * @return the resolver implementation type
   */
  String getType();

  /**
   * Resolves text form raw expression for the specified XML tag
   *
   * @param xmlTag     the XML tag context
   * @param mappingExp the raw expession to resolve
   *
   * @return the resolution result
   *
   * @throws XMappingException if any error occurs
   */
  @NotNull
  String resolveTargetText(@NotNull XmlTag xmlTag, @NotNull IXMappingExp mappingExp) throws
    XMappingException;

  /**
   * Finds the appropriate mapping for the specified XML tag
   *
   * @param xMappingSet the current mapping set
   * @param xmlTag      the requested XML tag
   *
   * @return the mapping if found, null otherwise
   */
  @Nullable
  IXMapping findMatchingMapping(@NotNull IXMappingSet xMappingSet, @NotNull XmlTag xmlTag);

  /**
   * Creates a new mapping set
   *
   * @return a new mapping set
   */
  @NotNull
  IXMappingSet createMappingSet();

  /**
   * Creates a new mapping
   *
   * @return a new mapping
   */
  @NotNull
  IXMapping createMapping();

  /**
   * Validates the specified mapping. Implementations may check match or text expressions
   *
   * @param xMapping the request mapping
   *
   * @throws XMappingException if any error occurs
   */
  void validateMapping(@NotNull IXMapping xMapping) throws XMappingException;

  /**
   * Checks if this resolver is supported. This method throws an exception if the resolver is not
   * supported. It does nothing otherwise.
   *
   * @throws ImplNotSupportedException if the resolver is not supported
   */
  void checkSupported() throws ImplNotSupportedException;
}
