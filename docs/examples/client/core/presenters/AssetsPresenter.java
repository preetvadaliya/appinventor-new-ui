// Copyright 2026 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.core.presenters;

import java.util.List;
import java.util.function.Consumer;

/** The rules behind the Media list (P5-43): the project's asset files. */
public class AssetsPresenter {

  /** What any layout's media region must be able to do. */
  public interface View {
    void showFiles(List<String> names);

    void setUploadEnabled(boolean enabled);

    void onFileChosen(Consumer<String> handler);

    void onUpload(Runnable handler);
  }
}
