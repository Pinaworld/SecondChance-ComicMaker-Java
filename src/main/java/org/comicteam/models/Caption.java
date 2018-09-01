package org.comicteam.models;

import org.comicteam.layouts.*;
import org.comicteam.models.ballons.*;
import org.comicteam.models.ballons.pointers.*;

import javafx.scene.canvas.*;

public final class Caption extends Balloon
{
    public Caption (ComicPanel comicPanel, Canvas canvas, ComicLayout layout, int background, Text text, BalloonPointer pointer)
    {
        super(comicPanel, canvas, layout, background, text, pointer);
    }
}
