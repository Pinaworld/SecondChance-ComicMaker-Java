package org.comicteam.controllers;

import java.nio.file.*;

import org.comicteam.annotations.*;
import org.comicteam.helpers.*;
import org.comicteam.plugins.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class PluginsController
{
    @FXML
    public Label pluginNameLabel;
    @FXML
    public Label pluginVersionLabel;
    @FXML
    public Label pluginDescriptionLabel;
    @FXML
    private ListView pluginsList;
    @Translate
    @FXML
    public Button addPluginButton;
    @Translate
    @FXML
    public Button deletePluginButton;

    public void initialize ()
    {
        TranslateProcessor.translate(PluginsController.class, this);

        pluginsList.getItems().clear();

        for (Class<?> c : PluginHelper.languages)
        {
            try
            {
                pluginsList.getItems().add(c.newInstance());
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        for (Class<?> c : PluginHelper.plugins)
        {
            try
            {
                pluginsList.getItems().add(c.newInstance());
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        if (pluginsList.getItems().size() > 0)
        {
            pluginsList.getSelectionModel().select(0);
            Plugin p = (Plugin) pluginsList.getSelectionModel().getSelectedItem();

            pluginNameLabel.setText(p.getName());
            pluginVersionLabel.setText(p.getVersion());
            pluginDescriptionLabel.setText(p.getDescription());
        }
        else
        {
            pluginNameLabel.setText("");
            pluginVersionLabel.setText("");
            pluginDescriptionLabel.setText("");
        }

        pluginsList.setOnMouseClicked(e -> {
            selectPlugin();
        });

        pluginsList.setOnKeyPressed(e -> {
            selectPlugin();
        });
    }

    public void selectPlugin ()
    {
        Plugin p = (Plugin) pluginsList.getSelectionModel().getSelectedItem();

        pluginNameLabel.setText(p.getName());
        pluginVersionLabel.setText(p.getVersion());
        pluginDescriptionLabel.setText(p.getDescription());
    }

    @FXML
    public void addPluginButtonClick ()
    {
        FileChooser chooser = new FileChooser();
        String filename = chooser.showOpenDialog(addPluginButton.getScene().getWindow()).getAbsolutePath();

        Path path = Paths.get(filename);

        PluginHelper.addPlugin(path);
        PluginHelper.loadInstalledPlugins();

        initialize();
    }

    @FXML
    public void deletePluginButtonClick ()
    {
        Plugin p = (Plugin) pluginsList.getSelectionModel().getSelectedItem();

        PluginHelper.deletePlugin(p);

        initialize();
    }
}
