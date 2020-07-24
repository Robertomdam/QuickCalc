package com.rmm.quickcalc.data;

import com.rmm.quickcalc.view.ICalculator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Roberto
 * Class that acts as the model of the CalculatorActivity in a MVP pattern.
 */
public class CalculatorModel implements ICalculator.Model {

    private ICalculator.Presenter mPresenter;

    /**
     * This is a key-value list that contains the available operators in the calculator with its associated string (Got from R's resources)
     */
    private HashMap<EOperators, String> mOperators;

    public CalculatorModel (ICalculator.Presenter presenter, HashMap<EOperators, String> operators)
    {
        mOperators = operators;
        mPresenter = presenter;

        CalculatorSupporter.init (mOperators);
    }

    /**
     * Checks if the value is an operator.
     * @param value The value to check.
     * @return Whether the value is an operator or not.
     */
    public boolean isOperator (char value)
    {
        return CalculatorSupporter.isOperator (value);
    }

    /**
     * Checks if the value is an operator.
     * @param value The value to check.
     * @return Whether the value is an operator or not.
     */
    public boolean isOperator (String value)
    {
        return CalculatorSupporter.isOperator (value);
    }

    /**
     * Retrieves the string mapped with the EOperator parameter variable.
     * @param operator The EOperator variable.
     * @return The string that matches with the EOperator variable.
     */
    @Override
    public String getOperator(EOperators operator) {
        return CalculatorSupporter.getOperator (operator);
    }

    /**
     * Processes the introduced valid expression and retrieves the calculated value.
     * If any operation fails, an Invalid Expression exception is thrown.
     * @param validExpression A valid expression.
     * @return The calculated value.
     * @throws InvalidExpressionException Exception thrown when any calculation fails.
     */
    @Override
    public double processExpression (Expression validExpression) throws InvalidExpressionException {

        ArrayList<EOperators> operators = validExpression.getOperators();

        while (operators.size() > 0) {

            validExpression = ExpressionAnalizer.step (validExpression);

            if (validExpression == null)
                throw new InvalidExpressionException ("Invalid expression");

            operators = validExpression.getOperators();
        }

        return Double.parseDouble ( validExpression.toString() );
    }
}
