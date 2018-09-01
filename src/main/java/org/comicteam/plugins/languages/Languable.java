package org.comicteam.plugins.languages;

import org.comicteam.plugins.*;

public interface Languable extends Pluginable
{
    String getTranslation (String name) throws NoSuchFieldException, IllegalAccessException;
}
