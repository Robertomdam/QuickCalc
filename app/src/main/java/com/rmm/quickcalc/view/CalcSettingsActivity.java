package com.rmm.quickcalc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rmm.quickcalc.R;

/**
 * @author Roberto
 * Activity that represents the settings of the app.
 */
public class CalcSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spThemes;

    /**
     * Find the necessary views of the layout.
     * Creates the spinner and populates it with the data of the array_themes.xml resource.
     * Sets the style of the spinner items.
     * Handles the onItemSelected events of the spinner and the onCLick of the back button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_settings);

        spThemes = findViewById(R.id.spThemes);
        ImageView ivBack = findViewById(R.id.ivBack);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource (this, R.array.themes, R.layout.spinner_themes);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_items_themes);
        spThemes.setAdapter(spinnerAdapter);

        spThemes.setOnItemSelectedListener(this);

        final Context c = this;
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, CalculatorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        spThemes.setSelection(ThemeManager.getCurrentThemeIndex());
    }

    /**
     * Sets the new theme to the ThemeManager and notifies to the user
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        Toast.makeText(this, "Themes has been changed successfully", Toast.LENGTH_SHORT).show();

        switch (i)
        {
            case 0:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.RASPBERRY );
                ThemeManager.save (getApplicationContext());
            break;

            case 1:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.BLUEBERRY );
                ThemeManager.save (getApplicationContext());
            break;

            case 2:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.STRAWBERRY );
                ThemeManager.save (getApplicationContext());
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
