package com.rmm.quickcalc.view;

import com.rmm.quickcalc.data.CalculatorModel;
import com.rmm.quickcalc.data.CalculatorSupporter;
import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;
import com.rmm.quickcalc.data.ExpressionAnalizer;
import com.rmm.quickcalc.data.InvalidExpressionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Roberto
 * Class to handle the presenter of CalculatorActivity using MVP
 */
public class CalculatorPresenter implements ICalculator.Presenter {

    private ICalculator.View mView;
    private ICalculator.Model mModel;

    private boolean lastKeyPressedEquals;

    public CalculatorPresenter (ICalculator.View view, HashMap<EOperators, String> operators)
    {
        mView = view;
        mModel = new CalculatorModel(this, operators);

        lastKeyPressedEquals = false;
    }

    /**
     * Clears the display of the view.
     */
    @Override
    public void onCreate() {
        mView.clearDisplay();
    }

    /**
     * Change the activity to the settings one.
     */
    @Override
    public void onMenuPressed() {
        mView.goSettingsActivity();
    }

    /**
     * Appends the number pressed to the current expression in the display.
     * If display is currently showing a 0, it will be replaced by the number instead of appended.
     * @param n The number that was pressed.
     */
    @Override
    public void onNumberPressed(int n) {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        if (mView.isClearedDisplay() || lastKeyPressedEquals)
            mView.setDisplayData (String.valueOf (n));
        else
            mView.appendDisplayData(String.valueOf (n));

        lastKeyPressedEquals = false;
    }

    /**
     * Clears the display.
     */
    @Override
    public void onACPressed() {
        mView.clearDisplay();

        lastKeyPressedEquals = false;
    }

    /**
     * Appends a dot in the display. This method checks if the last term in the expression has already a dot or not.
     */
    @Override
    public void onDotPressed() {

        String displayData = mView.getDisplayData();

        if (displayData.equals("error"))
            mView.clearDisplay();

        String lastElement = String.valueOf ( mView.getLastElementDisplayData() );

        if (lastElement.equals("."))
        {
            return;
        }
        else if (mModel.isOperator (lastElement)) // Its an operator
        {
            mView.appendDisplayData("0.");
        }
        else    // Its a number
        {
            // Check if the full number already has a dot

            Expression expression = ExpressionAnalizer.extract(displayData);

            if (expression == null)
                return;

            ArrayList<String> terms = expression.getTerms();
            String number = terms.get (terms.size() - 1);

            if (!number.contains("."))
                mView.appendDisplayData(".");
        }

        lastKeyPressedEquals = false;
    }

    /**
     * Calls the model to process the current expression in the display.
     * This method checks if the display is showing a 0 or the expression is not valid.
     */
    @Override
    public void onOperatorEqualsPressed() {

        // If input is only a 0, just do nothing
        if (mView.isClearedDisplay())
            return;

        // Getting the display expression
        String displayData = mView.getDisplayData();

        if (displayData.equals("error"))
            mView.clearDisplay();

        Expression expression = ExpressionAnalizer.extract(displayData);
        if (expression == null)
            return;

        // Here we are sure we have at least two terms, so we will have to operate them.
        try {
            double resultDouble = mModel.processExpression(expression);

            double result = CalculatorSupporter.getDoubleDecimalsPrecision(resultDouble, 3);

            String resultStr = "";

            if (CalculatorSupporter.canBeInteger(result))
                resultStr = String.valueOf(Math.round(result));
            else
                resultStr = String.valueOf(result);

            mView.setDisplayData(resultStr);

        } catch (InvalidExpressionException e)
        {
            mView.setDisplayData("error");
        }

        lastKeyPressedEquals = true;
    }

    /**
     * Appends a sum operator to the expression.
     * If the last element of the display is an operator it will be replaced by the sum one.
     */
    @Override
    public void onOperatorSumPressed() {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.SUM) );
        // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.SUM) );

        lastKeyPressedEquals = false;
    }

    /**
     * Appends a minus operator to the expression.
     * If the last element of the display is an operator it will be replaced by the minus one.
     */
    @Override
    public void onOperatorMinusPressed() {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MINUS) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MINUS) );

        lastKeyPressedEquals = false;
    }

    /**
     * Appends a multiplication operator to the expression.
     * If the last element of the display is an operator it will be replaced by the multiplication one.
     */
    @Override
    public void onOperatorMultiplicationPressed() {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MULTIPLICATION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MULTIPLICATION) );

        lastKeyPressedEquals = false;
    }

    /**
     * Appends a division operator to the expression.
     * If the last element of the display is an operator it will be replaced by the division one.
     */
    @Override
    public void onOperatorDivisionPressed() {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.DIVISION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.DIVISION) );

        lastKeyPressedEquals = false;
    }

    /**
     * Removes the last element of the display.
     */
    @Override
    public void onBackPressed() {

        if (mView.getDisplayData().equals("error"))
            mView.clearDisplay();

        if (!mView.isClearedDisplay())
            mView.removeLastElementDisplay();

        lastKeyPressedEquals = false;
    }
}
