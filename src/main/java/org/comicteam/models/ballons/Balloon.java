package org.comicteam.models.ballons;

import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.ballons.pointers.*;

import javafx.scene.canvas.*;

public abstract class Balloon extends ComicModel
{
    protected Text text;
    protected BalloonPointer pointer;

    public Balloon (ComicPanel comicPanel, Canvas canvas, ComicLayout layout, int background, Text text, BalloonPointer pointer)
    {
        super(text.getText(), canvas, comicPanel, layout, background);
        this.text = text;
        this.pointer = pointer;
    }

    public Text getText ()
    {
        return text;
    }

    public void setText (Text text)
    {
        this.text = text;
    }

    public BalloonPointer getPointer ()
    {
        return pointer;
    }

    public void setPointer (BalloonPointer pointer)
    {
        this.pointer = pointer;
    }
}
