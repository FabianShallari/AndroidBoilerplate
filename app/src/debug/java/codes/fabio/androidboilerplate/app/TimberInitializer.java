package codes.fabio.androidboilerplate.app;

import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton class TimberInitializer {

  @Inject TimberInitializer() {
  }

  void init() {
    Timber.plant(new Timber.DebugTree() {
      @Override protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + ":" + element.getLineNumber();
      }
    });
  }
}