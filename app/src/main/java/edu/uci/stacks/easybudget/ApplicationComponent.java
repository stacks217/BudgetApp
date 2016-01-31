package edu.uci.stacks.easybudget;

import javax.inject.Singleton;

import dagger.Component;
import edu.uci.stacks.easybudget.activity.ActivityComponent;
import edu.uci.stacks.easybudget.service.ServiceComponent;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent extends ActivityComponent, ServiceComponent {

}
