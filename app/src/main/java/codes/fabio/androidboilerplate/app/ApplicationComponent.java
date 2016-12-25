package codes.fabio.androidboilerplate.app;

import dagger.Component;
import javax.inject.Singleton;

/**
 * The root component of the dependency graph.
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface ApplicationComponent {

  void inject(BoilerplateApplication transitAppApplication);
}
