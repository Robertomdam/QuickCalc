package com.rmm.quickcalc.view;

import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;
import com.rmm.quickcalc.data.InvalidExpressionException;

/**
 * @author Roberto
 * Interface to implement MVP in Calculator layout.
 */
public interface ICalculator {

    public interface Model
    {
        boolean isOperator (char value);
        boolean isOperator (String value);

        String getOperator (EOperators operator);

        double processExpression (Expression validExpression) throws InvalidExpressionException;
    }

    public interface Presenter
    {
        void onCreate ();

        void onMenuPressed ();

        void onNumberPressed (int n);

        void onACPressed();
        void onDotPressed();
        void onOperatorEqualsPressed ();
        void onOperatorSumPressed ();
        void onOperatorMinusPressed ();
        void onOperatorMultiplicationPressed ();
        void onOperatorDivisionPressed ();
        void onBackPressed();
    }

    public interface View
    {
        String getDisplayData ();
        char getLastElementDisplayData ();
        void setDisplayData (String data);
        void replaceLastElementDisplay (String data);
        void appendDisplayData (String data);
        void removeLastElementDisplay ();
        void clearDisplay ();
        boolean isClearedDisplay ();

        void goSettingsActivity ();
    }

}
