package com.rmm.quickcalc.data;

import com.rmm.quickcalc.view.ICalculator;

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
}
