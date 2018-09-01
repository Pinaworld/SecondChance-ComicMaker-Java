package org.comicteam.controllers;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class SavingWarningController
{
    public static boolean mustCancel;
    public static SavingWarningController controller;

    @Translate
    @FXML
    public Label questionLabel;
    @Translate
    @FXML
    private Button yesButton;
    @Translate
    @FXML
    private Button noButton;
    @Translate
    @FXML
    private Button cancelButton;

    public void initialize ()
    {
        TranslateProcessor.translate(SavingWarningController.class, this);
        mustCancel = false;
        controller = this;
    }

    @FXML
    public void yesButtonClick ()
    {
        FXMLHelper.saveProject();
        FXMLHelper.closeWindow(yesButton);
        FXMLHelper.closeAllWindows(WorkingController.controller.pane);
    }

    @FXML
    public void noButtonClick ()
    {
        FXMLHelper.closeWindow(noButton);
        FXMLHelper.closeWindow(WorkingController.controller.pane);
        FXMLHelper.closeWindow(EditorController.controller.editorPane);
    }

    @FXML
    public void cancelButtonClick ()
    {
        FXMLHelper.closeWindow(cancelButton);
        mustCancel = true;
    }
}
