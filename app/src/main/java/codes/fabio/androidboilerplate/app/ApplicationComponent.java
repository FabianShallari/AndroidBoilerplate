package codes.fabio.androidboilerplate.app;

import codes.fabio.androidboilerplate.data.db.DbModule;
import codes.fabio.androidboilerplate.data.remote.NetworkModule;
import codes.fabio.androidboilerplate.data.repository.ExampleRepository;
import dagger.Component;
import javax.inject.Singleton;

/**
 * The root component of the dependency graph.
 */
@Singleton @Component(modules = { ApplicationModule.class, DbModule.class, NetworkModule.class })
public interface ApplicationComponent {

  void inject(BoilerplateApplication transitAppApplication);

  ExampleRepository exampleRepository();

}
