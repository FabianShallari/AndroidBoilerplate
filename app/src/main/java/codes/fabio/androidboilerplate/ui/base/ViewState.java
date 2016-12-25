package codes.fabio.androidboilerplate.ui.base;

/**
 * A ViewState is, like the name suggests, responsible to store the views state. In other words:
 * The view like an Activity or a Fragment stores its state, like "showing loading animation",
 * "showing error view" etc. The goal is to have a views that can easily restore their state after
 * screen orientation changes (from portrait to landscape and vice versa) by using a ViewState
 * and the well defined View interfaces.
 *
 * The idea is to call the same methods the presenter would call to restore the view's state.
 *
 * @param <V> The type of the View (extends {@link BaseView}
 */
interface ViewState<V extends BaseView> {
  void applyViewState(V view);
}
