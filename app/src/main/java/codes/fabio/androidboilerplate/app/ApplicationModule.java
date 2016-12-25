package codes.fabio.androidboilerplate.app;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Provides application-related dependencies like {@link Context}
 */
@Module class ApplicationModule {

  private final Context applicationContext;

  ApplicationModule(Context context) {
    this.applicationContext = context.getApplicationContext();
  }

  @Provides @Singleton Context provideContext() {
    return applicationContext;
  }
}
