package codes.fabio.androidboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * A {@link BaseView} with boilerplate for binding views with {@link ButterKnife} and showing
 * toasts
 */
public abstract class BaseFragment extends Fragment implements BaseView {

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(shouldRetainInstance());
  }

  protected abstract boolean shouldRetainInstance();

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
  }

  @Override public void onDestroyView() {
    ButterKnife.unbind(this);
    super.onDestroyView();
  }

  @Override public void showMessage(@NonNull String string) {
    showShortToastMessage(string);
  }

  protected void showShortToastMessage(String message) {
    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
  }

  protected void showLongToastMessage(String message) {
    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
  }
}
