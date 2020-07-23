package com.rmm.quickcalc.data;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author Roberto
 * Class that represents a mathematical expression. It is compound of terms and operators.
 * Expressions must be extracted by calling extract() method before accessing any other methods,
 * since this will populate and alterate the other members of the instance.
 */
public class Expression {

    /**
     * Represents the available status of a expression.
     */
    public enum Status { NOT_PROCESSED, READY, INVALID }
    private Status mStatus;

    private String mExpressionString;
    private char[] mExpressionCharArray;
    private ArrayList<String> mTerms;
    private ArrayList<EOperators> mOperators;

    public Expression (String expressionString)
    {
        mStatus = Status.NOT_PROCESSED;

        mExpressionString = expressionString;
        mExpressionCharArray = mExpressionString.toCharArray();

        mTerms     = new ArrayList<String> ();
        mOperators = new ArrayList<EOperators> ();
    }

    /**
     * Populates the terms and operators members of the instance based
     * on the expression passed in instantiation process. Both terms and operators
     * will not be sorted in any specific way, just the same it appears in the expression.
     * Some minor fixed are accomplish in this method, like setting a 0 when an expression begins with
     * an operator or removing an operator when it is the last element in the expression.
     */
    public void extract ()
    {
        String expressionString = mExpressionString;
        char[] expressionCharArray = mExpressionCharArray;

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
                    mStatus = Status.INVALID;
                    return;
                }

                // We finish our number and add them to a completed term
                mTerms.add(num.toString());

                // Reset the builder for the next term
                num = new StringBuilder();

                // And add the operator to the list of operators
                mOperators.add ( CalculatorSupporter.getOperator ( String.valueOf (expressionCharArray[i]) ) );
            }

            // If it is a number..
            else
            {
                num.append (expressionCharArray[i]);
            }
        }

        // Again.. we have to add the last term to the list of terms
        mTerms.add(num.toString());

        mStatus = Status.READY;
    }

    /**
     * Retrieves the terms of the expression.
     * @return The terms member
     */
    public ArrayList<String> getTerms() {
        return mTerms;
    }

    /**
     * Retrieves the operators of the expression.
     * @return The operators memeber.
     */
    public ArrayList<EOperators> getOperators() {
        return mOperators;
    }

    /**
     * Retrieves the status of the expression.
     * @return The status of the expression.
     */
    public Status getStatus() {
        return mStatus;
    }

    /**
     * Retrieves the expression as a String.
     * @return The expression as a String.
     */
    public String getStrExpression ()
    {
        StringBuilder strExpression = new StringBuilder();

        for (int i = 0; i < mTerms.size(); i++)
        {
            strExpression.append (mTerms.get(i));

            if (i < mOperators.size())
                strExpression.append (CalculatorSupporter.getOperator(mOperators.get(i)));
        }

        return strExpression.toString();
    }

    /**
     * Converts the index of one operator in the operators list to the
     * index of the same operator in the full expression (terms+operators).
     * Example: Expression 25+15*2
     * The * operator has the index 1 in the list of operators. However, its index
     * in the full expression would be 5.
     * @param operatorIndex The index of the operator in the operators list.
     * @return The index of the same operator in the full expression.
     */
    public int getOperatorIndexInFullExpression (int operatorIndex)
    {
        int index = 0;

        for (int i = 0; i <= operatorIndex; i++)
        {
            index += mTerms.get(i).length();
        }

        index += operatorIndex;

        return index;
    }

    /**
     * Converts the index of one term in the terms list to the
     * index of the same term in the full expression (terms+operators).
     * Example: Expression 25+15*2
     * The "15" term has the index 1 in the list of operators. However, its index
     * in the full expression would be 3 (The beginning of the term).
     * @param termIndex The index of the term in the terms list.
     * @return The index of the same term in the full expression.
     */
    public int getTermIndexInFullExpression (int termIndex)
    {
        int index = 0;

        for (int i = 0; i < termIndex; i++)
        {
            index += mTerms.get(i).length();
            index++;   // Corresponding to the operators, since every term will be continued by a operator (Except in the last term, but this will be never reached due to the for condition)
        }
        return index;
    }
}
