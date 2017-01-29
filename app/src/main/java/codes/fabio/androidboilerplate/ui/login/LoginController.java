package codes.fabio.androidboilerplate.ui.login;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import codes.fabio.androidboilerplate.R;
import codes.fabio.androidboilerplate.mvp.conductorbase.BaseRenderableController;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.Observable;

import static codes.fabio.androidboilerplate.app.BoilerplateApplication.getApplicationComponent;
import static com.bluelinelabs.conductor.Controller.RetainViewMode.RELEASE_DETACH;

public class LoginController
    extends BaseRenderableController<LoginComponent, LoginView, LoginPresenter, LoginViewState>
    implements LoginView {

  @BindView(R.id.usernameEditText) EditText usernameEditText;
  @BindView(R.id.passwordEditText) EditText passwordEditText;
  @BindView(R.id.loginButton) Button loginButton;

  @OnClick(R.id.newButton) void onNewControllerClicked() {
    getRouter().pushController(RouterTransaction.with(new LoginController())
        .pushChangeHandler(new HorizontalChangeHandler())
        .popChangeHandler(new HorizontalChangeHandler()));
  }

  @NonNull public Observable<Boolean> login() {
    return RxView.clicks(loginButton).map(ignored -> true);
    //return Observable.defer(() -> just(true));
  }

  @NonNull @Override public Observable<String> username() {
    return RxTextView.textChanges(usernameEditText)
        .map(CharSequence::toString)
        .filter(s -> !s.isEmpty());
  }

  @NonNull @Override public Observable<String> password() {
    return RxTextView.textChanges(passwordEditText)
        .map(CharSequence::toString)
        .filter(s -> !s.isEmpty());
  }

  @NonNull @Override
  protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.login_view, container, false);
  }

  @Override protected void onViewCreated(View view) {
    super.onViewCreated(view);
    setRetainViewMode(RELEASE_DETACH);
  }

  @Override protected void injectDependencies() {
    component.inject(this);
  }

  @Override protected LoginComponent createComponent() {
    return DaggerLoginComponent.builder()
        .applicationComponent(getApplicationComponent(getApplicationContext()))
        .build();
  }

  @Override public void render(LoginViewState viewState) {
    showMessage(viewState.message);
  }
}
