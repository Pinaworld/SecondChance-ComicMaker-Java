package org.comicteam.models;

import org.comicteam.layouts.*;

import javafx.scene.canvas.*;
import javafx.scene.text.*;

public final class Text extends ComicModel
{
    private String text;
    private Font font;

    public Text (ComicPanel comicPanel, Canvas canvas, ComicLayout layout, int background, String text, Font font)
    {
        super(text, canvas, comicPanel, layout, background);
        this.text = text;
        this.font = font;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public Font getFont ()
    {
        return font;
    }

    public void setFont (Font font)
    {
        this.font = font;
    }
}
