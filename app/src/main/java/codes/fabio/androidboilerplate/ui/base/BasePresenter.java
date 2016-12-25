package codes.fabio.androidboilerplate.ui.base;

import android.support.annotation.NonNull;

/**
 * The base interface for each mvp presenter
 */
public interface BasePresenter<V extends BaseView> {

  void attachView(@NonNull V view);

  boolean isViewAttached();

  void detachView();
}
