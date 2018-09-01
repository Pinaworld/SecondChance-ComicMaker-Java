package org.comicteam.controllers;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class MainController
{
    public static MainController controller;

    @Translate
    @FXML
    public Button newProjectButton;
    @Translate
    @FXML
    public Button openProjectButton;
    @Translate
    @FXML
    public Button settingsButton;

    public void initialize ()
    {
        TranslateProcessor.translate(MainController.class, this);
        controller = this;
    }

    @FXML
    public void newProjectButtonMouseClick ()
    {
        FXMLHelper.openNewProjectForm();
    }

    @FXML
    public void openProjectButtonMouseClick ()
    {
        if (FXMLHelper.openProject(openProjectButton))
        {
            FXMLHelper.closeAllWindows(openProjectButton);
            FXMLHelper.openWorkingForm();
        }
    }

    @FXML
    public void settingsButtonMouseClick ()
    {
        FXMLHelper.openSettingsForm();
    }
}
