package com.rmm.quickcalc.view;

public interface ICalculator {

    public interface Model
    {

    }

    public interface Presenter
    {
        void onCreate ();

        void onMenuPressed ();

        void onNumberPressed (int n);

        void onACPressed();
        void onDotPressed();
        void onOperatorEqualsPressed ();
        void onOperatorSumPressed ();
        void onOperatorMinusPressed ();
        void onOperatorMultiplicationPressed ();
        void onOperatorDivisionPressed ();
        void onBackPressed();
    }

    public interface View
    {
        String getDisplayData ();
        char getLastElementDisplayData ();
        void setDisplayData (String data);
        void replaceLastElementDisplay (String data);
        void appendDisplayData (String data);
        void removeLastElementDisplay ();
        void clearDisplay ();
        boolean isClearedDisplay ();

        void goSettingsActivity ();
    }

}
