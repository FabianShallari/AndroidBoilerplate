package codes.fabio.androidboilerplate.mvp.conductorbase;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ViewFlipper;
import butterknife.BindView;
import codes.fabio.androidboilerplate.R;
import codes.fabio.androidboilerplate.mvp.base.BasePresenter;
import codes.fabio.androidboilerplate.mvp.base.MultiStateView;

/**
 * /**
 * A {@link MultiStateView} controller which uses a {@link ViewFlipper} to change between 4 states
 * as defined in a {@link MultiStateView}
 *
 * @param <C> the Dagger Component that
 * @param <CONTENTVIEW> the type of the content view
 * @param <EMPTYVIEW> the type of the empty view
 * @param <ERRORVIEW> the type of the error view
 * @param <LOADINGVIEW> the type of the loading view
 * @param <M> the data model this controller displays
 * @param <V> the view interface this controller implements
 * @param <P> the presenter reposinble for orchestrating this controller
 */
public abstract class MultiStateControllerBase<C, CONTENTVIEW extends View, EMPTYVIEW extends View, ERRORVIEW extends View, LOADINGVIEW extends View, M, V extends MultiStateView<M>, P extends BasePresenter<V>>
    extends BaseDaggerController<C, V, P> implements MultiStateView<M> {

  @BindView(R.id.viewFlipper) ViewFlipper viewFlipper;
  @BindView(R.id.contentView) CONTENTVIEW contentview;
  @BindView(R.id.emptyView) EMPTYVIEW emptyview;
  @BindView(R.id.errorView) ERRORVIEW errorview;
  @BindView(R.id.loadingView) LOADINGVIEW loadingview;

  protected static final int CONTENT = 0;
  protected static final int EMPTY = 1;
  protected static final int ERROR = 2;
  protected static final int LOADING = 3;

  @Override protected void onViewCreated(View view) {
    super.onViewCreated(view);
    showLoading();
  }

  @Override public void showEmpty() {
    viewFlipper.setDisplayedChild(EMPTY);
  }

  @Override public void showError(@Nullable Throwable error) {
    //noinspection ConstantConditions
    String errorMessage =
        error == null ? getResources().getString(R.string.unknown_error) : error.getMessage();

    if (viewFlipper.getDisplayedChild() == CONTENT) {
      // Show light error since we are already showing the content
      showLightError(errorMessage);
    } else {
      // no content shown, show the error view
      viewFlipper.setDisplayedChild(ERROR);
      showFullErrorMessage(errorMessage);
    }
  }

  protected void showLightError(String message) {
    showMessage(message);
  }

  protected abstract void showFullErrorMessage(String message);

  @Override public void showContent(M data) {
    viewFlipper.setDisplayedChild(CONTENT);
  }

  @Override public void showLoading() {
    viewFlipper.setDisplayedChild(LOADING);
  }
}
