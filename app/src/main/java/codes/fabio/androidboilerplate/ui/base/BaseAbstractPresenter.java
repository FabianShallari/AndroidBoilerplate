package codes.fabio.androidboilerplate.ui.base;

import android.support.annotation.NonNull;

/**
 * A base implementation of a {@link BasePresenter}
 *
 * You should always check {@link #isViewAttached()} to check if the view is attached to this
 * presenter before calling {@link #getView()} to access the view.
 *
 */
public class BaseAbstractPresenter<V extends BaseView> implements BasePresenter<V> {

  private V view;

  @Override public void attachView(@NonNull V view) {
    this.view = view;
  }

  @Override public boolean isViewAttached() {
    return getView() != null;
  }

  @Override public void detachView() {
    this.view = null;
  }

  public V getView() {
    return view;
  }
}
