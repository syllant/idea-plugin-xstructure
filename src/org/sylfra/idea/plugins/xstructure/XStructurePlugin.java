package org.sylfra.idea.plugins.xstructure;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.peer.PeerFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.xml.ui.BasicDomElementComponent;
import com.intellij.util.xml.ui.DomFileEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class XStructurePlugin implements ProjectComponent, FileEditorProvider
{
  private Project myProject;

  private ToolWindow myToolWindow;
  private JPanel myContentPanel;

  public static final String TOOL_WINDOW_ID = "XStructure";

  public XStructurePlugin(Project project)
  {
    myProject = project;
  }

  public void projectOpened()
  {
    initToolWindow();
  }

  public void projectClosed()
  {
    unregisterToolWindow();
  }

  public void initComponent()
  {
    // empty
  }

  public void disposeComponent()
  {
    // empty
  }

  @NotNull
  public String getComponentName()
  {
    return "XStructure.XStructureWindowPlugin";
  }

  private void initToolWindow()
  {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(myProject);

    myContentPanel = new JPanel(new BorderLayout());

    myContentPanel.setBackground(UIUtil.getTreeTextBackground());
    myContentPanel.add(new JLabel("Hello World!", JLabel.CENTER), BorderLayout.CENTER);

    myToolWindow =
      toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, false, ToolWindowAnchor.LEFT);
    ContentFactory contentFactory = PeerFactory.getInstance().getContentFactory();
    Content content = contentFactory.createContent(myContentPanel, "SimpleWindow", false);
    myToolWindow.getContentManager().addContent(content);
  }

  private void unregisterToolWindow()
  {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(myProject);
    toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
  }

  public boolean accept(@NotNull Project project, @NotNull VirtualFile file)
  {
    return file.getFileType() == StdFileTypes.XML;
  }

  @NotNull
  public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file)
  {
    return new XXXEditor(project, file);
  }

  public void disposeEditor(@NotNull FileEditor editor)
  {
  }

  @NotNull
  public FileEditorState readState(@NotNull Element sourceElement, @NotNull Project project,
    @NotNull VirtualFile file)
  {
    return DummyFileEditorState.DUMMY;
  }

  public void writeState(@NotNull FileEditorState state, @NotNull Project project,
    @NotNull Element targetElement)
  {
  }

  @NotNull
  @NonNls
  public String getEditorTypeId()
  {
    return getComponentName();
  }

  @NotNull
  public FileEditorPolicy getPolicy()
  {
    return FileEditorPolicy.NONE;
  }

  private static class DummyFileEditorState implements FileEditorState
  {
    public static final FileEditorState DUMMY = new DummyFileEditorState();

    public boolean canBeMergedWith(FileEditorState otherState,
      FileEditorStateLevel level)
    {
      return false;
    }
  }
}
