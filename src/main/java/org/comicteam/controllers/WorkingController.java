package org.comicteam.controllers;

import java.io.*;

import org.comicteam.*;
import org.comicteam.annotations.*;
import org.comicteam.forms.*;
import org.comicteam.helpers.*;
import org.comicteam.layouts.*;
import org.comicteam.models.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class WorkingController
{
    private boolean menuOpened;
    public static WorkingController controller;

    @Translate
    @FXML
    public Label widthLabel;
    @Translate
    @FXML
    public Label heightLabel;

    @FXML
    public AnchorPane pane;
    @FXML
    public TreeView componentsTree;
    @FXML
    public Label currentPageLabel;
    @FXML
    private Label pageCountLabel;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private VBox rightClickBox;
    @FXML
    private AnchorPane measurePane;

    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private Button measureButton;

    public void initialize ()
    {
        TranslateProcessor.translate(WorkingController.class, this);
        measurePane.setVisible(false);

        redrawComponentsTree();

        EditorForm editor = new EditorForm();
        editor.start(new Stage(StageStyle.DECORATED));

        if (CMFile.cmfile.book.getPages().size() > 0)
        {
            selectPage(CMFile.cmfile.book.getPages().get(0));
        }

        controller = this;
    }

    public void redrawComponentsTree ()
    {
        componentsTree.setRoot(new TreeItem(CMFile.cmfile.book.getName()));
        componentsTree.getRoot().setExpanded(true);

        CMFile.cmfile.book.sortPages();
        CMFile.cmfile.book.sortPanels();

        for (ComicPage page : CMFile.cmfile.book.getPages())
        {
            TreeItem<Object> treePage = new TreeItem<>(page);
            componentsTree.getRoot().getChildren().add(treePage);
            componentsTree.getRoot().setExpanded(true);

            for (ComicPanel panel : page.getPanels())
            {
                TreeItem<Object> treePanel = new TreeItem<>(panel);
                treePage.getChildren().add(treePanel);
                treePage.setExpanded(true);

                for (ComicModel model : panel.getModels())
                {
                    treePanel.getChildren().add(new TreeItem<>(model));
                    treePanel.setExpanded(true);
                }
            }
        }
        componentsTree.refresh();

        if (!currentPageLabel.getText().equals(""))
        {
            if (Integer.valueOf(currentPageLabel.getText()) > CMFile.cmfile.book.getPages().size())
            {
                currentPageLabel.setText(String.valueOf(CMFile.cmfile.book.getPages().size()));
            }
        }

        pageCountLabel.setText(String.format("%s", CMFile.cmfile.book.getPages().size()));
    }

    public void alimentateMeasurePane (ComicPanel panel)
    {
        measurePane.setVisible(true);
        xField.setText(String.valueOf(panel.getLayout().getPosition().getHorizontal()));
        yField.setText(String.valueOf(panel.getLayout().getPosition().getVertical()));
        widthField.setText(String.valueOf(panel.getLayout().getSize().getHorizontal()));
        heightField.setText(String.valueOf(panel.getLayout().getSize().getVertical()));
    }

    public void alimentateMeasureModel (ComicModel model)
    {
        measurePane.setVisible(true);
        xField.setText(String.valueOf(model.getLayout().getPosition().getHorizontal()));
        yField.setText(String.valueOf(model.getLayout().getPosition().getVertical()));
        widthField.setText(String.valueOf(model.getLayout().getSize().getHorizontal()));
        heightField.setText(String.valueOf(model.getLayout().getSize().getVertical()));
    }

    public boolean canMovePanel (ComicPanel panel, int x, int y)
    {
        boolean xok = x + panel.getLayout().getSize().getHorizontal() <= MM.toMM((int) EditorController.controller.editorPane.getWidth());
        boolean yok = y + panel.getLayout().getSize().getVertical() <= MM.toMM((int) EditorController.controller.editorPane.getHeight());

        return xok && yok;
    }

    @FXML
    public void measureButtonClick ()
    {
        if (FXMLHelper.integerFieldCorrect(xField) && FXMLHelper.integerFieldCorrect(yField)
                && FXMLHelper.integerFieldCorrect(widthField) && FXMLHelper.integerFieldCorrect(heightField))
        {

            switch (FXMLHelper.getClassOfSelectedObject().getSimpleName())
            {
                case "ComicPanel":
                    if (canMovePanel(FXMLHelper.getSelectedComicPanel(), Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText())))
                    {
                        FXMLHelper.getSelectedComicPanel().getLayout().setPosition(
                                new Position(
                                        Integer.valueOf(xField.getText()),
                                        Integer.valueOf(yField.getText())
                                )
                        );

                        FXMLHelper.getSelectedComicPanel().getLayout().setSize(
                                new Size(
                                        Integer.valueOf(widthField.getText()),
                                        Integer.valueOf(heightField.getText())
                                )
                        );

                        CMFile.cmfile.saved = false;
                    }
                    break;
                case "ComicModel":
                    FXMLHelper.getSelectedModel().getLayout().setPosition(
                            new Position(
                                    Integer.valueOf(xField.getText()),
                                    Integer.valueOf(yField.getText())
                            )
                    );

                    FXMLHelper.getSelectedModel().getLayout().setSize(
                            new Size(
                                    Integer.valueOf(widthField.getText()),
                                    Integer.valueOf(heightField.getText())
                            )
                    );

                    FXMLHelper.getSelectedModel().getCanvas().setLayoutX(MM.toPx(Integer.valueOf(xField.getText())));
                    FXMLHelper.getSelectedModel().getCanvas().setLayoutY(MM.toPx(Integer.valueOf(yField.getText())));

                    CMFile.cmfile.saved = false;

                    break;
            }

            EditorController.controller.redrawEditorPane();
        }
    }

    @FXML
    public void menuButtonClick ()
    {
        if (!menuOpened)
        {
            try
            {
                menuPane = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
                pane.getChildren().add(menuPane);
                menuOpened = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            pane.getChildren().remove(menuPane);
            menuOpened = false;
        }
    }

    @FXML
    public void previousPageClick ()
    {
        if (CMFile.cmfile.currentPage > 0)
        {
            selectPage(CMFile.cmfile.book.getPages().get(CMFile.cmfile.currentPage - 1));
        }
    }

    @FXML
    public void nextPageClick ()
    {
        if (CMFile.cmfile.currentPage < CMFile.cmfile.book.getPages().size() - 1)
        {
            selectPage(CMFile.cmfile.book.getPages().get(CMFile.cmfile.currentPage + 1));
        }
    }

    public void selectPage (ComicPage page)
    {
        EditorController.controller.showPage(page);

        currentPageLabel.setText(String.valueOf(CMFile.cmfile.currentPage + 1));
        componentsTree.getSelectionModel().select(
                componentsTree.getRoot().getChildren().get(CMFile.cmfile.currentPage)
        );
    }

    @FXML
    public void componentsTreeClick (MouseEvent e)
    {
        if (e.getButton() == MouseButton.PRIMARY)
        {
            hideComponentsTreeRightClick();

            switch (FXMLHelper.getClassOfSelectedObject().getSimpleName())
            {
                case "ComicPage":
                    selectPage(FXMLHelper.getSelectedComicPage());
                    measurePane.setVisible(false);
                    break;
                case "ComicPanel":
                    EditorController.controller.showPage(FXMLHelper.getComicPageOfSelectedComicPanel());
                    alimentateMeasurePane(FXMLHelper.getSelectedComicPanel());

                    CanvasHelper.selectPanel(
                            ((Pane) EditorController.controller.editorPane.getChildren().get(FXMLHelper.getComicPageOfSelectedComicPanel().getPanels().indexOf(FXMLHelper.getSelectedComicPanel())))
                    );
                    break;
                case "ComicModel":
                    alimentateMeasureModel(FXMLHelper.getSelectedModel());
                    break;
            }
        }

        if (e.getButton() == MouseButton.SECONDARY)
        {
            switch (FXMLHelper.getClassOfSelectedObject().getSimpleName())
            {
                case "ComicPage":
                    showPageRightClick("rightClickPage", e.getX(), e.getY());
                    break;
                case "ComicPanel":
                    showPageRightClick("rightClickPanel", e.getX(), e.getY());
                    break;
                default:
                    switch (FXMLHelper.getClassOfSelectedObject().getSimpleName())
                    {
                        case "ComicModel":
                        case "Balloon":
                            showPageRightClick("rightClickModel", e.getX(), e.getY());
                            break;
                        default:
                            showPageRightClick("rightClickBook", e.getX(), e.getY());
                            break;
                    }

                    break;
            }
        }
    }

    public void showPageRightClick (String fxml, double x, double y)
    {
        try
        {
            hideComponentsTreeRightClick();

            rightClickBox = FXMLLoader.load(getClass().getResource(String.format("/fxml/%s.fxml", fxml)));
            rightClickBox.setLayoutX(0);
            rightClickBox.setLayoutY(y + 50);

            pane.getChildren().add(rightClickBox);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void hideComponentsTreeRightClick ()
    {
        if (rightClickBox != null)
        {
            pane.getChildren().remove(rightClickBox);
            rightClickBox = null;
        }
    }
}