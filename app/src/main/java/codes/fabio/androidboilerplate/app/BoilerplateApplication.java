package codes.fabio.androidboilerplate.app;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import javax.inject.Inject;

public class BoilerplateApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Inject TimberInitializer timberInitializer;

  @Override public void onCreate() {
    super.onCreate();

    // Inject application component
    applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    applicationComponent.inject(this);

    // Initialize Libraries
    timberInitializer.init();
    LeakCanary.install(this);
  }

  // Utility method to easily access application component
  public static ApplicationComponent getApplicationComponent(Context context) {
    return ((BoilerplateApplication) context.getApplicationContext()).applicationComponent;
  }
}