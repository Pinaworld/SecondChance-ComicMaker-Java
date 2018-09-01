package org.comicteam.forms;

import java.io.*;

import org.comicteam.*;
import org.comicteam.controllers.*;
import org.comicteam.helpers.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class EditorForm extends Application
{
    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/editor.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(
                root,
                MM.toPx(CMFile.cmfile.book.getSize().getHorizontal()),
                MM.toPx(CMFile.cmfile.book.getSize().getVertical())
        );

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle(CMFile.cmfile.book.getName());
        primaryStage.setX(400);

        primaryStage.setOnCloseRequest(e -> {
            if (!CMFile.cmfile.saved)
            {
                FXMLHelper.openSavingWarningForm();

                if (SavingWarningController.mustCancel)
                {
                    e.consume();
                }
            }
            else
            {
                FXMLHelper.closeWindow(WorkingController.controller.pane);
            }
        });

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/logo.png"))));

        primaryStage.show();
    }
}