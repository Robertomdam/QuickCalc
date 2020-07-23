package com.rmm.quickcalc.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * @author Roberto
 * Activity that represents a calculator. It is managed using MVP pattern, representing the view.
 */
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

    /**
     * Accomplishes several task that have to be done once at the beginning of the app.
     * Maps the operators enum with the ones defined in the strings.xml resource.
     * Instantiates the presenter of the MVP.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * As you can notice, there in no set content view method in the onCreate. This is
         * because i'm handleling several themes in the app and the only way I have accomplished
         * to make it works correctly (keeping the regular navigation with back button active) is
         * to take some part of the code from the onCreate to onResume. I'm aware of these will
         * be kind of kill performance (There are some findViewById methods also) but since the
         * scope of the app is just to be a simple calculator I think that it will be fine.
         */

        HashMap<EOperators, String> operators = new HashMap<>();

        operators.put (EOperators.EQUALS        , getResources().getString(R.string.key_Equals          ));
        operators.put (EOperators.SUM           , getResources().getString(R.string.key_Sum             ));
        operators.put (EOperators.MINUS         , getResources().getString(R.string.key_Minus           ));
        operators.put (EOperators.MULTIPLICATION, getResources().getString(R.string.key_Multiplication  ));
        operators.put (EOperators.DIVISION      , getResources().getString(R.string.key_Division        ));

        mPresenter = new CalculatorPresenter (this, operators);
    }

    /**
     * Sets the theme of the activity depending on the current one in the ThemeManager.
     * This provokes the setContentView method to run here, every time the onResume is called
     * and also the same for the findAllViews calls.
     */
    @Override
    protected void onResume() {
        super.onResume();

        setTheme(ThemeManager.getCurrentThemeResource (getApplicationContext()));

        setContentView(R.layout.activity_calculator);
        setFullscreen ();

        findAllViews();
        setupOnClickListeners();

        mPresenter.onCreate();
    }

    /**
     * Find all the views in the layout and stores them in the corresponding variables.
     */
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

    /**
     * Setups the onCLickListeners of the views that need it.
     */
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

    /**
     * Sets the fullscreen mode in the activity.
     */
    void setFullscreen ()
    {
        // Teorically for (Build.VERSION.SDK_INT < 16)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Goes to the settings activity.
     */
    @Override
    public void goSettingsActivity() {
        Intent intent = new Intent (this, CalcSettingsActivity.class);
        startActivity (intent);
    }

    /**
     * Retrieves the content of the display.
     * @return The content of the display.
     */
    @Override
    public String getDisplayData() {
        return tvDisplay.getText().toString();
    }

    /**
     * Retrieves the last character of the display.
     * @return The last character of the display.
     */
    @Override
    public char getLastElementDisplayData() {
        return tvDisplay.getText().charAt (tvDisplay.getText().length() - 1);
    }

    /**
     * Sets the display content.
     * @param data The data to be shown in the display.
     */
    @Override
    public void setDisplayData (String data) {
        tvDisplay.setText (data);
    }

    /**
     * Replaces the last character of the display.
     * @param data The new character to be used for replace the last one in the display.
     */
    @Override
    public void replaceLastElementDisplay(String data) {
        removeLastElementDisplay();
        appendDisplayData (data);
    }

    /**
     * Appends some data in the display.
     * @param data The data that have to be appended in the display.
     */
    @Override
    public void appendDisplayData (String data) {
        tvDisplay.setText (
            tvDisplay.getText().toString().concat(data)
        );
    }

    /**
     * Removes the last character of the display.
     */
    @Override
    public void removeLastElementDisplay() {
        String currentString = tvDisplay.getText().toString();

        tvDisplay.setText(
            currentString.toString().substring(0, currentString.length() - 1)
        );

        if (tvDisplay.getText().toString().isEmpty())
            clearDisplay();
    }

    /**
     * Clears the data of the display.
     */
    @Override
    public void clearDisplay () {
        tvDisplay.setText("0");
    }

    /**
     * Checks if the data of the display is 0 or not.
     * @return Whether the data in the display is 0 or not.
     */
    @Override
    public boolean isClearedDisplay() {
        return (tvDisplay.getText().toString().equals("0"));
    }
}
