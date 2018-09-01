package org.comicteam.annotations;

import org.comicteam.helpers.*;

public class LanguageProcessor
{
    public static void language (Class c)
    {
        if (c.isAnnotationPresent(Language.class))
        {
            LanguageHelper.addLanguage(c);
        }
    }
}