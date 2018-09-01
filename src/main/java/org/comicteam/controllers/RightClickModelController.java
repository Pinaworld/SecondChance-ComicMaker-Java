package org.comicteam.controllers;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class RightClickModelController
{
    public static RightClickModelController controller;

    @Translate
    @FXML
    public Button modifyModelButton;
    @Translate
    @FXML
    public Button deleteModelButton;

    @FXML
    private TextField modelNameField;

    public void initialize ()
    {
        TranslateProcessor.translate(RightClickModelController.class, this);
        modelNameField.setText(FXMLHelper.getSelectedModel().getName());
        controller = this;
    }

    @FXML
    public void modelNameFieldReleased (KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER)
        {
            FXMLHelper.getSelectedModel().setName(modelNameField.getText());
            System.out.println(FXMLHelper.getSelectedModel().getName());
            WorkingController.controller.redrawComponentsTree();
            WorkingController.controller.hideComponentsTreeRightClick();
        }

    }

    @FXML
    public void deleteModelButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        FXMLHelper.getPanelOfSelectedModel().getModels().remove(FXMLHelper.getSelectedModel());

        WorkingController.controller.redrawComponentsTree();
        EditorController.controller.redrawEditorPane();
    }
}
