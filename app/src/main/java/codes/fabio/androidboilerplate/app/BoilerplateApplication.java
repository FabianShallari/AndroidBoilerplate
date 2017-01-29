package codes.fabio.androidboilerplate.app;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import javax.inject.Inject;

public class BoilerplateApplication extends Application {

  private ApplicationComponent applicationComponent;
  private RefWatcher refWatcher;

  @Inject TimberInitializer timberInitializer;

  @Override public void onCreate() {
    super.onCreate();

    // Inject application component
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    applicationComponent.inject(this);

    // Initialize Libraries
    timberInitializer.init();
    refWatcher = LeakCanary.install(this);
  }

  // Utility method to easily access application component
  public static ApplicationComponent getApplicationComponent(Context context) {
    return ((BoilerplateApplication) context.getApplicationContext()).applicationComponent;
  }

  // Utility method to easily access RefWatcher
  public static RefWatcher getRefWatcher(Context context) {
    return ((BoilerplateApplication) context.getApplicationContext()).refWatcher;
  }
}