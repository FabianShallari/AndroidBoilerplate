package codes.fabio.androidboilerplate.ui.login;

import android.support.annotation.NonNull;
import codes.fabio.androidboilerplate.mvp.base.RenderableView;
import rx.Observable;

interface LoginView extends RenderableView<LoginViewState> {
  @NonNull Observable<Boolean> login();

  @NonNull Observable<String> username();

  @NonNull Observable<String> password();
}
