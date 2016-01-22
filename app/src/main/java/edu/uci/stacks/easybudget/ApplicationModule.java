package edu.uci.stacks.easybudget;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.uci.stacks.easybudget.data.BudgetConfig;
import edu.uci.stacks.easybudget.data.BudgetDataDbHelper;
import edu.uci.stacks.easybudget.data.category.CategoryData;

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
    BudgetConfig provideBudgetConfig(Context context) {
        return new BudgetConfig(context);
    }

    @Provides
    BudgetDataDbHelper provideBudgetDataDbHelper(Context context) {
        return new BudgetDataDbHelper(context);
    }

    @Provides @Singleton
    CategoryData provideCategoryData(BudgetDataDbHelper budgetDataDbHelper) {
        return new CategoryData(budgetDataDbHelper);
    }
}