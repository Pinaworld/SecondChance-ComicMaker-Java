package org.comicteam.models.ballons;

import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.ballons.pointers.*;

import javafx.scene.canvas.*;

public final class ThoughtBalloon extends Balloon
{
    public ThoughtBalloon (ComicPanel comicPanel, Canvas canvas, ComicLayout layout, int background, Text text, BalloonPointer pointer)
    {
        super(comicPanel, canvas, layout, background, text, pointer);
    }
}
