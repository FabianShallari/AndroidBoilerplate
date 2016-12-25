package codes.fabio.androidboilerplate.ui.base;

import android.support.annotation.Nullable;
import codes.fabio.androidboilerplate.ui.common.PerFragment;
import javax.inject.Inject;

/**
 * An LCE (Loading-Content-Error-Empty) {@link ViewState} is a viewstate that can save and restore
 * loading, the content, the error (exception), or the empty states in which the view is.
 */
@PerFragment public class LceViewState implements ViewState<LceView> {

  private static final int STATE_SHOW_EMPTY = 0;
  private static final int STATE_SHOW_ERROR = 1;
  private static final int STATE_SHOW_CONTENT = 2;
  private static final int STATE_SHOW_LOADING = 3;

  private int state;

  @Nullable private Throwable error;

  @Inject LceViewState() {
    state = STATE_SHOW_LOADING;
  }

  @Override public void applyViewState(LceView view) {
    switch (state) {
      case STATE_SHOW_CONTENT:
        view.showContent();
        break;
      case STATE_SHOW_EMPTY:
        view.showEmpty();
        break;
      case STATE_SHOW_ERROR:
        view.showError(error);
        break;
      default:
        view.showLoading();
        break;
    }
  }

  void setStatShowLoading() {
    state = STATE_SHOW_LOADING;
  }

  void setStateShowError(@Nullable Throwable error) {
    this.error = error;
    state = STATE_SHOW_ERROR;
  }

  void setStateShowContent() {
    state = STATE_SHOW_CONTENT;
  }

  void setStateShowEmpty() {
    state = STATE_SHOW_EMPTY;
  }
}
