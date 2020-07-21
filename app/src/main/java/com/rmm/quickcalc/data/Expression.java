package com.rmm.quickcalc.data;

import android.util.Log;

import java.util.ArrayList;

public class Expression {

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

    public ArrayList<String> getTerms() {
        return mTerms;
    }

    public ArrayList<EOperators> getOperators() {
        return mOperators;
    }

    public Status getStatus() {
        return mStatus;
    }
}
