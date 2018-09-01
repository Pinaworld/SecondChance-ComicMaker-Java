package org.comicteam.controllers;

import org.comicteam.*;
import org.comicteam.annotations.*;
import org.comicteam.layouts.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class RightClickBookController
{
    public static RightClickBookController controller;

    @Translate
    @FXML
    public Button addPageButton;

    public void initialize ()
    {
        TranslateProcessor.translate(RightClickBookController.class, this);
        controller = this;
    }

    @FXML
    public void addPageButtonClick ()
    {
        WorkingController.controller.hideComponentsTreeRightClick();

        CMFile.cmfile.book.getPages().add(
                new ComicPage(
                        CMFile.cmfile.book.getPages().size() + 1
                )
        );
        CMFile.cmfile.saved = false;

        if (CMFile.cmfile.book.getPages().size() == 1)
        {
            WorkingController.controller.currentPageLabel.setText("1");
        }

        WorkingController.controller.redrawComponentsTree();
    }

    @FXML
    public void renameBook ()
    {

    }
}