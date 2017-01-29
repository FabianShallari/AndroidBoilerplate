package codes.fabio.androidboilerplate.mvp.conductorbase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import codes.fabio.androidboilerplate.R;
import codes.fabio.androidboilerplate.mvp.base.BasePresenter;
import codes.fabio.androidboilerplate.mvp.base.MultiStateView;

import static android.support.v4.content.ContextCompat.getColor;
import static codes.fabio.androidboilerplate.util.DrawableUtil.tintToColor;

/**
 * A specialized {@link MultiStateControllerBase} which uses a {@link SwipeRefreshLayout} as its
 * content view, a {@link ProgressBar} as its loading view, and a {@link ViewGroup} for its empty
 * and error states. This can be used as based and makes no assumptions for the error, empty or
 * loading views to represent the corresponding states
 *
 * {@inheritDoc}
 */
public abstract class RefreshableRecyclerViewMultiStateControllerBase<COMPONENT, MODEL, VIEW extends MultiStateView<MODEL>, PRESENTER extends BasePresenter<VIEW>>
    extends
    MultiStateControllerBase<COMPONENT, SwipeRefreshLayout, ViewGroup, ViewGroup, ProgressBar, MODEL, VIEW, PRESENTER> {

  @BindView(R.id.recyclerView) protected RecyclerView recyclerView;
  @BindView(R.id.errorTextView) protected TextView errorTextView;
  @BindView(R.id.emptyTextView) protected TextView emptyTextView;

  @OnClick({ R.id.emptyRetry, R.id.errorRetry }) void onRetry() {
    viewFlipper.setDisplayedChild(LOADING);
    loadData(true);
  }

  @Override protected void onViewCreated(View view) {
    super.onViewCreated(view);

    // Listen for refreshes
    contentview.setOnRefreshListener(() -> loadData(true));

    // Tint ProgressBar
    //noinspection ConstantConditions
    loadingview.setIndeterminateDrawable(tintToColor(loadingview.getIndeterminateDrawable(),
        getColor(getActivity(), R.color.colorAccent)));

    // Set Empty Text
    emptyTextView.setText(emptyText());
  }

  protected String emptyText() {
    //noinspection ConstantConditions
    return getResources().getString(R.string.no_items);
  }

  @Override public void showContent(MODEL data) {
    super.showContent(data);
    contentview.setRefreshing(false);
  }

  @Override public void showError(@Nullable Throwable error) {
    super.showError(error);
    contentview.setRefreshing(false);
  }

  @Override protected void showFullErrorMessage(String message) {
    errorTextView.setText(message);
  }

  @Override public void showEmpty() {
    super.showEmpty();
    contentview.setRefreshing(false);
  }

  @NonNull @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.refreshable_multi_state_view, container, false);
  }

  @Override public void showLoading() {
    super.showLoading();
    contentview.setRefreshing(false);
  }
}
