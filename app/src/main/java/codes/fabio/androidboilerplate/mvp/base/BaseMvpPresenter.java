package codes.fabio.androidboilerplate.mvp.base;

import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

/**
 * A base MVP implementation of a {@link BasePresenter}
 *
 * You should always check {@link #isViewAttached()} to check if the view is attached to this
 * presenter before calling {@link #getView()} to access the view.
 */
public class BaseMvpPresenter<V extends BaseView> implements BasePresenter<V> {

  private WeakReference<V> view;

  @Override public void attachView(@NonNull V view) {
    this.view = new WeakReference<V>(view);
  }

  @Override public void detachView(boolean retainPresenter) {
    if (view != null) {
      view.clear();
      view = null;
    }
  }

  public boolean isViewAttached() {
    return view != null && view.get() != null;
  }

  public V getView() {
    if (view != null && view.get() != null) {
      return view.get();
    } else {
      throw new IllegalStateException(
          "The Underlying View is null, Please make sure to check that view is attached using isViewAttached before accessing view");
    }
  }
}
