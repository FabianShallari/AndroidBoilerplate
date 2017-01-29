package codes.fabio.androidboilerplate.mvp.extensions;

import codes.fabio.androidboilerplate.mvp.base.BaseView;

/**
 * A {@link BaseView} that can scroll infinitely.
 *
 * This is useful to implement if you want to implement paging in an infinite scrolling fashion
 * Use together with {@link InfiniteScrollingViewListener}
 *
 * @see InfiniteScrollingViewListener
 */
public interface InfiniteScrollingView extends BaseView {

  void loadMoreData();

  int currentContentCount();

  int lastVisibleItemPosition();

  boolean isLoadingMoreData();

  boolean canLoadMore();

  void setLoadingMore(boolean loadingEnd);

  void setCanLoadMore(boolean hasMoreData);
}
