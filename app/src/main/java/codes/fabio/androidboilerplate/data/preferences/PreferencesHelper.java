package codes.fabio.androidboilerplate.data.preferences;

import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

// TODO: 1/29/17 Edit as per app needs
@Singleton public class PreferencesHelper {

  private final SharedPreferences sharedPreferences;

  public static final String KEY_EXAMPLE = "key_example";

  @Inject PreferencesHelper(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public String getExampleValue() {
    return sharedPreferences.getString(KEY_EXAMPLE, "");
  }

  public void putExampleValue(String example) {
    sharedPreferences.edit().putString(KEY_EXAMPLE, example).apply();
  }
}
