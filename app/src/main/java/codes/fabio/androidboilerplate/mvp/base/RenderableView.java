package codes.fabio.androidboilerplate.mvp.base;

/**
 * A view that given a certain viewState handles rendering that certain state
 *
 * @param <VS> the type of the {@link ViewState}
 */
public interface RenderableView<VS extends ViewState> extends BaseView {
  void render(VS viewState);
}
