package com.rmm.quickcalc;

import com.rmm.quickcalc.data.CalculatorSupporter;
import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;
import com.rmm.quickcalc.data.ExpressionAnalizer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ExpressionAnalizerTest {

    @BeforeClass
    public static void setUp() throws Exception {

        HashMap<EOperators, String> operators = new HashMap<>();
        operators.put (EOperators.SUM           , "+");
        operators.put (EOperators.MINUS         , "-");
        operators.put (EOperators.MULTIPLICATION, "*");
        operators.put (EOperators.DIVISION      , "/");
        operators.put (EOperators.EQUALS        , "=");

        CalculatorSupporter.init (operators);

    }

    @Test
    public void checkExtractExpressionNotNull ()
    {
        Expression expression = ExpressionAnalizer.extract ("25+2-155");

        Assert.assertNotNull (expression);
    }

    @Test
    public void checkExtractExpressionTermsOperators ()
    {
        Expression expression = ExpressionAnalizer.extract ("-7+14*3");

        System.out.println (expression.getTerms().size());
        System.out.println (expression.getOperators().size());
    }

    @Test
    public void checkStep ()
    {
        Expression expression = ExpressionAnalizer.extract ("5+3*2-1");
        Assert.assertNotNull (expression);
        System.out.println ("Init expression: " + expression);

        expression = ExpressionAnalizer.step (expression);
        Assert.assertNotNull (expression);
        System.out.println ("Step: " + expression);
    }

    @Test
    public void checkCalcExpression ()
    {
        Expression expression = ExpressionAnalizer.extract ("5+3*2-1");
        Assert.assertNotNull (expression);
        System.out.println ("Init expression: " + expression);

        ArrayList<EOperators> operators = expression.getOperators();

        while (operators.size() > 0) {

            expression = ExpressionAnalizer.step (expression);
            Assert.assertNotNull (expression);

            System.out.println ("Step: " + expression);
            operators = expression.getOperators();
        }
    }

    @Test
    public void showFormattedResult ()
    {
        Expression expression = ExpressionAnalizer.extract ("1.115*2");
        Assert.assertNotNull (expression);
        System.out.println ("Init expression: " + expression);

        ArrayList<EOperators> operators = expression.getOperators();

        while (operators.size() > 0) {

            expression = ExpressionAnalizer.step (expression);
            Assert.assertNotNull (expression);

            System.out.println ("Step: " + expression);
            operators = expression.getOperators();
        }

        double resultDouble = Double.parseDouble ( expression.toString() );
        System.out.println ("Final result: " + resultDouble);

// Reducing the number of decimals to a  smaller number if needed
        double resultDoubleFixDecimals = CalculatorSupporter.getDoubleDecimalsPrecision(resultDouble, 3);

// Checking if the value has only zeros in the decimal part
        String resultStr = "";

        if (CalculatorSupporter.canBeInteger(resultDoubleFixDecimals))
            resultStr = String.valueOf (Math.round(resultDoubleFixDecimals));
        else
            resultStr = String.valueOf (resultDoubleFixDecimals);

        System.out.println ("Final string: " + resultStr);
        System.out.println ("Final formatted string: " + String.format(Locale.US, "%.3", resultStr));
    }
}