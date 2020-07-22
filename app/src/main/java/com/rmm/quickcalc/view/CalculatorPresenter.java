package com.rmm.quickcalc.view;

import android.util.Log;

import com.rmm.quickcalc.data.CalculatorModel;
import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;

import java.util.ArrayList;
import java.util.HashMap;;

public class CalculatorPresenter implements ICalculator.Presenter {

    private ICalculator.View mView;
    private ICalculator.Model mModel;

    boolean lastKeyPressedEquals;

    public CalculatorPresenter (ICalculator.View view, HashMap<EOperators, String> operators)
    {
        mView = view;
        mModel = new CalculatorModel(this, operators);

        lastKeyPressedEquals = false;
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

        if (mView.isClearedDisplay() || lastKeyPressedEquals)
            mView.setDisplayData (String.valueOf (n));
        else
            mView.appendDisplayData(String.valueOf (n));

        lastKeyPressedEquals = false;
    }

    @Override
    public void onACPressed() {
        mView.clearDisplay();

        lastKeyPressedEquals = false;
    }

    @Override
    public void onDotPressed() {

        String displayData = mView.getDisplayData();

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

            Expression expression = new Expression (displayData);
            expression.extract();

            if (expression.getStatus() != Expression.Status.READY)
                return;

            ArrayList<String> terms = expression.getTerms();
            String number = terms.get (terms.size() - 1);

            if (!number.contains("."))
                mView.appendDisplayData(".");
        }

        lastKeyPressedEquals = false;
    }

    @Override
    public void onOperatorEqualsPressed() {

        // If input is only a 0, just do nothing
        if (mView.isClearedDisplay())
            return;

        // Getting the display expresion
        String displayData = mView.getDisplayData();

        Expression expression = new Expression (displayData);
        expression.extract();

        if (expression.getStatus() != Expression.Status.READY)
            return;

        // Here we are sure we have at least two terms, so we will have to operate them.
        String res = mModel.processExpression(expression);

        mView.setDisplayData(res);

        lastKeyPressedEquals = true;
    }

    @Override
    public void onOperatorSumPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.SUM) );
        // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.SUM) );

        lastKeyPressedEquals = false;
    }

    @Override
    public void onOperatorMinusPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MINUS) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MINUS) );

        lastKeyPressedEquals = false;
    }

    @Override
    public void onOperatorMultiplicationPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MULTIPLICATION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MULTIPLICATION) );

        lastKeyPressedEquals = false;
    }

    @Override
    public void onOperatorDivisionPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.DIVISION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.DIVISION) );

        lastKeyPressedEquals = false;
    }

    @Override
    public void onBackPressed() {

        if (!mView.isClearedDisplay())
            mView.removeLastElementDisplay();

        lastKeyPressedEquals = false;
    }
}
