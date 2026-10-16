# Folder structure after P7

The complete `appinventor/appengine/src/com/google/appinventor/client/` tree once
P0–P7 are merged, for classic and neo. Generated from `docs/file-map.html` with
the corrections made while writing the tickets:

- `Ode.java` stays in `client/` as the GWT entry point, much thinner
  (`docs/02-target.md`).
- `widgets/Icon` (image and caption) stays with the mocks as `NonVisibleIcon`;
  the controls' `Icon` comes from `components/Icon`.
- `gwt.css` stays in `war/static/css/`.
- Dialog names under `features/dialogs/system/` for the dialogs built inline
  today are proposals; P5-41 settles them.
- The views follow decisions 0008 and 0009: one template per page region, and
  the designer and blocks regions (palette, viewer, component tree, media,
  properties, drawers) are views owned by each layout. Today's palette, tree and
  properties classes listed under `features/` split into presenters
  (`core/presenters`) and those layout regions in P5-32 and P5-43.
- CSS files follow decision 0010; shared CSS is `features/public/features/`
  (`reset.css`, `layout.css` and shared feature files).
- Transitional classes (`actions/CommandAction`, `client/ShellBinders`) are gone
  by then (P5-42, P4-21).

Nova, when it comes, is one more folder next to `classic/` and `neo/` with the
same shape.

