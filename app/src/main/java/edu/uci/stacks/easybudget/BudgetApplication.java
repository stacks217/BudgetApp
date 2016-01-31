package edu.uci.stacks.easybudget;

import android.app.Application;

public class BudgetApplication extends Application {

    private static ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
