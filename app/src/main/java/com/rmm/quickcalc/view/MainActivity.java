package com.rmm.quickcalc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmm.quickcalc.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Teorically for (Build.VERSION.SDK_INT < 16)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        ImageView iv_Menu = findViewById(R.id.ivMenu);
        iv_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSettingsActivity();
            }
        });
    }

    void goSettingsActivity ()
    {
        Intent intent = new Intent (this, CalcSettingsActivity.class);
        startActivity (intent);
    }
}
