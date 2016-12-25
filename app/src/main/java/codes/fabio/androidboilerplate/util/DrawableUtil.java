package codes.fabio.androidboilerplate.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;

import static android.support.v4.graphics.drawable.DrawableCompat.setTint;

/**
 * Common utility methods for backwards compatible drawable tinting and runtime dimension
 * conversion
 */
public class DrawableUtil {

  /**
   * Tint a drawable to a color in a backwards-compatible fashion
   * Works with SDK < 21
   *
   * @param original the original Drawable
   * @param color the color to tint the drawable
   * @param mutate flag if the Drawable should be mutated, use if you don't want the drawable to
   * share its state with other drawables
   * @return the tinted Drawable
   */
  public static Drawable tintToColor(Drawable original, @ColorInt int color, boolean mutate) {

    Drawable wrappedDrawable = DrawableCompat.wrap(original);
    if (mutate) {
      wrappedDrawable = wrappedDrawable.mutate();
    }
    setTint(wrappedDrawable, color);
    return wrappedDrawable;
  }

  /**
   * Calls {@link DrawableUtil#tintToColor(Drawable, int, boolean)} without mutating the original
   * drawable
   */
  public static Drawable tintToColor(Drawable original, @ColorInt int color) {
    return tintToColor(original, color, false);
  }

  /**
   * Convert dps to pixels
   */
  public static int dpToPx(Context context, float dp) {
    return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
  }
}
