package codes.fabio.androidboilerplate.ui.login;

import codes.fabio.androidboilerplate.data.repository.ExampleRepository;
import codes.fabio.androidboilerplate.di.PerController;
import codes.fabio.androidboilerplate.mvp.base.MviPresenter;
import javax.inject.Inject;
import rx.Observable;
import timber.log.Timber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

@PerController class LoginPresenter extends MviPresenter<LoginView, LoginViewState> {

  private final ExampleRepository exampleRepository;

  @Inject LoginPresenter(ExampleRepository exampleRepository) {
    this.exampleRepository = exampleRepository;
  }

  @Override protected void bindIntents() {

    Observable<LoginViewState> loginViewStateObservable =
        intent(LoginView::login).doOnNext(ignored -> Timber.d("Login Clicked"))
            .concatMap(ignored -> exampleRepository.modelObservable()
                .map(model -> new LoginViewState(String.valueOf(model.size())))
                .onErrorReturn(throwable -> new LoginViewState(throwable.getMessage()))
                .subscribeOn(io()))
            .observeOn(mainThread());

    subscribeViewState(loginViewStateObservable, LoginView::render);
  }
}
