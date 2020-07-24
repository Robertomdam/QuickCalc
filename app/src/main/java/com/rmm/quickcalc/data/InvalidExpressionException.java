package com.rmm.quickcalc.data;

/**
 * @author Roberto
 * Class that represents a custom exception related with expression errors.
 */
public class InvalidExpressionException extends Exception {

    public InvalidExpressionException (String message)
    {
        super (message);
    }

}
