package codes.fabio.androidboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.Bind;
import codes.fabio.androidboilerplate.R;
import javax.inject.Inject;

import static android.support.v4.content.ContextCompat.getColor;
import static codes.fabio.androidboilerplate.util.DrawableUtil.tintToColor;

/**
 * A {@link LceView} fragment which retains its instance and uses dependency injection and {@link
 * LceViewState} to fully retain its state during a configuration change
 */
public abstract class BaseRetainedLceViewStateFragment<C, CV extends View, V extends BaseView, M, P extends BasePresenter<V>>
    extends BaseInjectedFragment<C, V, P> implements LceView<M> {

  protected static final int EMPTY = 0;
  protected static final int ERROR = 1;
  protected static final int CONTENT = 2;
  protected static final int LOADING = 3;

  @Bind(R.id.viewFlipper) protected ViewFlipper viewFlipper;
  @Bind(R.id.contentView) protected CV contentView;
  @Bind(R.id.emptyView) protected TextView textView;
  @Bind(R.id.errorView) protected TextView errorView;
  @Bind(R.id.progressBar) protected ProgressBar loadingView;

  @Inject protected LceViewState lceViewState;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loadingView.setIndeterminateDrawable(tintToColor(loadingView.getIndeterminateDrawable(),
        getColor(getContext(), R.color.colorAccent)));
    lceViewState.applyViewState(this);
  }

  @Override protected boolean shouldRetainInstance() {
    return true;
  }

  @Override public void showError(@Nullable Throwable error) {

    String errorMessage = error == null ? getString(R.string.unknown_error) : error.getMessage();

    if (viewFlipper.getDisplayedChild() != CONTENT) {
      showMessage(errorMessage);
    } else {
      errorView.setText(errorMessage);
      viewFlipper.setDisplayedChild(ERROR);
    }

    lceViewState.setStateShowError(error);
  }

  @Override public void showContent() {
    viewFlipper.setDisplayedChild(CONTENT);
    lceViewState.setStateShowContent();
  }

  @Override public void showEmpty() {
    textView.setText(getEmptyText());
    viewFlipper.setDisplayedChild(EMPTY);

    lceViewState.setStateShowEmpty();
  }

  protected String getEmptyText() {
    return getString(R.string.no_items);
  }

  @Override public void showLoading() {
    viewFlipper.setDisplayedChild(LOADING);

    lceViewState.setStatShowLoading();
  }
}
