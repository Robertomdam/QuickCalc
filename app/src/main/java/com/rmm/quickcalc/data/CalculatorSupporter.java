package com.rmm.quickcalc.data;

import java.util.HashMap;
import java.util.Map;

public abstract class CalculatorSupporter {

    private static HashMap <EOperators, String> mOperators;

    public static void init (HashMap<EOperators, String> operators)
    {
        mOperators = operators;
    }

    public static boolean isOperator (char value)
    {
        return isOperator (String.valueOf(value));
    }

    public static boolean isOperator (String value)
    {
        for (int i = 0; i < mOperators.size(); i++) {
            String strOperator = mOperators.get ( EOperators.values()[i] );    // Using 'i' since the key is a enum variable

            if (strOperator.equals (value))
                return true;
        }

        return false;
    }

    public static String getOperator (EOperators operator) {
        return mOperators.get (operator);
    }

    public static EOperators getOperator (String operator) {

        for ( Map.Entry entry : mOperators.entrySet() )
        {
            if (entry.getValue().equals(operator))
                return (EOperators) entry.getKey();
        }

        return null;
    }
}
