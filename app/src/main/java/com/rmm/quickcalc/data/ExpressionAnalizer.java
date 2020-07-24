package com.rmm.quickcalc.data;

import java.util.ArrayList;

/**
 * Class that helps handling expressions. It contains some functions to operate the expressions.
 */
public abstract class ExpressionAnalizer {

    /**
     * Retrieves an Expression object based on the string expression passed by parameter.
     * It tries to fix some problematic aspect such as removing an operator if it is placed at
     * the end of the expression (i.e.: 4+15/ would be converted to 4+15)
     * This method split the terms and operators of the introduced expression and stores them
     * in the returned Expression instance.
     * @param expressionString String that contains the mathematical expression.
     * @return A Expression object that represents an expresion.
     */
    public static Expression extract (String expressionString)
    {
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<EOperators> operators = new ArrayList<>();

        char[] expressionCharArray = expressionString.toCharArray();

        // If the first element is an operator we add a 0 at left part of the full expresion
        if (CalculatorSupporter.isOperator(expressionCharArray[0]))
        {
            expressionString = "0" + expressionString;
            expressionCharArray = expressionString.toCharArray();
        }

        // If the last element is an operator we just ignore it
        if (CalculatorSupporter.isOperator(expressionCharArray[expressionString.length() - 1]))
        {
            expressionString = expressionString.substring(0, expressionString.length() - 1);
            expressionCharArray = expressionString.toCharArray();
        }

        StringBuilder num = new StringBuilder();

        for (int i = 0; i < expressionCharArray.length ; i++) {

            // If the current element is an operator...
            if (CalculatorSupporter.isOperator(expressionCharArray [i]))
            {
                // If two operators are together or the last element is an operator, the expression is marked as not valid
                if (CalculatorSupporter.isOperator ( expressionCharArray[i - 1] ) || i == expressionCharArray.length - 1)
                {
                    return null;
                }

                // We finish our number and add them to a completed term
                terms.add(num.toString());

                // Reset the builder for the next term
                num = new StringBuilder();

                // And add the operator to the list of operators
                operators.add ( CalculatorSupporter.getOperator ( String.valueOf (expressionCharArray[i]) ) );
            }
            // If it is a number..
            else
            {
                num.append (expressionCharArray[i]);
            }
        }

        // Again.. we have to add the last term to the list of terms
        terms.add(num.toString());

        // Checking that all the terms are valid numbers (avoiding expressions strings like i.e.: 5+t-22)
        for (int i = 0; i < terms.size(); i++) {
            if ( !isValidNumber (terms.get(i)) )
                return null;
        }

        return new Expression(terms, operators);
    }

    /**
     * This method tries to calculate the first possible operation in the specified expression.
     * (i.e.: 5+3*2-1  Will be processed and returned as 5+6-1)
     * The calculation processes consist of operating the terms that corresponds to the operator that
     * has the biggest priority in the expression. If none, just calculate the first one.
     * @param validExpression A valid expression object.
     * @return A expression that represents the next step of the calculation process of the introduced valid expression.
     */
    public static Expression step (Expression validExpression)
    {
        ArrayList<String>         terms = validExpression.getTerms();
        ArrayList<EOperators> operators = validExpression.getOperators();

        // Gets the first operator with priority (If there is no one, -1 is returned)
        int operatorIndex = getFirstPriorityOperatorIndex (operators);

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

        if (Double.isInfinite(result) || Double.isNaN(result))
            return null;

        // Replacing the calculated terms and operator for its resulting value
        int operatorIndexInExpression = validExpression.getOperatorIndexInFullExpression(operatorIndex);
        int term1Index = operatorIndex;
        int term2Index = operatorIndex + 1;

        int indexRemoveStart = validExpression.getTermIndexInFullExpression(term1Index);
        int indexRemoveEnd = validExpression.getTermIndexInFullExpression(term2Index) + terms.get(term2Index).length();

        // Creation of the new expression string
        String expressionString = validExpression.toString();
        StringBuilder newExpression = new StringBuilder();

        newExpression.append(expressionString.substring(0, indexRemoveStart));
        newExpression.append(result);
        newExpression.append(expressionString.substring(indexRemoveEnd, expressionString.length()));

        // Creates the new expression
        return ExpressionAnalizer.extract (newExpression.toString());
    }

    /**
     * Checks if the introduced number is a valid one.
     * @param number The number to check.
     * @return Whether the number is valid or not.
     */
    private static boolean isValidNumber (String number)
    {
        try {
            Double.parseDouble(number);

        } catch (NumberFormatException e) {
            return false;

        }

        return true;
    }

    /**
     * Retrieves index of the the first occurrence of an operator that
     * has priority (multiplication or division) from a list of operators.
     * Example: The list of operators contains: { +, -, - x, +, + }
     * In this list, the retrieved index would be 3.
     * @param operatorsList The list of operators.
     * @return The index of the first operator with priority that is found.
     */
    private static int getFirstPriorityOperatorIndex (ArrayList<EOperators> operatorsList)
    {
        for (int i = 0; i < operatorsList.size(); i++) {
            if (operatorsList.get(i) == EOperators.MULTIPLICATION || operatorsList.get(i) == EOperators.DIVISION)
                return i;
        }

        return -1;
    }
}
