package org.comicteam.controllers;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class ClipartController
{
    @Translate
    @FXML
    public Button searchButton;

    @FXML
    private TextField keywordSearchField;
    @FXML
    private ScrollBar resultsScrollBar;
    @FXML
    private GridPane resultsGridPane;
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView imageView00;
    @FXML
    private ImageView imageView01;
    @FXML
    private ImageView imageView10;
    @FXML
    private ImageView imageView11;

    //@FXML
    //private SVGPath svgPath;

    public void initialize ()
    {
        TranslateProcessor.translate(ClipartController.class, this);
        resultsScrollBar.setMax(0);
    }

    @FXML
    public void searchButtonClick ()
    {
        Thread t2 = new Thread(() -> {
            resultsScrollBar.setValue(0);
            resultsScrollBar.setMin(0);
            resultsScrollBar.setMax(0);

            boolean shown = false;
            int actual = 0;

            while (resultsScrollBar.getMax() <= ClipartHelper.getLoadedPagesCount())
            {
                if (resultsScrollBar.getMax() == 1 && !shown)
                {
                    showImages();
                    shown = true;
                }

                if (actual != ClipartHelper.getLoadedPagesCount())
                {
                    resultsScrollBar.setMax(ClipartHelper.getLoadedPagesCount() - 1);
                    actual++;
                }
            }
        });

        Thread t1 = new Thread(() -> {
            t2.start();
            ClipartHelper.request(keywordSearchField.getText());
        });

        t1.start();
    }

    public void showImages ()
    {
        Image topLeft = ClipartHelper.getImageTopLeft((int) resultsScrollBar.getValue());
        Image topRight = ClipartHelper.getImageTopRight((int) resultsScrollBar.getValue());
        Image bottomLeft = ClipartHelper.getImageBottomLeft((int) resultsScrollBar.getValue());
        Image bottomRight = ClipartHelper.getImageBottomRight((int) resultsScrollBar.getValue());

        if (topLeft != null)
        {
            imageView00.setImage(topLeft);
        }

        if (topRight != null)
        {
            imageView01.setImage(topRight);
        }

        if (bottomLeft != null)
        {
            imageView10.setImage(bottomLeft);
        }

        if (bottomRight != null)
        {
            imageView11.setImage(bottomRight);
        }
    }

    @FXML
    public void resultsGridPaneScroll (ScrollEvent e)
    {
        if (e.getDeltaY() > 0 && resultsScrollBar.getValue() > 0)
        {
            resultsScrollBar.decrement();
            showImages();
        }

        if (e.getDeltaY() < 0 && resultsScrollBar.getValue() < resultsScrollBar.getMax())
        {
            resultsScrollBar.increment();
            showImages();
        }
    }

    @FXML
    public void imageView00Click ()
    {
        EditorController.controller.addImage(imageView00.getImage());
        WorkingController.controller.redrawComponentsTree();
    }

    @FXML
    public void imageView01Click ()
    {
        EditorController.controller.addImage(imageView01.getImage());
        WorkingController.controller.redrawComponentsTree();
    }

    @FXML
    public void imageView10Click ()
    {
        EditorController.controller.addImage(imageView10.getImage());
        WorkingController.controller.redrawComponentsTree();
    }

    @FXML
    public void imageView11Click ()
    {
        EditorController.controller.addImage(imageView11.getImage());
        WorkingController.controller.redrawComponentsTree();
    }
}