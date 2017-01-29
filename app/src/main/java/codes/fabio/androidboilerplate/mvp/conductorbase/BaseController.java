package codes.fabio.androidboilerplate.mvp.conductorbase;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import codes.fabio.androidboilerplate.mvp.base.BaseView;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.rxlifecycle.RxController;
import com.squareup.leakcanary.RefWatcher;
import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;
import static codes.fabio.androidboilerplate.app.BoilerplateApplication.getRefWatcher;

/**
 * A {@link BaseView} with boilerplate for binding views with {@link ButterKnife}, showing
 * messages as {@link Toast}s and watching for memory leaks with {@link RefWatcher}
 */
public abstract class BaseController extends RxController implements BaseView {

  private Unbinder unbinder;
  private boolean hasExited;

  public BaseController(Bundle bundle) {
    super(bundle);
  }

  public BaseController() {
    this(null);
  }

  @NonNull @Override
  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    Timber.d("onCreateView: %s", this);
    View view = inflateView(inflater, container);
    onViewCreated(view);
    return view;
  }

  @CallSuper protected void onViewCreated(View view) {
    unbinder = ButterKnife.bind(this, view);
  }

  @NonNull protected abstract View inflateView(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup container);

  @Override protected void onDestroyView(@NonNull View view) {
    unbinder.unbind();
    super.onDestroyView(view);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (hasExited && getApplicationContext() != null) {
      getRefWatcher(getApplicationContext()).watch(this);
    }
  }

  @Override protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler,
      @NonNull ControllerChangeType changeType) {
    super.onChangeEnded(changeHandler, changeType);

    hasExited = !changeType.isEnter;
    if (isDestroyed()) {
      getRefWatcher(getApplicationContext()).watch(this);
    }
  }

  @Override public void showMessage(String message) {
    Toast.makeText(getActivity(), message, LENGTH_SHORT).show();
  }
}
