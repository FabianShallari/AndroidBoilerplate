package codes.fabio.androidboilerplate.mvp.extensions;

import android.support.v7.widget.RecyclerView;
import java.lang.ref.WeakReference;
import timber.log.Timber;

import static java.lang.Math.max;

/**
 * A {@link RecyclerView.OnScrollListener} which gets attached a {@link InfiniteScrollingView} and
 * is responsible to listen to the scrolling of that view and call {@link
 * InfiniteScrollingView#loadMoreData()} when it is appropriate that the view loads more data
 *
 * Use this to implement paging in an infinite scrolling fashion
 */
public class InfiniteScrollingViewListener extends RecyclerView.OnScrollListener {

  private WeakReference<InfiniteScrollingView> view;
  private final int indexOffsetThreshold;

  /**
   * Create a new listener with the desired indexOffsetThreshold
   *
   * @param indexOffsetThreshold the offset of the index of the view (from the last view) that
   * should trigger a {@link InfiniteScrollingView#loadMoreData()} operation.
   *
   * Basically this is the number of positions from the lastview's position that when scrolled to
   * triggers the next paging operation.
   *
   * For example: in a list with 10 items and an indexOffsetThreshold of 3, when the list is
   * scrolled to the ((10 - 1) - 3) = 6th index (7th item) the next paging is triggered
   */
  private InfiniteScrollingViewListener(int indexOffsetThreshold) {
    this.indexOffsetThreshold = indexOffsetThreshold;
  }

  /**
   * Create a new listener with an indexOffsetThreshold equal to 4
   */
  public InfiniteScrollingViewListener() {
    this(4);
  }

  public void attachView(InfiniteScrollingView infiniteScrollingView) {
    this.view = new WeakReference<>(infiniteScrollingView);
  }

  private boolean isViewAttached() {
    return view != null && view.get() != null;
  }

  public InfiniteScrollingView getView() {
    return view.get();
  }

  public void detachView() {
    if (this.view != null) {
      this.view.clear();
      this.view = null;
    }
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    if (isViewAttached()) {

      int lastVisibleItemIndex = getView().lastVisibleItemPosition();

      int contentCount = getView().currentContentCount();

      // itemIndexThreshold represents the index of the item that should be scrolled to in order to
      // trigger the loading more operation
      // It can be indexOffsetThreshold indexes from the content count or 0 if for some reason
      // the content count is initially smaller than the indexOffsetThreshold
      int itemIndexThreshold = max((contentCount - 1) - indexOffsetThreshold, 0);

      Timber.d("onScrolled: lastVisibleItem :%d \t contentCount: %d \t itemIndexThreshold: %d",
          lastVisibleItemIndex, contentCount, itemIndexThreshold);

      if (lastVisibleItemIndex == itemIndexThreshold) {
        Timber.d("onScrolled: lastVisible item is same as threshold: %d", lastVisibleItemIndex);
      }

      if (lastVisibleItemIndex >= contentCount - 1) {
        Timber.d("onScrolled: lastVisibleItem is last item");
      }

      Timber.d("onScrolled: view is loading end: %s", getView().isLoadingMoreData());
      Timber.d("onScrolled: view has more data: %s", getView().canLoadMore());

      // IF the view is NOT already loading background data and still has data to load then check
      // IF we have SCROLLED TO OR SCROLLED PAST the itemIndexThreshold
      //  (lastVisibleItemIndex >= itemIndexThreshold)
      if (getView().canLoadMore()
          && !getView().isLoadingMoreData()
          && lastVisibleItemIndex >= itemIndexThreshold) {
        Timber.d("onScrolled: Calling Loading More");
        getView().loadMoreData();
      }
    }
  }
}
