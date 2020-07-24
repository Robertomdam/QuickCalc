package com.rmm.quickcalc;

import com.rmm.quickcalc.data.CalculatorSupporter;
import com.rmm.quickcalc.data.EOperators;
import com.rmm.quickcalc.data.Expression;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpressionTest {

    @BeforeClass
    public static void init ()
    {
        HashMap<EOperators, String> operators = new HashMap<>();
        operators.put (EOperators.SUM, "+");
        operators.put (EOperators.MINUS, "-");
        operators.put (EOperators.MULTIPLICATION, "*");
        operators.put (EOperators.DIVISION, "/");
        operators.put (EOperators.EQUALS, "=");

        CalculatorSupporter.init (operators);
    }

//    @Test
//    public void noOperators ()
//    {
//        Expression expression = new Expression("2525.5");
//        expression.extract();
//
//        ArrayList<String>     terms     = expression.getTerms();
//        ArrayList<EOperators> operators = expression.getOperators();
//
//        System.out.println ("Num terms: "     + terms.size());
//        System.out.println ("Num operators: " + operators.size());
//
//        System.out.println ("Terms: "     + terms.get(0));
//    }
//
//    @Test
//    public void easyExtractChecking ()
//    {
//        Expression expression = new Expression("2+4");
//        expression.extract();
//
//        ArrayList<String>     terms     = expression.getTerms();
//        ArrayList<EOperators> operators = expression.getOperators();
//
//        Assert.assertTrue (terms.size() == 2);
//        Assert.assertTrue (operators.size() == 1);
//
////        System.out.println ("Num operators: " + operators.size());
//
//        Assert.assertTrue (operators.get(0).equals(EOperators.SUM));
//    }
//
//    @Test
//    public void mediumExtractChecking ()
//    {
//        Expression expression = new Expression ("*21-357");
//        expression.extract();
//
//        ArrayList<String>     terms     = expression.getTerms();
//        ArrayList<EOperators> operators = expression.getOperators();
//
////        Assert.assertTrue (terms.size() == 2);
////        Assert.assertTrue (operators.size() == 1);
//
//        System.out.println ("Num terms: "     + terms.size());
//        System.out.println ("Num operators: " + operators.size());
//
//        Assert.assertTrue (operators.get(0).equals(EOperators.MULTIPLICATION));
//
//        System.out.println ("First term: " + terms.get(0));
//        System.out.println ("Seconds term: " + terms.get(1));
//        System.out.println ("Third term: " + terms.get(2));
//    }
//
//    @Test
//    public void expressionIsNotValid ()
//    {
//        Expression expression = new Expression ("-2*/2*");
//        expression.extract();
//
//        System.out.println ("Status: " + expression.getStatus().toString());
//
//        Assert.assertTrue (expression.getStatus() == Expression.Status.INVALID);
//    }
//
//    @Test
//    public void getStrExpression ()
//    {
//        Expression expression = new Expression("-25+7-4");
//        expression.extract();
//
//        System.out.println ("Expression: "     + expression.getStrExpression());
//    }
}