package org.comicteam.models;

import org.comicteam.layouts.*;

import javafx.scene.canvas.*;
import javafx.scene.layout.*;

public class ComicModel extends Region
{
    protected String name;
    protected Canvas canvas;
    protected ComicPanel comicPanel;
    protected ComicLayout layout;
    protected int bg;

    public ComicModel (String name, Canvas canvas, ComicPanel comicPanel, ComicLayout layout, int background)
    {
        this.name = name;
        this.canvas = canvas;
        this.comicPanel = comicPanel;
        this.layout = layout;
        this.bg = background;
    }

    public ComicPanel getComicPanel ()
    {
        return comicPanel;
    }

    public void setComicPanel (ComicPanel comicPanel)
    {
        this.comicPanel = comicPanel;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Canvas getCanvas ()
    {
        return canvas;
    }

    public void setCanvas (Canvas canvas)
    {
        this.canvas = canvas;
    }

    public ComicLayout getLayout ()
    {
        return layout;
    }

    public void setLayout (ComicLayout layout)
    {
        this.layout = layout;
    }

    public int getBG ()
    {
        return bg;
    }

    public void setBG (int background)
    {
        this.bg = background;
    }

    @Override
    public String toString ()
    {
        return name;
    }
}
