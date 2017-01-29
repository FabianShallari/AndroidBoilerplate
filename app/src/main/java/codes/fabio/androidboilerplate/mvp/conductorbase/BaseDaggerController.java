package codes.fabio.androidboilerplate.mvp.conductorbase;

import android.support.annotation.NonNull;
import android.view.View;
import codes.fabio.androidboilerplate.mvp.base.BasePresenter;
import codes.fabio.androidboilerplate.mvp.base.BaseView;
import javax.inject.Inject;

/**
 * Boilerplate for a controller which createas a dagger component, injects
 * dependencies among which the presenter, and attaches and detaches itself from a {@link
 * BasePresenter}
 *
 * @param <C> Dagger Component which is responsible for injecting dependencies into this controller
 * @param <V> View interface that is implemented by this controller
 */
public abstract class BaseDaggerController<C, V extends BaseView, P extends BasePresenter<V>>
    extends BaseController {

  protected C component;

  protected abstract C createComponent();

  protected abstract void injectDependencies();

  @Inject protected P presenter;

  @Override protected void onViewCreated(View view) {
    super.onViewCreated(view);
    if (component == null) {
      component = createComponent();
      injectDependencies();
    }
  }

  @Override protected void onAttach(@NonNull View view) {
    super.onAttach(view);
    //noinspection unchecked
    presenter.attachView((V) this);
  }

  @Override protected void onDetach(@NonNull View view) {
    super.onDetach(view);
    presenter.detachView(isRetainingPresenter());
  }

  private boolean isRetainingPresenter() {
    return !isBeingDestroyed();
  }
}
