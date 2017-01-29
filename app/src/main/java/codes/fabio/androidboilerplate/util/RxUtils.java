package codes.fabio.androidboilerplate.util;

import rx.Subscription;

/**
 * Utility methods for RxOperations
 */
public class RxUtils {

  private RxUtils() {
    // util class, no instances
  }

  public static void unsubscribe(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
