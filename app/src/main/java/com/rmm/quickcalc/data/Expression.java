package com.rmm.quickcalc.data;

import java.util.ArrayList;

/**
 * @author Roberto
 * Class that represents a mathematical expression. It is compound of terms and operators.
 */
public class Expression {

    private ArrayList<String> mTerms;
    private ArrayList<EOperators> mOperators;

    public Expression (ArrayList<String> terms, ArrayList<EOperators> operators)
    {
        mTerms     = terms;
        mOperators = operators;
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
     * Retrieves the expression as a String.
     * @return The expression as a String.
     */
    @Override
    public String toString ()
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