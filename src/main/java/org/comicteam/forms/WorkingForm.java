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

public class WorkingForm extends Application
{

    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/working.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(
                root,
                400,
                Screen.getPrimary().getBounds().getHeight()
        );

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle(CMFile.cmfile.book.getName());
        primaryStage.setX(0);

        primaryStage.setOnCloseRequest(e -> {
            if (!CMFile.cmfile.saved)
            {
                FXMLHelper.openSavingWarningForm();
            }
            else
            {
                FXMLHelper.closeAllWindows(WorkingController.controller.pane);
            }
        });

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/logo.png"))));

        primaryStage.show();
    }
}
