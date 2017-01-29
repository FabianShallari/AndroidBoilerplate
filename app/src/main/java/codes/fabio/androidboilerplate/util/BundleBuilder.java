package codes.fabio.androidboilerplate.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Build {@link Bundle} objects using the builder pattern§
 */
// TODO: 1/29/17 Add Methods for int and other types
public class BundleBuilder {

  private final Bundle bundle;

  private BundleBuilder(Bundle bundle) {
    this.bundle = bundle;
  }

  public static BundleBuilder create() {
    return new BundleBuilder(new Bundle());
  }

  public BundleBuilder putAll(@NonNull Bundle from) {
    bundle.putAll(bundle);
    return this;
  }

  public BundleBuilder putString(@NonNull String key, @Nullable String value) {
    bundle.putString(key, value);
    return this;
  }

  public Bundle build() {
    return bundle;
  }
}


