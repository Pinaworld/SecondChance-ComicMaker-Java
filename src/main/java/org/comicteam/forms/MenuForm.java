package org.comicteam.forms;

import java.io.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class MenuForm extends Application
{

    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setX(50);
        primaryStage.setY(100);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.showAndWait();
    }
}
