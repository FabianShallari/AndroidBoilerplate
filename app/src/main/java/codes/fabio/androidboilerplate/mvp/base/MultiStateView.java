package codes.fabio.androidboilerplate.mvp.base;

import android.support.annotation.Nullable;

/**
 * A {@link BaseView} that assumes that there are 4 display operations:
 *
 * {@link #showLoading()}: Display a loading animation while loading data in background
 * by invoking the corresponding presenter method
 *
 * {@link #showError(Throwable)}: Display a error view (a TextView) on the screen if
 * an error has occurred while loading data.
 *
 * {@link #showEmpty()}: Display an empty view (a TextView or an ImageView depending on the
 * implementation) if there are no data in the underlying data model
 *
 * {@link #showContent(M)} ()}: After the content has been loaded the presenter calls {@link
 * #setData(M)} to fill the view with data.
 * Afterwards, the presenter calls {@link #showContent(M)} to display the data
 *
 * @param <M> The underlying data model
 */

public interface MultiStateView<M> extends BaseView {

  void showContent(M data);

  void showEmpty();

  void showError(@Nullable Throwable error);

  void showLoading();

  void setData(M data);

  void loadData(boolean refresh);
}
