package com.rmm.quickcalc.data;

import android.util.Log;

import com.rmm.quickcalc.view.ICalculator;

import java.util.ArrayList;
import java.util.HashMap;

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

    public boolean isOperator (char value)
    {
        return CalculatorSupporter.isOperator (value);
    }

    public boolean isOperator (String value)
    {
        return CalculatorSupporter.isOperator (value);
    }

    @Override
    public String getOperator(EOperators operator) {
        return CalculatorSupporter.getOperator (operator);
    }

    @Override
    public String processExpression (Expression validExpression) {

        Expression returnExpression = new Expression(validExpression.getStrExpression());
        returnExpression.extract();

        ArrayList<String> terms = returnExpression.getTerms();
        ArrayList<EOperators> operators = returnExpression.getOperators();

        while (operators.size() > 0) {

            // Gets the first operator with priority (If there is no one, -1 is returned)
            int operatorIndex = getFirstPriorityOperatorIndex(operators);

            // If none priority operator was found, just calculate the first one
            if (operatorIndex == -1)
                operatorIndex = 0;

            double term1 = Float.parseFloat(terms.get(operatorIndex));
            double term2 = Float.parseFloat(terms.get(operatorIndex + 1));

            double result = 0;
            switch (operators.get(operatorIndex)) {
                case SUM:
                    result = CalculatorSupporter.sum(term1, term2);
                    break;

                case MINUS:
                    result = CalculatorSupporter.subtract(term1, term2);
                    break;

                case MULTIPLICATION:
                    result = CalculatorSupporter.mul(term1, term2);
                    break;

                case DIVISION:
                    result = CalculatorSupporter.divide(term1, term2);
                    break;
            }

            // Replacing the calculated terms and operator for its resulting value
            int operatorIndexInExpression = returnExpression.getOperatorIndexInFullExpression(operatorIndex);
            int term1Index = operatorIndex;
            int term2Index = operatorIndex + 1;

            int indexRemoveStart = returnExpression.getTermIndexInFullExpression(term1Index);
            int indexRemoveEnd = returnExpression.getTermIndexInFullExpression(term2Index) + terms.get(term2Index).length();

            // Creation of the new expression string
            String expression = returnExpression.getStrExpression();
            StringBuilder newExpression = new StringBuilder();
            newExpression.append(expression.substring(0, indexRemoveStart));

            int resultInt = (int) Math.ceil(result);
            float resultFloat = (float) result;
            if ((result % 1) == 0)
                newExpression.append(resultInt);
            else
                newExpression.append(resultFloat);

            newExpression.append(expression.substring(indexRemoveEnd, expression.length()));

            // Creates the new expression
            returnExpression = new Expression (newExpression.toString());
            returnExpression.extract();

            terms = returnExpression.getTerms();
            operators = returnExpression.getOperators();
        }

        return returnExpression.getStrExpression();
    }

    int getFirstPriorityOperatorIndex (ArrayList<EOperators> operators)
    {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == EOperators.MULTIPLICATION || operators.get(i) == EOperators.DIVISION)
                return i;
        }

        return -1;
    }
}
