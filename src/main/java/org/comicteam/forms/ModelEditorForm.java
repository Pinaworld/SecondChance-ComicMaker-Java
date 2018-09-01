package org.comicteam.forms;

import java.io.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class ModelEditorForm extends Application
{
    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/modeleditor.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Model Editor");

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/modeleditor.png"))));

        primaryStage.show();
    }
}