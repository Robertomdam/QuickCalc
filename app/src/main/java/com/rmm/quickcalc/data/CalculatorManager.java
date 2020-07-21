package com.rmm.quickcalc.data;

import com.rmm.quickcalc.view.ICalculator;

public class CalculatorManager implements ICalculator.Model {

    ICalculator.Presenter mPresenter;

    public CalculatorManager (ICalculator.Presenter presenter)
    {
        mPresenter = presenter;
    }

}
