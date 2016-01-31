package edu.uci.stacks.easybudget;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetDataDbHelper;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;

@Module
public class ApplicationModule {
    private final BudgetApplication application;

    public ApplicationModule(BudgetApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    BudgetConfig provideBudgetConfig(Context context, MoneyTransactionData moneyTransactionData) {
        return new BudgetConfig(context, moneyTransactionData);
    }

    @Provides
    BudgetDataDbHelper provideBudgetDataDbHelper(Context context) {
        return new BudgetDataDbHelper(context);
    }

    @Provides @Singleton
    CategoryData provideCategoryData(BudgetDataDbHelper budgetDataDbHelper) {
        return new CategoryData(budgetDataDbHelper);
    }

    @Provides @Singleton
    MoneyTransactionData provideMoneyTransactionData(BudgetDataDbHelper budgetDataDbHelper) {
        return new MoneyTransactionData(budgetDataDbHelper);
    }
}