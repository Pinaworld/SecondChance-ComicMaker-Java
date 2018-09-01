package org.comicteam.controllers;

import java.util.*;

import org.comicteam.*;
import org.comicteam.annotations.*;
import org.comicteam.helpers.*;
import org.comicteam.layouts.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class NewProjectController
{
    @Translate
    @FXML
    public Label nameLabel;
    @Translate
    @FXML
    public Label serieLabel;
    @Translate
    @FXML
    public Label authorsLabel;
    @Translate
    @FXML
    public Label descriptionLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField serieField;
    @FXML
    private TextArea authorsArea;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField hSizeField;
    @FXML
    private TextField vSizeField;
    @Translate
    @FXML
    public Button createProjectButton;

    public void initialize ()
    {
        TranslateProcessor.translate(NewProjectController.class, this);
    }

    @FXML
    public void createProjectButtonClick ()
    {
        if (!FXMLHelper.nameFieldCorrect(nameField) || !FXMLHelper.integerFieldCorrect(hSizeField) || !FXMLHelper.integerFieldCorrect(vSizeField))
        {
            nameFieldKeyReleased();
            hSizeFieldKeyReleased();
            vSizeFieldKeyReleased();

            return;
        }

        CMFile.cmfile = new CMFile(
                nameField.getText(),
                serieField.getText(),
                Arrays.asList(authorsArea.getText().split("\n")),
                descriptionArea.getText(),
                new Size(
                        Integer.valueOf(hSizeField.getText()),
                        Integer.valueOf(vSizeField.getText())
                )
        );

        FXMLHelper.closeAllWindows(createProjectButton);

        FXMLHelper.openWorkingForm();
    }

    @FXML
    public void nameFieldKeyReleased ()
    {
        FXMLHelper.setNameFieldBorder(nameField);
    }

    @FXML
    public void hSizeFieldKeyReleased ()
    {
        FXMLHelper.setSizeFieldBorder(hSizeField);
    }

    @FXML
    public void vSizeFieldKeyReleased ()
    {
        FXMLHelper.setSizeFieldBorder(vSizeField);
    }
}