package org.comicteam.controllers;

import org.comicteam.*;
import org.comicteam.annotations.*;
import org.comicteam.forms.*;
import org.comicteam.helpers.*;
import org.comicteam.layouts.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class RightClickPanelController
{
    public static RightClickPanelController controller;

    @Translate
    @FXML
    public Button addClipartModelButton;
    @Translate
    @FXML
    public Button addModelWithEditorButton;
    @Translate
    @FXML
    public Button addModelWithExternalDocumentButton;
    @Translate
    @FXML
    public Button deletePanelButton;

    @FXML
    private TextField panelNameField;


    public void initialize ()
    {
        TranslateProcessor.translate(RightClickPanelController.class, this);
        panelNameField.setText("Panel");
        controller = this;
    }

    @FXML
    public void addClipartModelButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        ClipartForm form = new ClipartForm();
        form.start(new Stage(StageStyle.DECORATED));
    }

    @FXML
    public void addModelWithEditorButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        ModelEditorForm mef = new ModelEditorForm();
        mef.start(new Stage(StageStyle.DECORATED));
    }

    @FXML
    public void addModelWithExternalDocumentButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        Image image = ExternalDocumentHelper.getImage(WorkingController.controller.componentsTree);

        if (image != null)
        {
            EditorController.controller.addImage(image);
            WorkingController.controller.redrawComponentsTree();
        }
    }

    @FXML
    public void deletePanelButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        TreeItem selectedItem = (TreeItem) WorkingController.controller.componentsTree.getSelectionModel().getSelectedItem();

        ComicPage parentPage = (ComicPage) selectedItem.getParent().getValue();
        ComicPanel panelToDelete = (ComicPanel) selectedItem.getValue();

        parentPage.getPanels().remove(panelToDelete);

        CMFile.cmfile.saved = false;

        WorkingController.controller.redrawComponentsTree();
        EditorController.controller.redrawEditorPane();
    }

    @FXML
    public void panelNameFieldReleased (KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER)
        {
            WorkingController.controller.redrawComponentsTree();
            WorkingController.controller.hideComponentsTreeRightClick();
            return;
        }

        FXMLHelper.getSelectedComicPanel().setName(panelNameField.getText());
        System.out.println(FXMLHelper.getSelectedComicPanel().getName());
    }

    @FXML
    public void addBubbleSpeech ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        SpeechBubbleForm sbf = new SpeechBubbleForm();
        sbf.start(new Stage(StageStyle.DECORATED));
    }
}
