package edu.uci.stacks.easybudget.activity;

import edu.uci.stacks.easybudget.activity.fragment.MonthlyChartFragment;
import edu.uci.stacks.easybudget.activity.fragment.MonthlyViewFragment;

public interface ActivityComponent {

    void inject(FirstTimeActivity firstTimeActivity);
    void inject(EditBasicBudgetActivity basicBudgetActivityctivity);
    void inject(EditAdvancedBudgetActivity advancedBudgetActivity);
    void inject(EditCategoryActivity categoryActivity);
    void inject(EnterPurchaseActivity enterPurchaseActivity);
    void inject(MainActivity mainActivity);
    void inject(MonthlyViewFragment monthlyViewFragment);
    void inject(MonthlyChartFragment monthlyChartFragment);
    void inject(ViewAllPurchasesActivity viewAllPurchasesActivity);

}
