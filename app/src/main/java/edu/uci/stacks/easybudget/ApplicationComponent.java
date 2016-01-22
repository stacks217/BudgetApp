package edu.uci.stacks.easybudget;

import javax.inject.Singleton;

import dagger.Component;
import edu.uci.stacks.easybudget.activity.ActivityComponent;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent extends ActivityComponent {

}
