package codes.fabio.androidboilerplate.ui.login;

import android.support.annotation.NonNull;
import codes.fabio.androidboilerplate.mvp.base.ViewState;

// TODO: 1/29/17 Make Real ViewState with multiple states
class LoginViewState implements ViewState {

  public final String message;

  LoginViewState(@NonNull String message) {
    this.message = message;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LoginViewState)) return false;

    LoginViewState that = (LoginViewState) o;

    return message.equals(that.message);
  }

  @Override public int hashCode() {
    return message.hashCode();
  }
}
