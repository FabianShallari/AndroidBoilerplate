package codes.fabio.androidboilerplate.mvp.conductorbase;

import codes.fabio.androidboilerplate.mvp.base.MviPresenter;
import codes.fabio.androidboilerplate.mvp.base.RenderableView;
import codes.fabio.androidboilerplate.mvp.base.ViewState;

/**
 * A {@linkplain BaseDaggerController} that should be used with MVI pattern as it enforces generics
 * that are subtypes of {@linkplain RenderableView} {@linkplain MviPresenter} and also renders a
 * type of {@linkplain ViewState}
 *
 * @param <C> dagger component to inject dependencies from
 * @param <V> view interface to implement
 * @param <P> presenter
 * @param <VS> viewState to render
 */
public abstract class BaseRenderableController<C, V extends RenderableView<VS>, P extends MviPresenter<V, VS>, VS extends ViewState>
    extends BaseDaggerController<C, V, P> implements RenderableView<VS> {

}
