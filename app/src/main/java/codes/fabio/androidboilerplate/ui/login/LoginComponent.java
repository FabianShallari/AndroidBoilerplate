package codes.fabio.androidboilerplate.ui.login;

import codes.fabio.androidboilerplate.app.ApplicationComponent;
import codes.fabio.androidboilerplate.di.PerController;
import dagger.Component;

@PerController @Component(dependencies = ApplicationComponent.class) interface LoginComponent {

  void inject(LoginController loginController);
}
