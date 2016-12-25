package codes.fabio.androidboilerplate;

import android.os.Build;
import codes.fabio.androidboilerplate.app.BoilerplateApplication;
import java.lang.reflect.Method;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * A common class for running Roboelectric tests with
 */
public class BoilerplateApplicationRoboelectricTestRunner extends RobolectricTestRunner {

  private static final int TEST_SDK_LEVEL = Build.VERSION_CODES.M;

  /**
   * Creates a runner to run {@code testClass}. Looks in your working directory for your
   * AndroidManifest.xml file
   * and res directory by default. Use the {@link Config} annotation to configure.
   *
   * @param testClass the test class to be run
   * @throws InitializationError if junit says so
   */
  public BoilerplateApplicationRoboelectricTestRunner(Class<?> testClass)
      throws InitializationError {
    super(testClass);
  }

  @Override public Config getConfig(Method method) {
    Config defaultConfig = super.getConfig(method);
    return new Config.Implementation(new int[] { TEST_SDK_LEVEL }, defaultConfig.manifest(),
        defaultConfig.qualifiers(), defaultConfig.packageName(), defaultConfig.abiSplit(),
        defaultConfig.resourceDir(), defaultConfig.assetDir(), defaultConfig.buildDir(),
        defaultConfig.shadows(), defaultConfig.instrumentedPackages(), BoilerplateApplication.class,
        defaultConfig.libraries(), BuildConfig.class);
  }
}
