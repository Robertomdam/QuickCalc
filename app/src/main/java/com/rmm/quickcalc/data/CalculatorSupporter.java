package com.rmm.quickcalc.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Roberto
 * Helper class to handle some mathematical related functionality. Non instantiable class, all
 * the methods are static.
 */
public abstract class CalculatorSupporter {

    private static HashMap <EOperators, String> mOperators;

    /**
     * CLass that initializes the data of the class.
     * Establishes the operatos available to use by the class methods.
     * @param operators The available operators.
     */
    public static void init (HashMap<EOperators, String> operators)
    {
        mOperators = operators;
    }

    /**
     * Checks if the passed value is an operator or not.
     * @param value The value to be checked.
     * @return Whether the parameter value is an operator or not.
     */
    public static boolean isOperator (char value)
    {
        return isOperator (String.valueOf(value));
    }

    /**
     * Checks if the passed value is an operator or not.
     * @param value The value to be checked.
     * @return Whether the parameter value is an operator or not.
     */
    public static boolean isOperator (String value)
    {
        for (int i = 0; i < mOperators.size(); i++) {
            String strOperator = mOperators.get ( EOperators.values()[i] );    // Using 'i' since the key is a enum variable

            if (strOperator.equals (value))
                return true;
        }

        return false;
    }

    /**
     * Retrieves the mapped string value of the EOperator variable.
     * @param operator EOperator variable.
     * @return The mapped string of the EOperator variable.
     */
    public static String getOperator (EOperators operator) {
        return mOperators.get (operator);
    }

    /**
     * Retrieves the mapped EOperator variable of the string operator.
     * @param operator String operator .
     * @return The EOperator variable that matches the string operator.
     */
    public static EOperators getOperator (String operator) {

        for ( Map.Entry entry : mOperators.entrySet() )
        {
            if (entry.getValue().equals(operator))
                return (EOperators) entry.getKey();
        }

        return null;
    }

    /**
     * Retrieves the result of the sum operation between two numbers.
     * @param n1 The first term to operate.
     * @param n2 The seconds term to operate.
     * @return The result of the sum operation.
     */
    public static double sum (double n1, double n2)
    {
        return n1 + n2;
    }

    /**
     * Retrieves the result of the subtract operation between two numbers.
     * @param n1 The first term to operate.
     * @param n2 The seconds term to operate.
     * @return The result of the subtract operation.
     */
    public static double subtract (double n1, double n2)
    {
        return n1 - n2;
    }

    /**
     * Retrieves the result of the multiplication operation between two numbers.
     * @param n1 The first term to operate.
     * @param n2 The seconds term to operate.
     * @return The result of the multiplication operation.
     */
    public static double mul (double n1, double n2)
    {
        return n1 * n2;
    }

    /**
     * Retrieves the result of the division operation between two numbers.
     * @param n1 The first term to operate.
     * @param n2 The seconds term to operate.
     * @return The result of the division operation.
     */
    public static double divide (double n1, double n2)
    {
        return n1 / n2;
    }

    /**
     * Retrieves the number with only the same number of decimals as indicated in the precision.
     * @param number The number to operate with.
     * @param precision The number of decimals for the returned number.
     * @return The number passed by parameter with the specified precision.
     */
    public static double getDoubleDecimalsPrecision (double number, int precision)
    {
        int decimalsScale = BigDecimal.valueOf(number).scale();
        int maxNumDecimals = precision;

        if (maxNumDecimals > decimalsScale)
            maxNumDecimals = decimalsScale;

        String str = String.format(Locale.US, "%."+maxNumDecimals+"f", number);
        double resultDoubleFixDecimals = Double.parseDouble ( str );

        return resultDoubleFixDecimals;
    }

    /**
     * Checks if the parameter number have all zeros in its decimals values.
     * @param number The number to check.
     * @return Whether the number has all its decimal values as zeros.
     */
    public static boolean canBeInteger (double number)
    {
        long resultLong = Math.round (number);

        return (resultLong == number);
    }
}
