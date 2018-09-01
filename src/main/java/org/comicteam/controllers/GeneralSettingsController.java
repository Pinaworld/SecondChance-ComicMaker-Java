package org.comicteam.controllers;

import java.io.*;
import java.util.*;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;
import org.comicteam.plugins.languages.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class GeneralSettingsController
{
    @Translate
    @FXML
    public Label languageLabel;
    @Translate
    @FXML
    public Label defaultSavePathLabel;

    @FXML
    private ChoiceBox<String> languagesCombo;
    @FXML
    private Button directoryChooserButton;
    @FXML
    private TextField savePathField;

    public void initialize ()
    {
        TranslateProcessor.translate(GeneralSettingsController.class, this);

        savePathField.setText(SettingsHelper.get("savePath"));

        List<Languable> languages = LanguageHelper.getLanguagesAvailables();

        for (Languable l : languages)
        {
            languagesCombo.getItems().add(l.getName());
        }

        languagesCombo.setValue(SettingsHelper.get("language"));

        languagesCombo.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
            {
                languagesCombo.show();
            }
        });

        languagesCombo.setOnAction(e -> {
            languagesComboAction();
        });
    }

    @FXML
    public void directoryChooserButtonClick ()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        File file = chooser.showDialog(directoryChooserButton.getScene().getWindow());

        if (file == null)
        {
            return;
        }

        try
        {
            SettingsHelper.set("savePath", file.getPath());
            savePathField.setText(file.getPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void languagesComboAction ()
    {
        if (languagesCombo.getValue() != null)
        {
            try
            {
                SettingsHelper.set("language", languagesCombo.getValue());
                TranslateProcessor.translate(MainController.class, MainController.controller);
                TranslateProcessor.translate(GeneralSettingsController.class, this);

                if (MenuController.controller != null)
                {
                    TranslateProcessor.translate(MenuController.class, MenuController.controller);
                }

                if (WorkingController.controller != null)
                {
                    TranslateProcessor.translate(WorkingController.class, WorkingController.controller);
                }

                if (RightClickBookController.controller != null)
                {
                    TranslateProcessor.translate(RightClickBookController.class, RightClickBookController.controller);
                }

                if (RightClickPageController.controller != null)
                {
                    TranslateProcessor.translate(RightClickPageController.class, RightClickPageController.controller);
                }

                if (RightClickPanelController.controller != null)
                {
                    TranslateProcessor.translate(RightClickPanelController.class, RightClickPanelController.controller);
                }

                if (RightClickModelController.controller != null)
                {
                    TranslateProcessor.translate(RightClickModelController.class, RightClickModelController.controller);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}