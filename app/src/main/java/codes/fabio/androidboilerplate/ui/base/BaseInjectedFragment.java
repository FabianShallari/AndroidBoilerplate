package codes.fabio.androidboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import javax.inject.Inject;

/**
 * Boilerplate for injecting bundle arguments, creating and dagger component, injecting
 * dependencies, and attaching and detaching itself from a {@link BasePresenter}
 *
 * @param <C> Dagger Component
 * @param <V> View
 * @param <P> Presenter
 */
public abstract class BaseInjectedFragment<C, V extends BaseView, P extends BasePresenter<V>>
    extends BaseFragment {

  protected C component;
  @Inject P presenter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectArguments(getArguments());
    component = createComponent();
  }

  protected void injectArguments(@Nullable Bundle arguments) {
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injectComponent();
    presenter.attachView(((V) this));
  }

  protected abstract C createComponent();

  protected abstract void injectComponent();

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.detachView();
  }
}
