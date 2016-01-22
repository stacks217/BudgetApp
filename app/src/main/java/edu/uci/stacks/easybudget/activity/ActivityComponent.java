package edu.uci.stacks.easybudget.activity;

public interface ActivityComponent {

    void inject(FirstTimeActivity firstTimeActivity);
    void inject(EditBasicBudgetActivity basicBudgetActivityctivity);
    void inject(EditAdvancedBudgetActivity advancedBudgetActivity);
    void inject(EditCategoryActivity categoryActivity);
    void inject(MainActivity mainActivity);

}
