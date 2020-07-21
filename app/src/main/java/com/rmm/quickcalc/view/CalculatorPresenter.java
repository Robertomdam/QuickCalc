package com.rmm.quickcalc.view;

import com.rmm.quickcalc.data.CalculatorModel;
import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;

import java.util.ArrayList;
import java.util.HashMap;;

public class CalculatorPresenter implements ICalculator.Presenter {

    private ICalculator.View mView;
    private ICalculator.Model mModel;

    public CalculatorPresenter (ICalculator.View view, HashMap<EOperators, String> operators)
    {
        mView = view;
        mModel = new CalculatorModel(this, operators);
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

    }

    @Override
    public void onOperatorEqualsPressed() {

    }

    @Override
    public void onOperatorSumPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.SUM) );
        // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.SUM) );
    }

    @Override
    public void onOperatorMinusPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MINUS) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MINUS) );

    }

    @Override
    public void onOperatorMultiplicationPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.MULTIPLICATION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.MULTIPLICATION) );

    }

    @Override
    public void onOperatorDivisionPressed() {

        // If last element is an operator, replace by the pressed one
        if (mModel.isOperator(mView.getLastElementDisplayData()))
            mView.replaceLastElementDisplay ( mModel.getOperator (EOperators.DIVISION) );
            // If it is a number, it appends the operator
        else
            mView.appendDisplayData ( mModel.getOperator (EOperators.DIVISION) );

    }

    @Override
    public void onBackPressed() {

        if (!mView.isClearedDisplay())
            mView.removeLastElementDisplay();
    }
//
//    private String extractLastTermFromExpression (char[] data) {
//
//        String strData = Arrays.toString (data);
//
//        for (int i = data.length - 1; i >= 0 ; i--) {
//
//            // If there exists use the whole number from this operator to the end of the string.
//            if (isOperator(data[i]))
//            {
//                if (i == data.length - 1)
//                    return "0";
//                else
//                    return strData.substring (i + 1, strData.length());
//            }
//        }
//
//        return "";
//    }
}
