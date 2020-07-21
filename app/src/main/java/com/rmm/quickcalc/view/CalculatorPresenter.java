package com.rmm.quickcalc.view;

import android.util.Log;

import com.rmm.quickcalc.data.CalculatorManager;
import com.rmm.quickcalc.data.EOperators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;;

public class CalculatorPresenter implements ICalculator.Presenter {

    private ICalculator.View mView;
    private ICalculator.Model mModel;

    /**
     * This is a key-value list that contains the available operators in the calculator with its associated string (Got from R's resources)
     */
    HashMap<EOperators, String> mOperators;

    public CalculatorPresenter (ICalculator.View view, HashMap<EOperators, String> operators)
    {
        mOperators = operators;

        mView = view;
        mModel = new CalculatorManager(this);
    }

    @Override
    public void onCreate() {
        mView.clearDisplay();
    }

    @Override
    public void onMenuPressed() {
        mView.goSettingsActivity();
    }

    @Override
    public void onNumberPressed(int n) {

        if (mView.isClearedDisplay())
            mView.setDisplayData (String.valueOf (n));
        else
            mView.appendDisplayData(String.valueOf (n));
    }

    @Override
    public void onACPressed() {
        mView.clearDisplay();
    }

    @Override
    public void onDotPressed() {
        String displayData = mView.getDisplayData();

        String number = "";

        // Look for the last operator in the string (If any)
        number = extractLastTermFromExpression (displayData.toCharArray());

        // If none operator was found, its because there is only one number, so continue with it
        if (number.isEmpty())
            number = displayData;

        if (number.equals("0"))
            mView.appendDisplayData("0");

        // Check if the number already have a dot. If not, append the dot (Else do nothing)
        if (!number.contains("."))
            mView.appendDisplayData(".");
    }

    @Override
    public void onOperatorEqualsPressed() {

    }

    @Override
    public void onOperatorSumPressed() {

        // If last element is an operator, replace by the pressed one
        if (isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mOperators.get(EOperators.SUM) );
        // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mOperators.get(EOperators.SUM) );
    }

    @Override
    public void onOperatorMinusPressed() {

        // If last element is an operator, replace by the pressed one
        if (isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mOperators.get(EOperators.MINUS) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mOperators.get(EOperators.MINUS) );

    }

    @Override
    public void onOperatorMultiplicationPressed() {

        // If last element is an operator, replace by the pressed one
        if (isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mOperators.get(EOperators.MULTIPLICATION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mOperators.get(EOperators.MULTIPLICATION) );

    }

    @Override
    public void onOperatorDivisionPressed() {

        // If last element is an operator, replace by the pressed one
        if (isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mOperators.get(EOperators.DIVISION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mOperators.get(EOperators.DIVISION) );

    }

    @Override
    public void onBackPressed() {

        if (!mView.isClearedDisplay())
            mView.removeLastElementDisplay();
    }

    private boolean isOperator (char value)
    {
        return isOperator (String.valueOf(value));
    }

    private boolean isOperator (String value)
    {
        for (int i = 0; i < mOperators.size(); i++) {
            String strOperator = mOperators.get ( EOperators.values()[i] );    // Using 'i' since the key is a enum variable

            if (strOperator.equals (value))
                return true;
        }

        return false;
    }

    private String extractLastTermFromExpression (char[] data) {

        String strData = Arrays.toString (data);

        for (int i = data.length - 1; i >= 0 ; i--) {

            // If there exists use the whole number from this operator to the end of the string.
            if (isOperator(data[i]))
            {
                if (i == data.length - 1)
                    return "0";
                else
                    return strData.substring (i + 1, strData.length());
            }
        }

        return "";
    }
}
