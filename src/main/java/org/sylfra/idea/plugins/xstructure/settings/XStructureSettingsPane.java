package org.sylfra.idea.plugins.xstructure.settings;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Settings panel for xStructure shown in general settings dialog
 * <p/>
 * UI made with IDEA UI Designer
 *
 * @author <a href="mailto:sylfradev@yahoo.fr">Sylvain FRANCOIS</a>
 * @version $Id$
 */
public class XStructureSettingsPane implements Disposable
{
  private JTextField tfMappingsStorageDir;
  private JCheckBox cbSyncMappings;
  private JPanel contentPane;
  private JCheckBox cbOverwriteWhenSyncMappings;

  public XStructureSettingsPane()
  {
    // Disable cbOverwriteWhenSyncMappings if cbSyncMappings is not checked
    cbSyncMappings.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cbOverwriteWhenSyncMappings.setEnabled(cbSyncMappings.isSelected());
      }
    });
  }

  /**
   * Returns the content pane
   *
   * @return the content pane
   */
  public JPanel getContentPane()
  {
    return contentPane;
  }

  /**
   * Update UI from settings bean (auto-generated)
   *
   * @param data the settings bean
   */
  public void setData(XStructureSettings data)
  {
    cbSyncMappings.setSelected(data.isSyncMappingsAtStartup());
    tfMappingsStorageDir.setText(data.getMappingsStorageDir());
    cbOverwriteWhenSyncMappings.setSelected(data.isOverwriteWhenSyncMappings());
  }

  /**
   * Update settings bean from UI (auto-generated)
   *
   * @param data the settings bean to update
   */
  public void getData(XStructureSettings data)
  {
    data.setSyncMappingsAtStartup(cbSyncMappings.isSelected());
    data.setMappingsStorageDir(tfMappingsStorageDir.getText());
    data.setOverwriteWhenSyncMappings(cbOverwriteWhenSyncMappings.isSelected());
  }

  /**
   * Returns true if the settings have been modified, false otherwise (auto-generated)
   *
   * @param data the settings bean
   *
   * @return true if the settings have been modified, false otherwise
   */
  public boolean isModified(XStructureSettings data)
  {
    if (cbSyncMappings.isSelected() != data.isSyncMappingsAtStartup())
    {
      return true;
    }
    if (tfMappingsStorageDir.getText() != null ?
      !tfMappingsStorageDir.getText().equals(data.getMappingsStorageDir()) :
      data.getMappingsStorageDir() != null)
    {
      return true;
    }
    if (cbOverwriteWhenSyncMappings.isSelected() != data.isOverwriteWhenSyncMappings())
    {
      return true;
    }
    return false;
  }

  // To use a FileTextField
  private void createUIComponents()
  {
    FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(false, true, false,
      false, false, false);
    tfMappingsStorageDir =
      FileChooserFactory.getInstance().createFileTextField(fileChooserDescriptor,
        false, this).getField();
  }

  /**
   * {@inheritDoc}
   */
  public void dispose()
  {
  }
}
