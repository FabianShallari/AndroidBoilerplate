package codes.fabio.androidboilerplate.mvp.base;

import android.support.annotation.NonNull;

/**
 * The root interface for a MVP presenter
 */
public interface BasePresenter<V extends BaseView> {

  void attachView(@NonNull V view);

  void detachView(boolean retainPresenter);
}
