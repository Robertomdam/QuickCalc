package com.rmm.quickcalc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmm.quickcalc.R;
import com.rmm.quickcalc.data.EOperators;

import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.SSLEngineResult;

public class CalculatorActivity extends AppCompatActivity implements ICalculator.View {

    // MVP
    private ICalculator.Presenter mPresenter;

    // Views
    private ImageView ivMenu;
    private TextView tvDisplay;

    private ArrayList<Button> btNumbers;

    private Button btAC;
    private Button btDot;
    private Button btEquals;
    private Button btSum;
    private Button btMinus;
    private Button btMultiplication;
    private Button btDivision;
    private ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setFullscreen ();

        findAllViews();
        setupOnClickListeners();

        HashMap<EOperators, String> operators = new HashMap<>();

        operators.put (EOperators.EQUALS        , getResources().getString(R.string.key_Equals          ));
        operators.put (EOperators.SUM           , getResources().getString(R.string.key_Sum             ));
        operators.put (EOperators.MINUS         , getResources().getString(R.string.key_Minus           ));
        operators.put (EOperators.MULTIPLICATION, getResources().getString(R.string.key_Multiplication  ));
        operators.put (EOperators.DIVISION      , getResources().getString(R.string.key_Division        ));

        mPresenter = new CalculatorPresenter (this, operators);
        mPresenter.onCreate();
    }

    void findAllViews ()
    {
        // Menu
        ivMenu = findViewById(R.id.ivMenu);

        // Display
        tvDisplay = findViewById(R.id.tvDisplay);

        // Numbers
        btNumbers = new ArrayList<Button>();
        btNumbers.add( (Button) findViewById(R.id.button0) );
        btNumbers.add( (Button) findViewById(R.id.button1) );
        btNumbers.add( (Button) findViewById(R.id.button2) );
        btNumbers.add( (Button) findViewById(R.id.button3) );
        btNumbers.add( (Button) findViewById(R.id.button4) );
        btNumbers.add( (Button) findViewById(R.id.button5) );
        btNumbers.add( (Button) findViewById(R.id.button6) );
        btNumbers.add( (Button) findViewById(R.id.button7) );
        btNumbers.add( (Button) findViewById(R.id.button8) );
        btNumbers.add( (Button) findViewById(R.id.button9) );

        // Operators
        btAC                = findViewById(R.id.buttonAC);
        btDot               = findViewById(R.id.buttonDot);
        btEquals            = findViewById(R.id.buttonEquals);
        btSum               = findViewById(R.id.buttonSum);
        btMinus             = findViewById(R.id.buttonMinus);
        btMultiplication    = findViewById(R.id.buttonMultiply);
        btDivision          = findViewById(R.id.buttonDivide);
        btBack              = findViewById(R.id.buttonBack);
    }

    void setupOnClickListeners ()
    {
        // Menu
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onMenuPressed();
            }
        });

        // Numbers

        for (int i = 0; i < btNumbers.size(); i++)
        {
            final Button bt = btNumbers.get(i);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onNumberPressed (Integer.parseInt (bt.getText().toString()));
                }
            });
        }

        // Operators
        btAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onACPressed();
            }
        });

        btEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onOperatorEqualsPressed();
            }
        });

        btSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onOperatorSumPressed();
            }
        });

        btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onOperatorMinusPressed();
            }
        });

        btMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onOperatorMultiplicationPressed();
            }
        });

        btDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onOperatorDivisionPressed();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onBackPressed();
            }
        });

        btDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onDotPressed();
            }
        });
    }

    void setFullscreen ()
    {
        // Teorically for (Build.VERSION.SDK_INT < 16)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void goSettingsActivity() {
        Intent intent = new Intent (this, CalcSettingsActivity.class);
        startActivity (intent);
    }

    @Override
    public String getDisplayData() {
        return tvDisplay.getText().toString();
    }

    @Override
    public char getLastElementDisplayData() {
        return tvDisplay.getText().charAt (tvDisplay.getText().length() - 1);
    }

    @Override
    public void setDisplayData (String data) {
        tvDisplay.setText (data);
    }

    @Override
    public void replaceLastElementDisplay(String data) {
        removeLastElementDisplay();
        appendDisplayData (data);
    }

    @Override
    public void appendDisplayData (String data) {
        tvDisplay.setText (
            tvDisplay.getText().toString().concat(data)
        );
    }

    @Override
    public void removeLastElementDisplay() {
        String currentString = tvDisplay.getText().toString();

        tvDisplay.setText(
            currentString.toString().substring(0, currentString.length() - 1)
        );

        if (tvDisplay.getText().toString().isEmpty())
            clearDisplay();
    }

    @Override
    public void clearDisplay () {
        tvDisplay.setText("0");
    }

    @Override
    public boolean isClearedDisplay() {
        return (tvDisplay.getText().toString().equals("0"));
    }
}
