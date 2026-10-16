// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

/** Hands the blocks editor's Blockly panel to the workspace region (P5-43). */
public class BlocksWorkspacePresenter {

  /** What any layout's blocks workspace must be able to do. */
  public interface View {
    /** The Blockly panel (features/ owns it); drawing stays Blockly's. */
    void showBlockly(PanelContent blockly);
  }
}
