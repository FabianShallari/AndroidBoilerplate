package codes.fabio.androidboilerplate.util;

import android.app.ActivityManager;
import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import static android.support.v4.app.ActivityManagerCompat.isLowRamDevice;
import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;
import static com.bumptech.glide.load.DecodeFormat.PREFER_RGB_565;

public class GlideConfigurationModule implements GlideModule {

  @Override public void applyOptions(Context context, GlideBuilder builder) {
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

    builder.setDecodeFormat(isLowRamDevice(activityManager) ? PREFER_RGB_565 : PREFER_ARGB_8888);
  }

  @Override public void registerComponents(Context context, Glide glide) {
    // no-op
  }
}
