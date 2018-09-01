package org.comicteam.helpers;

import javax.imageio.*;

import java.awt.image.*;
import java.io.*;

import org.comicteam.controllers.*;
import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.ballons.*;

import javafx.embed.swing.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class CanvasHelper
{
    private static Border blackBorder;
    private static Border blueBorder;

    static
    {
        blackBorder = new Border(
                new BorderStroke(
                        Paint.valueOf("BLACK"),
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT
                )
        );

        blueBorder = new Border(
                new BorderStroke(
                        Paint.valueOf("BLUE"),
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT
                )
        );
    }

    public static Pane getPanel (ComicPanel panel)
    {
        Pane pane = new Pane();
        pane.setPrefWidth(MM.toPx(panel.getLayout().getSize().getHorizontal()));
        pane.setPrefHeight(MM.toPx(panel.getLayout().getSize().getVertical()));
        pane.setLayoutX(MM.toPx(panel.getLayout().getPosition().getHorizontal()));
        pane.setLayoutY(MM.toPx(panel.getLayout().getPosition().getVertical()));
        pane.setBorder(blackBorder);

        return pane;
    }

    public static void selectPanel (Pane panel)
    {
        unselectAllPanels();
        panel.setBorder(blueBorder);
    }

    public static void unselectAllPanels ()
    {
        for (Node p : EditorController.controller.editorPane.getChildren())
        {
            ((Pane) p).setBorder(blackBorder);
        }
    }

    public static Pane getBalloon (Balloon balloon)
    {
        switch (balloon.getClass().getSimpleName())
        {
            case "SpeechBalloon":
                return getSpeechBalloon(balloon);
            /*case "ThoughtBalloon":
                return getThoughtBalloon(balloon);*/
            case "Caption":
                return getCaption(balloon);
            default:
                return null;
        }
    }

    public static Pane getSpeechBalloon (Balloon balloon)
    {
        Pane pane = new Pane();
        pane.setLayoutX(balloon.getLayout().getPosition().getHorizontal());
        pane.setLayoutY(balloon.getLayout().getPosition().getVertical());
        pane.setPrefWidth(balloon.getLayout().getSize().getHorizontal());
        pane.setPrefHeight(balloon.getLayout().getSize().getVertical());

        Canvas canvas = new Canvas();

        canvas.getGraphicsContext2D().strokeOval(
                MM.toPx(balloon.getLayout().getPosition().getHorizontal()),
                MM.toPx(balloon.getLayout().getPosition().getVertical()),
                MM.toPx(balloon.getLayout().getSize().getHorizontal()),
                MM.toPx(balloon.getLayout().getSize().getVertical())
        );

        pane.getChildren().add(canvas);
        pane.getChildren().add(getText(balloon.getText()));
        /*canvas.getGraphicsContext2D().strokeText(
                balloon.getText().getText(),
                MM.toPx(balloon.getLayout().getPosition().getHorizontal()),
                MM.toPx(balloon.getLayout().getPosition().getVertical())
        );*/

        return pane;
    }

    public static Canvas getThoughtBalloon (Balloon balloon)
    {
        Canvas canvas = new Canvas(
                MM.toPx(balloon.getLayout().getSize().getHorizontal()),
                MM.toPx(balloon.getLayout().getSize().getVertical())
        );

        return canvas;
    }

    public static Pane getCaption (Balloon balloon)
    {
        Pane paneCaption = new Pane();
        paneCaption.setLayoutX(MM.toPx(balloon.getLayout().getPosition().getHorizontal()));
        paneCaption.setLayoutY(MM.toPx(balloon.getLayout().getPosition().getVertical()));
        paneCaption.setPrefWidth(MM.toPx(balloon.getLayout().getSize().getHorizontal() + 5));
        paneCaption.setPrefHeight(MM.toPx(balloon.getLayout().getSize().getVertical() + 5));

        paneCaption.getChildren().add(getText(balloon.getText()));

        /*canvas.getGraphicsContext2D().strokeRect(
                MM.toPx(balloon.getLayout().getPosition().getHorizontal()),
                MM.toPx(balloon.getLayout().getPosition().getVertical()),
                MM.toPx(balloon.getLayout().getSize().getHorizontal()),
                MM.toPx(balloon.getLayout().getSize().getVertical())
        );*/

        /*canvas.getGraphicsContext2D().strokeText(
                balloon.getText().getText(),
                MM.toPx(balloon.getLayout().getPosition().getHorizontal()),
                MM.toPx(balloon.getLayout().getPosition().getVertical() + 4)
        );

        canvas.getGraphicsContext2D().*/

        return paneCaption;
    }

    public static Canvas getText (Text text)
    {
        Canvas canvas = new Canvas();

        canvas.getGraphicsContext2D().setFont(text.getFont());
        canvas.getGraphicsContext2D().strokeText(
                text.getText(),
                0,
                0
        );

        return canvas;
    }

    public static void movePanel (Node node, ComicPanel panel, int x, int y)
    {
        node.setLayoutX(x);
        node.setLayoutY(y);

        panel.getLayout().setPosition(
                new Position(
                        MM.toMM(x),
                        MM.toMM(y)
                )
        );
    }

    public static void moveModel (Canvas canvas, ComicModel model, int x, int y)
    {
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);

        model.getLayout().setPosition(
                new Position(
                        MM.toMM(x),
                        MM.toMM(y)
                )
        );
    }

    public static void resizePanel (Node node, ComicPanel panel, int x, int y)
    {
        ((Pane) node).setPrefWidth(x);
        ((Pane) node).setPrefHeight(y);

        panel.getLayout().setSize(
                new Size(
                        MM.toMM(x),
                        MM.toMM(y)
                )
        );
    }

    public static void resizeModelPlus (Canvas canvas)
    {
        canvas.setScaleX(canvas.getScaleX() + 0.01);
        canvas.setScaleY(canvas.getScaleY() + 0.01);

    }

    public static void resizeModelMinus (Canvas canvas)
    {
        canvas.setScaleX(canvas.getScaleX() - 0.01);
        canvas.setScaleY(canvas.getScaleY() - 0.01);
    }


    public static ByteArrayOutputStream writeCanvasFile (Canvas canvas)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, wi);
        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);

        try
        {
            ImageIO.write(
                    ri,
                    "png",
                    baos
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return baos;
    }
}