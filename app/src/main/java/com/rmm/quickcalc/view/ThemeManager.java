package com.rmm.quickcalc.view;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.graphics.drawable.IconCompat;

import com.rmm.quickcalc.R;

public abstract class ThemeManager {

    public enum Themes { RASPBERRY, BLUEBERRY, STRAWBERRY }
    private static Themes mCurrentTheme = Themes.RASPBERRY;     // Just noticing the theme manager that this is the current theme (The same as in AndroidManifest)

    public static void setCurrentTheme (Themes theme)
    {
        mCurrentTheme = theme;
    }

    public static int getCurrentThemeResource ()
    {
        int themeResource;
        switch (mCurrentTheme)
        {
            default:
            case RASPBERRY:
                themeResource = R.style.ThemeRaspberry;
                break;
            case BLUEBERRY:
                themeResource = R.style.ThemeBlueberry;
                break;
            case STRAWBERRY:
                themeResource = R.style.ThemeStrawberry;
                break;
        }

        return themeResource;
    }
}