```
client/
├── core/   # logic only: no widgets, no CSS
│   ├── actions/   # every command, by ActionId
│   │   ├── account/
│   │   │   ├── DeleteAccountAction.java
│   │   │   ├── DeleteKeystoreAction.java
│   │   │   ├── DownloadCsrAction.java
│   │   │   ├── DownloadKeystoreAction.java
│   │   │   ├── SelectLanguage.java
│   │   │   ├── SignOutAction.java
│   │   │   └── UploadKeystoreAction.java
│   │   ├── admin/
│   │   │   └── SwitchToUserAdminAction.java
│   │   ├── build/
│   │   │   ├── BarcodeAction.java
│   │   │   ├── DownloadUserSourceAction.java
│   │   │   └── GenerateYailAction.java
│   │   ├── connect/
│   │   │   ├── AboutCompanionAction.java
│   │   │   ├── ChromebookAction.java
│   │   │   ├── CompanionUpdateAction.java
│   │   │   ├── EmulatorAction.java
│   │   │   ├── HardResetAction.java
│   │   │   ├── RefreshCompanionAction.java
│   │   │   ├── ResetAction.java
│   │   │   ├── SaveProjectToCompanionAction.java
│   │   │   ├── UsbAction.java
│   │   │   └── WirelessAction.java
│   │   ├── editor/
│   │   │   ├── SwitchToBlocksEditorAction.java
│   │   │   ├── SwitchToFormEditorAction.java
│   │   │   ├── ToggleConsoleAction.java
│   │   │   └── ToggleTutorialAction.java
│   │   ├── gallery/
│   │   │   ├── LoginToGalleryAction.java
│   │   │   └── SendToGalleryAction.java
│   │   ├── help/
│   │   │   ├── AboutAction.java
│   │   │   ├── OpenFeedbackAction.java
│   │   │   ├── OpenGuideAction.java
│   │   │   ├── OpenLinkAction.java
│   │   │   ├── ShowShortcutsAction.java
│   │   │   └── ShowSplashAction.java
│   │   ├── project/
│   │   │   ├── CheckpointAction.java
│   │   │   ├── DeleteAction.java
│   │   │   ├── DeleteForeverProjectAction.java
│   │   │   ├── ExportAllProjectsAction.java
│   │   │   ├── ExportProjectAction.java
│   │   │   ├── ImportProjectAction.java
│   │   │   ├── ImportTemplateAction.java
│   │   │   ├── MoveProjectsAction.java
│   │   │   ├── NewFolderAction.java
│   │   │   ├── NewProjectAction.java
│   │   │   ├── ProjectPropertiesAction.java
│   │   │   ├── RestoreProjectAction.java
│   │   │   ├── SaveAction.java
│   │   │   ├── SaveAsAction.java
│   │   │   ├── SwitchToProjectAction.java
│   │   │   └── TrashAction.java
│   │   ├── screen/
│   │   │   ├── AddFormAction.java
│   │   │   ├── RemoveFormAction.java
│   │   │   └── SwitchScreenAction.java
│   │   ├── settings/
│   │   │   ├── DisableAutoloadAction.java
│   │   │   ├── EnableAutoloadAction.java
│   │   │   ├── SetFontDyslexicAction.java
│   │   │   ├── SetFontRegularAction.java
│   │   │   ├── ShowAppStoreSettingsDialog.java
│   │   │   └── UISettingsAction.java
│   │   ├── Action.java
│   │   ├── ActionId.java
│   │   ├── ActionRegistry.java
│   │   └── IconKey.java
│   ├── admin/
│   │   └── AdminComparators.java
│   ├── commands/
│   │   ├── AddFormCommand.java
│   │   ├── BuildCommand.java
│   │   ├── ChainableCommand.java
│   │   ├── CommandRegistry.java
│   │   ├── CopyYoungAndroidProjectCommand.java
│   │   ├── DeleteFileCommand.java
│   │   ├── DownloadFileCommand.java
│   │   ├── DownloadProjectOutputCommand.java
│   │   ├── EnsurePhoneConnectedCommand.java
│   │   ├── GenerateYailCommand.java
│   │   ├── PreviewFileCommand.java
│   │   ├── ProjectNodeCommand.java
│   │   ├── SaveAllEditorsCommand.java
│   │   ├── SaveScreenCheckpointCommand.java
│   │   ├── ShowBarcodeCommand.java
│   │   ├── WaitForBuildResultCommand.java
│   │   └── WarningDialogCommand.java
│   ├── components/
│   │   ├── ComponentDatabase.java
│   │   ├── ComponentNotFoundException.java
│   │   ├── DataStoreProvider.java
│   │   ├── MutableDataStoreProvider.java
│   │   └── SimpleComponentDatabase.java
│   ├── editor/
│   │   ├── EditorManager.java
│   │   ├── LoadBlocksException.java
│   │   └── YailGenerationException.java
│   ├── errors/
│   │   ├── BugReport.java
│   │   └── ErrorReporter.java
│   ├── gallery/
│   │   └── LoadGalleryProject.java
│   ├── i18n/
│   │   ├── OdeMessages.java
│   │   └── OdeMessages_*.properties
│   ├── json/
│   │   ├── JsArray.java
│   │   ├── JsObject.java
│   │   ├── JSON.java
│   │   └── JsonUtil.java
│   ├── jzip/
│   │   ├── GenerateOptions.java
│   │   ├── JSZip.java
│   │   ├── LoadOptions.java
│   │   ├── TextDecoder.java
│   │   ├── TextEncoder.java
│   │   ├── Type.java
│   │   └── ZipObject.java
│   ├── layout/
│   │   └── LayoutId.java
│   ├── local/
│   │   ├── LocalDownloader.java
│   │   ├── LocalProjectService.java
│   │   ├── LocalTokenAuthService.java
│   │   ├── LocalUploader.java
│   │   └── LocalUserInfoService.java
│   ├── presenters/   # logic behind each view + its View interface
│   │   ├── project/
│   │   │   ├── NewProjectPresenter.java
│   │   │   ├── NewUrlDialogCallback.java
│   │   │   └── RequestProjectNewNameInterface.java
│   │   ├── settings/
│   │   │   └── AppearancePresenter.java
│   │   ├── DesignToolbarPresenter.java
│   │   ├── MainMenuPresenter.java
│   │   ├── ProjectListPresenter.java
│   │   ├── ProjectToolbarPresenter.java
│   │   ├── ShellPresenter.java
│   │   └── TopBarPresenter.java
│   ├── project/
│   │   ├── folders/
│   │   │   ├── Folder.java
│   │   │   ├── FolderJSONKeys.java
│   │   │   ├── FolderManager.java
│   │   │   └── FolderManagerEventListener.java
│   │   ├── AssetManager.java
│   │   ├── ComponentDatabaseChangeListener.java
│   │   ├── Project.java
│   │   ├── ProjectChangeAdapter.java
│   │   ├── ProjectChangeListener.java
│   │   ├── ProjectComparators.java
│   │   ├── ProjectManager.java
│   │   ├── ProjectManagerEventAdapter.java
│   │   ├── ProjectManagerEventListener.java
│   │   ├── ProjectSelectionChangeHandler.java
│   │   └── YoungAndroidFormUpgrader.java
│   ├── properties/
│   │   ├── json/
│   │   │   ├── ClientJsonArray.java
│   │   │   ├── ClientJsonBoolean.java
│   │   │   ├── ClientJsonNumber.java
│   │   │   ├── ClientJsonObject.java
│   │   │   ├── ClientJsonParser.java
│   │   │   ├── ClientJsonString.java
│   │   │   └── ClientJsonValue.java
│   │   ├── BadPropertyEditorException.java
│   │   ├── Properties.java
│   │   └── Property.java
│   ├── rpc/
│   │   ├── ExtendedServiceProxy.java
│   │   ├── OdeAsyncCallback.java
│   │   ├── RpcListener.java
│   │   └── RpcListenerAdapter.java
│   ├── settings/
│   │   ├── project/
│   │   │   ├── ProjectSettings.java
│   │   │   └── YoungAndroidSettings.java
│   │   ├── user/
│   │   │   ├── BlocksSettings.java
│   │   │   ├── GeneralSettings.java
│   │   │   ├── SplashSettings.java
│   │   │   ├── UserSettings.java
│   │   │   └── YoungAndroidSettings.java
│   │   ├── CommonSettings.java
│   │   ├── Settings.java
│   │   ├── SettingsAccessProvider.java
│   │   └── UserPreferences.java
│   ├── theme/
│   │   ├── ThemeChangedEvent.java
│   │   ├── ThemeMode.java
│   │   └── ThemeService.java
│   ├── tracking/
│   │   └── Tracking.java
│   ├── upload/
│   │   └── FileUploadErrorCode.java
│   ├── util/
│   │   ├── Color.java
│   │   ├── Downloader.java
│   │   ├── MultiRegistry.java
│   │   ├── Promise.java
│   │   ├── Registry.java
│   │   ├── Uploader.java
│   │   └── Urls.java
│   └── validation/
│       └── TextValidators.java
├── features/   # shared screens, written once, look from the active controls
│   ├── admin/
│   │   └── AdminUserList.java
│   ├── assets/
│   │   ├── AssetContextMenu.java
│   │   └── AssetList.java
│   ├── blocks/
│   │   ├── Block.java
│   │   ├── BlockDrawerSelectionListener.java
│   │   ├── BlocklyPanel.java
│   │   ├── BlocklyUtil.java
│   │   ├── BlocksCategory.java
│   │   ├── BlocksCodeGenerationException.java
│   │   ├── BlocksCodeGenerationTarget.java
│   │   ├── BlocksEditor.java
│   │   ├── BlocksLanguage.java
│   │   ├── Connection.java
│   │   ├── Coordinate.java
│   │   ├── EventHelper.java
│   │   ├── Field.java
│   │   ├── Input.java
│   │   ├── LoadBlocksException.java
│   │   ├── WorkspaceSvg.java
│   │   └── YaBlocksEditor.java
│   ├── console/
│   │   └── ConsolePanel.java
│   ├── dataviz/
│   │   └── DataVisualizerPanel.java
│   ├── designer/
│   │   ├── dnd/
│   │   │   ├── ClonedWidget.java
│   │   │   ├── DragSource.java
│   │   │   ├── DragSourcePanel.java
│   │   │   ├── DragSourceSupport.java
│   │   │   ├── DropTarget.java
│   │   │   └── HTML5DragDrop.java
│   │   ├── mocks/   # the user's app components (phone theme, not layout)
│   │   │   ├── i18n/
│   │   │   │   └── ComponentTranslations.java
│   │   │   ├── public/
│   │   │   │   └── mocks/
│   │   │   │       ├── android_holo.css
│   │   │   │       ├── android_material.css
│   │   │   │       ├── DarkTheme.css
│   │   │   │       └── iOS.css
│   │   │   ├── utils/
│   │   │   │   ├── PropertiesUtil.java
│   │   │   │   └── SVGPanel.java
│   │   │   ├── DataFileChangeListener.java
│   │   │   ├── DesignPreviewChangeListener.java
│   │   │   ├── FormChangeListener.java
│   │   │   ├── LayoutInfo.java
│   │   │   ├── MockAbsoluteArrangement.java
│   │   │   ├── MockAbsoluteLayout.java
│   │   │   ├── MockAreaChartDataModel.java
│   │   │   ├── MockAreaChartView.java
│   │   │   ├── MockAxisChartView.java
│   │   │   ├── MockBall.java
│   │   │   ├── MockBarChartDataModel.java
│   │   │   ├── MockBarChartView.java
│   │   │   ├── MockButton.java
│   │   │   ├── MockButtonBase.java
│   │   │   ├── MockCanvas.java
│   │   │   ├── MockCanvasLayout.java
│   │   │   ├── MockChart.java
│   │   │   ├── MockChartData.java
│   │   │   ├── MockChartData2D.java
│   │   │   ├── MockChartDataModel.java
│   │   │   ├── MockChartLayout.java
│   │   │   ├── MockChartView.java
│   │   │   ├── MockChatBot.java
│   │   │   ├── MockCheckBox.java
│   │   │   ├── MockCircle.java
│   │   │   ├── MockCircularProgress.java
│   │   │   ├── MockCloudDB.java
│   │   │   ├── MockComponent.java
│   │   │   ├── MockComponentsUtil.java
│   │   │   ├── MockContactPicker.java
│   │   │   ├── MockContainer.java
│   │   │   ├── MockDataFile.java
│   │   │   ├── MockDatePicker.java
│   │   │   ├── MockDesignerRoot.java
│   │   │   ├── MockEmailPicker.java
│   │   │   ├── MockFeatureCollection.java
│   │   │   ├── MockFeatureCollectionLayout.java
│   │   │   ├── MockFilePicker.java
│   │   │   ├── MockFirebaseDB.java
│   │   │   ├── MockForm.java
│   │   │   ├── MockFormHelper.java
│   │   │   ├── MockFormLayout.java
│   │   │   ├── MockFusionTablesControl.java
│   │   │   ├── MockHorizontalArrangement.java
│   │   │   ├── MockHVArrangement.java
│   │   │   ├── MockHVArrangementHelper.java
│   │   │   ├── MockHVLayout.java
│   │   │   ├── MockHVLayoutBase.java
│   │   │   ├── MockImage.java
│   │   │   ├── MockImageBase.java
│   │   │   ├── MockImageBot.java
│   │   │   ├── MockImagePicker.java
│   │   │   ├── MockImageSprite.java
│   │   │   ├── MockLabel.java
│   │   │   ├── MockLayout.java
│   │   │   ├── MockLinearProgress.java
│   │   │   ├── MockLineChartBaseDataModel.java
│   │   │   ├── MockLineChartDataModel.java
│   │   │   ├── MockLineChartView.java
│   │   │   ├── MockLineChartViewBase.java
│   │   │   ├── MockLineString.java
│   │   │   ├── MockListPicker.java
│   │   │   ├── MockListView.java
│   │   │   ├── MockMap.java
│   │   │   ├── MockMapFeature.java
│   │   │   ├── MockMapFeatureBase.java
│   │   │   ├── MockMapFeatureBaseWithFill.java
│   │   │   ├── MockMapLayout.java
│   │   │   ├── MockMarker.java
│   │   │   ├── MockNonVisibleComponent.java
│   │   │   ├── MockPasswordTextBox.java
│   │   │   ├── MockPhoneNumberPicker.java
│   │   │   ├── MockPieChartDataModel.java
│   │   │   ├── MockPieChartView.java
│   │   │   ├── MockPointChartDataModel.java
│   │   │   ├── MockPointChartView.java
│   │   │   ├── MockPolygon.java
│   │   │   ├── MockPolygonBase.java
│   │   │   ├── MockRadioButton.java
│   │   │   ├── MockRectangle.java
│   │   │   ├── MockScatterChartDataModel.java
│   │   │   ├── MockScatterChartView.java
│   │   │   ├── MockScrollHorizontalArrangement.java
│   │   │   ├── MockScrollVerticalArrangement.java
│   │   │   ├── MockSlider.java
│   │   │   ├── MockSpinner.java
│   │   │   ├── MockSpreadsheet.java
│   │   │   ├── MockSprite.java
│   │   │   ├── MockSwitch.java
│   │   │   ├── MockTableArrangement.java
│   │   │   ├── MockTableLayout.java
│   │   │   ├── MockTextBox.java
│   │   │   ├── MockTextBoxBase.java
│   │   │   ├── MockTimePicker.java
│   │   │   ├── MockToggleBase.java
│   │   │   ├── MockTranslator.java
│   │   │   ├── MockTrendline.java
│   │   │   ├── MockTwitter.java
│   │   │   ├── MockVerticalArrangement.java
│   │   │   ├── MockVideoPlayer.java
│   │   │   ├── MockVisibleComponent.java
│   │   │   ├── MockWebViewer.java
│   │   │   ├── MockWrapper.java
│   │   │   └── NonVisibleIcon.java
│   │   ├── palette/
│   │   │   ├── AbstractPaletteItemWidget.java
│   │   │   ├── AbstractPalettePanel.java
│   │   │   ├── BaseComponentFactory.java
│   │   │   ├── CollapsablePanel.java
│   │   │   ├── CollapsiblePaletteCategoryPanel.java
│   │   │   ├── ComponentFactory.java
│   │   │   ├── ComponentHelpWidget.java
│   │   │   ├── ComponentIcons.java
│   │   │   ├── ComponentRemoveWidget.java
│   │   │   ├── DropTargetProvider.java
│   │   │   ├── icons/   # component palette icons, shared default
│   │   │   ├── LegoPaletteHelper.java
│   │   │   ├── OrderedPaletteHelper.java
│   │   │   ├── PaletteHelper.java
│   │   │   ├── SimpleComponentDescriptor.java
│   │   │   ├── SimplePaletteItem.java
│   │   │   ├── SimplePalettePanel.java
│   │   │   ├── YoungAndroidComponentFactory.java
│   │   │   └── YoungAndroidPalettePanel.java
│   │   ├── tree/
│   │   │   ├── ISourceStructureBox.java
│   │   │   ├── SourceStructureBoxFilter.java
│   │   │   ├── SourceStructureExplorer.java
│   │   │   └── SourceStructureExplorerItem.java
│   │   ├── DesignerChangeListener.java
│   │   ├── DesignerEditor.java
│   │   ├── DesignerRootComponent.java
│   │   ├── HiddenComponentsManager.java
│   │   ├── SimpleEditor.java
│   │   ├── SimpleNonVisibleComponentsPanel.java
│   │   ├── SimpleVisibleComponentsPanel.java
│   │   ├── ViewerHeader.java
│   │   ├── ViewerHeader.ui.xml
│   │   ├── YaFormEditor.java
│   │   └── YaNonVisibleComponentsPanel.java
│   ├── dialogs/   # every wizard and dialog
│   │   ├── build/
│   │   │   └── BarcodeDialog.java
│   │   ├── component/
│   │   │   ├── ComponentImportWizard.java
│   │   │   ├── ComponentRenameWizard.java
│   │   │   ├── ComponentUploadWizard.java
│   │   │   ├── DeleteComponentDialog.java
│   │   │   └── RenameComponentDialog.java
│   │   ├── files/
│   │   │   ├── DeleteFileDialog.java
│   │   │   ├── FileUploadError.java
│   │   │   ├── FileUploadError.ui.xml
│   │   │   ├── FileUploadErrorDialog.java
│   │   │   ├── FileUploadErrorDialog.ui.xml
│   │   │   ├── FileUploadWizard.java
│   │   │   ├── FileUploadWizard.ui.xml
│   │   │   └── PreviewFileDialog.java
│   │   ├── project/
│   │   │   ├── CopyProjectDialog.java
│   │   │   ├── DownloadUserSourceWizard.java
│   │   │   ├── FolderTreeItem.java
│   │   │   ├── InputTemplateUrlWizard.java
│   │   │   ├── MarkOriginWizard.java
│   │   │   ├── MoveProjectsWizard.java
│   │   │   ├── MoveProjectsWizard.ui.xml
│   │   │   ├── NewFolderWizard.java
│   │   │   ├── NewFolderWizard.ui.xml
│   │   │   ├── NewProjectDialog.java
│   │   │   ├── NewProjectDialog.ui.xml
│   │   │   ├── NoProjectDialog.java
│   │   │   ├── NoProjectDialog.ui.xml
│   │   │   ├── ProjectPropertiesDialog.java
│   │   │   ├── ProjectPropertiesDialog.ui.xml
│   │   │   ├── ProjectUploadWizard.java
│   │   │   ├── ProjectUploadWizard.ui.xml
│   │   │   ├── RenameProjectDialog.java
│   │   │   ├── TemplateUploadWizard.java
│   │   │   └── UrlImportWizard.java
│   │   ├── screen/
│   │   │   └── AddScreenDialog.java
│   │   ├── settings/
│   │   │   ├── AppearanceDialog.java
│   │   │   ├── AppearanceDialog.ui.xml
│   │   │   ├── AppStoreSettingsDialog.java
│   │   │   ├── AppStoreSettingsDialog.ui.xml
│   │   │   └── KeystoreUploadWizard.java
│   │   └── system/
│   │       ├── AboutCompanionDialog.java
│   │       ├── AboutDialog.java
│   │       ├── BashWarningDialog.java
│   │       ├── BlocksTruncatedDialog.java
│   │       ├── CorruptedProjectDialog.java
│   │       ├── DisabledAccountDialog.java
│   │       ├── EmptyTrashDialog.java
│   │       ├── ErrorDialog.java
│   │       ├── ErrorDialog.ui.xml
│   │       ├── FinalDialog.java
│   │       ├── GalleryLoadingDialog.java
│   │       ├── GenericWarningDialog.java
│   │       ├── InvalidSessionDialog.java
│   │       ├── MessageDialog.java
│   │       ├── SessionExpiredDialog.java
│   │       ├── ShortcutsDialog.java
│   │       ├── SurveyDialog.java
│   │       ├── WarningDialog.java
│   │       ├── WelcomeDialog.java
│   │       └── WontConnectDialog.java
│   ├── editor/
│   │   ├── FileEditor.java
│   │   ├── ProjectEditor.java
│   │   ├── ProjectEditorFactory.java
│   │   ├── ProjectEditorRegistry.java
│   │   └── YaProjectEditor.java
│   ├── gallery/
│   │   └── GalleryImages.java
│   ├── properties/
│   │   ├── editors/
│   │   │   ├── ListViewAddDataDialog.java
│   │   │   ├── ListViewAddDataDialog.ui.xml
│   │   │   ├── ListViewDataRow.java
│   │   │   ├── ListViewDataRow.ui.xml
│   │   │   ├── ListWithNone.java
│   │   │   ├── YoungAndroidAccelerometerSensitivityChoicePropertyEditor.java
│   │   │   ├── YoungAndroidAlignmentChoicePropertyEditor.java
│   │   │   ├── YoungAndroidAssetSelectorPropertyEditor.java
│   │   │   ├── YoungAndroidBestFitModelPropertyEditor.java
│   │   │   ├── YoungAndroidBooleanPropertyEditor.java
│   │   │   ├── YoungAndroidButtonShapeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidChartLabelValueTypeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidChartLineTypeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidChartPointShapeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidChartTypeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidChatbotModelPropertyEditor.java
│   │   │   ├── YoungAndroidChatbotProviderPropertyEditor.java
│   │   │   ├── YoungAndroidColorChoicePropertyEditor.java
│   │   │   ├── YoungAndroidComponentSelectorPropertyEditor.java
│   │   │   ├── YoungAndroidDataColumnSelectorProperty.java
│   │   │   ├── YoungAndroidDefaultURLPropertyEditor.java
│   │   │   ├── YoungAndroidFileScopePropertyEditor.java
│   │   │   ├── YoungAndroidFloatRangePropertyEditor.java
│   │   │   ├── YoungAndroidFontTypefaceChoicePropertyEditor.java
│   │   │   ├── YoungAndroidGeographicPointPropertyEditor.java
│   │   │   ├── YoungAndroidGeoJSONPropertyEditor.java
│   │   │   ├── YoungAndroidHorizontalAlignmentChoicePropertyEditor.java
│   │   │   ├── YoungAndroidIntegerRangePropertyEditor.java
│   │   │   ├── YoungAndroidLegoEv3ColorSensorModeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidLegoEv3GyroSensorModeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidLegoEv3SensorPortChoicePropertyEditor.java
│   │   │   ├── YoungAndroidLegoEv3UltrasonicSensorModeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidLegoNxtSensorPortChoicePropertyEditor.java
│   │   │   ├── YoungAndroidLengthPropertyEditor.java
│   │   │   ├── YoungAndroidListViewAddDataPropertyEditor.java
│   │   │   ├── YoungAndroidListViewLayoutChoicePropertyEditor.java
│   │   │   ├── YoungAndroidMapCustomUrlPropertyEditor.java
│   │   │   ├── YoungAndroidMapScaleUnitsPropertyEditor.java
│   │   │   ├── YoungAndroidMapTypePropertyEditor.java
│   │   │   ├── YoungAndroidMinSdkPropertyEditor.java
│   │   │   ├── YoungAndroidNavigationMethodChoicePropertyEditor.java
│   │   │   ├── YoungAndroidRecyclerViewOrientationPropertyEditor.java
│   │   │   ├── YoungAndroidScreenAnimationChoicePropertyEditor.java
│   │   │   ├── YoungAndroidScreenOrientationChoicePropertyEditor.java
│   │   │   ├── YoungAndroidSensorDistIntervalChoicePropertyEditor.java
│   │   │   ├── YoungAndroidSensorTimeIntervalChoicePropertyEditor.java
│   │   │   ├── YoungAndroidSizingChoicePropertyEditor.java
│   │   │   ├── YoungAndroidSpriteOriginPropertyEditor.java
│   │   │   ├── YoungAndroidStrokeStylePropertyEditor.java
│   │   │   ├── YoungAndroidTextReceivingPropertyEditor.java
│   │   │   ├── YoungAndroidThemeChoicePropertyEditor.java
│   │   │   ├── YoungAndroidToastLengthChoicePropertyEditor.java
│   │   │   └── YoungAndroidVerticalAlignmentChoicePropertyEditor.java
│   │   ├── AdditionalChoicePropertyEditor.java
│   │   ├── BooleanPropertyEditor.java
│   │   ├── ChoicePropertyEditor.java
│   │   ├── CollapsibleCategoryPanel.java
│   │   ├── ColorChoicePropertyEditor.java
│   │   ├── CountryChoicePropertyEditor.java
│   │   ├── EditableProperties.java
│   │   ├── EditableProperty.java
│   │   ├── FloatPropertyEditor.java
│   │   ├── IntegerPropertyEditor.java
│   │   ├── LanguageChoicePropertyEditor.java
│   │   ├── NonNegativeFloatPropertyEditor.java
│   │   ├── NonNegativeIntegerPropertyEditor.java
│   │   ├── PropertiesPanel.java
│   │   ├── PropertyChangeListener.java
│   │   ├── PropertyEditor.java
│   │   ├── PropertyHelpWidget.java
│   │   ├── ScalingChoicePropertyEditor.java
│   │   ├── StringPropertyEditor.java
│   │   ├── SubsetJSONPropertyEditor.java
│   │   ├── TextAreaPropertyEditor.java
│   │   ├── TextPropertyEditor.java
│   │   └── TextPropertyEditorBase.java
│   ├── public/
│   │   └── features/
│   │       └── layout.css + reset.css
│   ├── system/
│   │   ├── ConnectProgressBar.java
│   │   ├── ErrorBanner.java
│   │   ├── ExpiredServiceOverlay.java
│   │   ├── ExpiredServiceOverlay.ui.xml
│   │   ├── MiniProgressBar.java
│   │   ├── ProgressDialog.java
│   │   ├── RpcStatusPopup.java
│   │   └── ShowProgressBarCommand.java
│   ├── tutorial/
│   │   ├── TutorialPanel.java
│   │   └── TutorialPopup.java
│   └── workspace/
│       └── boxes/
│           ├── AdminUserListBox.java
│           ├── AssetListBox.java
│           ├── BlockSelectorBox.java
│           ├── Box.java
│           ├── BoxRegistry.java
│           ├── ColumnLayout.java
│           ├── HandlerPanel.java
│           ├── LayoutChangeListener.java
│           ├── PaletteBox.java
│           ├── PaneLayout.java
│           ├── ProjectListBox.java
│           ├── PropertiesBox.java
│           ├── SourceStructureBox.java
│           ├── ViewerBox.java
│           └── WorkAreaPanel.java
├── controls/   # the contract every layout's building blocks implement
│   ├── behaviour/   # keyboard, focus, ARIA, written once
│   │   ├── ActionBinding.java
│   │   ├── DialogA11y.java
│   │   ├── FocusImplSafari.java
│   │   ├── FocusImplStandard.java
│   │   ├── FocusTrap.java
│   │   ├── ImageAccessibility.java
│   │   ├── MenuKeyboard.java
│   │   ├── PZAwarePositionCallback.java
│   │   ├── RovingTabIndex.java
│   │   ├── TableAccessibility.java
│   │   └── Validator.java
│   ├── standard/   # plain default set a new layout starts from
│   │   ├── StandardButton.java
│   │   ├── StandardDialog.java
│   │   ├── StandardControls.java
│   │   ├── StandardMenuButton.java
│   │   ├── StandardTextField.java
│   │   └── StandardWizard.java
│   ├── uibinder/
│   │   └── ActionItemParser.java
│   ├── Button.java
│   ├── Checkbox.java
│   ├── Collapsible.java
│   ├── ContextMenu.java
│   ├── Dialog.java
│   ├── Icon.java
│   ├── IconButton.java
│   ├── Icons.java
│   ├── Controls.java
│   ├── SharedView.java
│   ├── MenuButton.java
│   ├── MenuItem.java
│   ├── Radio.java
│   ├── Select.java
│   ├── Tabs.java
│   ├── TextField.java
│   ├── ThemedImage.java
│   ├── Toolbar.java
│   ├── ToolbarItem.java
│   ├── Tooltip.java
│   ├── Tree.java
│   └── Wizard.java
├── layouts/   # one self-contained folder per layout
│   ├── Layout.java
│   ├── LayoutRegistry.java
│   ├── classic/
│   │   ├── ClassicLayout.java          # controls, views, stylesheets()
│   │   ├── controls/                   # each: .ui.xml + behaviour .java
│   │   │   ├── ClassicControls.java
│   │   │   ├── ClassicButton, ClassicIconButton
│   │   │   ├── ClassicMenuButton, ClassicMenu, ClassicMenuItem, ClassicMenuSeparator
│   │   │   ├── ClassicTextField, ClassicSelect, ClassicCheckbox
│   │   │   ├── ClassicCollapsible, ClassicTree
│   │   │   └── ClassicDialog
│   │   ├── icons/
│   │   │   └── ClassicIcons.java
│   │   ├── views/                      # each: .ui.xml + .java
│   │   │   ├── shell/      ClassicShell
│   │   │   ├── topbar/     ClassicTopBar
│   │   │   ├── menus/      ClassicMainMenu
│   │   │   ├── status/     ClassicStatusBar
│   │   │   ├── projects/   ClassicProjectsPage, ClassicProjectToolbar,
│   │   │   │               ClassicProjectList, ClassicProjectRow,
│   │   │   │               ClassicProjectFolder
│   │   │   ├── editor/     ClassicEditorPage, ClassicDesignToolbar,
│   │   │   │               ClassicDesignerArea, ClassicPalette,
│   │   │   │               ClassicPaletteItem, ClassicViewer,
│   │   │   │               ClassicComponentTree, ClassicMedia,
│   │   │   │               ClassicProperties, ClassicPropertyRow,
│   │   │   │               ClassicBlocksArea, ClassicBlockDrawers,
│   │   │   │               ClassicBlocksWorkspace
│   │   │   └── admin/      ClassicAdminPage
│   │   └── public/classic/
│   │       ├── tokens.css
│   │       ├── controls.css
│   │       ├── views/      shell.css, projects.css, editor.css
│   │       └── icons/
│   └── neo/                            # same shape, Neo* names
└── Ode.java
```
