package codes.fabio.androidboilerplate.app;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

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

  @Provides @Singleton SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences("example_app_shared_prefs", MODE_PRIVATE);
  }
}
