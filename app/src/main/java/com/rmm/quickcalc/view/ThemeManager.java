package com.rmm.quickcalc.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.rmm.quickcalc.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Roberto
 * Themes manager to handle the different themes of the app. It's a none instantiable class, so all its methods will be accessed staticly
 */
public abstract class ThemeManager {

    private static String SHARED_PREFERENCES_NAME = "sharedPref";
    private static String SHARED_PREFERENCES_THEME_KEY = "themes";

    /**
     * The themes available in the app
     */
    public enum Themes { RASPBERRY, BLUEBERRY, STRAWBERRY }
    private static int mCurrentTheme = R.style.ThemeRaspberry;     // Just noticing the theme manager that this is the current theme (The same as in AndroidManifest)

    /**
     * Notifies the manager about which is the current theme of the app.
     * @param theme The current theme of the app.
     */
    public static void setCurrentTheme (Themes theme)
    {
        switch (theme)
        {
            default:
            case RASPBERRY:
                mCurrentTheme = R.style.ThemeRaspberry;
                break;
            case BLUEBERRY:
                mCurrentTheme = R.style.ThemeBlueberry;
                break;
            case STRAWBERRY:
                mCurrentTheme = R.style.ThemeStrawberry;
                break;
        }
    }

    /**
     * Retrieves the current theme of the app.
     * @return The current theme of the app (R.style.).
     */
    public static int getCurrentThemeResource (Context appContext)
    {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt (SHARED_PREFERENCES_THEME_KEY, mCurrentTheme);
    }

    /**
     * Saves the current state into shared preferences file.
     * If this method is not called after changing the theme, the app wont have the new theme
     * when it is started the next time.
     * @param appContext The context of the application.
     */
    public static void save (Context appContext)
    {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt (SHARED_PREFERENCES_THEME_KEY, mCurrentTheme).apply();
    }

    /**
     * Retrieves the index of the current theme.
     * @return The index of the current theme.
     */
    public static int getCurrentThemeIndex ()
    {
        int index = 0;
        switch (mCurrentTheme)
        {
            default:
            case R.style.ThemeRaspberry:
                index = 0;
                break;

            case R.style.ThemeBlueberry:
                index = 1;
                break;

            case R.style.ThemeStrawberry:
                index = 2;
                break;
        }

        return index;
    }
}
