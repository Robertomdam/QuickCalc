package com.rmm.quickcalc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rmm.quickcalc.R;

public class CalcSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private boolean hasCreated = false; // To avoid the item selected callback at the beginning
    private Spinner spThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_settings);

        hasCreated = true;

        spThemes = findViewById(R.id.spThemes);
        ImageView ivBack = findViewById(R.id.ivBack);

//        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource (this, R.array.themes, android.R.layout.simple_spinner_dropdown_item);
//        spThemes.setAdapter(spinnerAdapter);


        spThemes.setSelection(0);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (hasCreated) {
            hasCreated = false;
            return;
        }

        Toast.makeText(this, "Selected: " + i, Toast.LENGTH_SHORT).show();

        switch (i)
        {
            case 0:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.RASPBERRY );
            break;

            case 1:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.BLUEBERRY );
            break;

            case 2:
                ThemeManager.setCurrentTheme( ThemeManager.Themes.STRAWBERRY );
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
