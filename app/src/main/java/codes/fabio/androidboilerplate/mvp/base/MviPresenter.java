package codes.fabio.androidboilerplate.mvp.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

import static codes.fabio.androidboilerplate.util.RxUtils.unsubscribe;
import static java.util.Objects.requireNonNull;

/**
 * A {@linkplain BasePresenter} that can be used with MVI Pattern for building fully reactive
 * unidirectional view layers.
 *
 * <p>
 * The methods {@link #bindIntents()} and {@link #unbindIntents()} are kind of representing the
 * <ul>
 * <li>{@link #bindIntents()} is called the first time the view is attached </li>
 * <li>{@link #unbindIntents()} is called once the view is detached permanently because the view
 * has
 * been destroyed and hence this presenter is not needed anymore and will also be destroyed
 * afterwards too.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This means that a presenter can survive orientation changes. During orientation changes (or when
 * the view is put on the back stack because the user navigated to another view) the view
 * will be detached temporarily and reattached to the presenter afterwards. To avoid memory leaks
 * this Presenter class offers two methods:
 * <ul>
 * <li>{@link #intent(ViewIntentBinder)}</li>: Use this to bind an Observable intent from the view
 * to an internal relay
 *
 * <li>{@link #subscribeViewState(Observable, ViewStateConsumer)}: Use this to bind the ViewState
 * that holds all the data the view needs to display to an internal relay</li>
 * </ul>
 *
 * By using {@link #intent(ViewIntentBinder)} and {@link #subscribeViewState(Observable,
 * ViewStateConsumer)}}
 * a relay will be established between the view and this presenter that allows the view to be
 * temporarily detached, without unsubscribing the underlying reactive business logic workflow and
 * without causing memory leaks (caused by recreation of the view).
 * </p>
 *
 * <p>
 * Please note that the methods {@link #attachView(RenderableView)}} and {@link
 * #detachView(boolean)}
 * should not be overridden unless you have a really good reason to do so. Usually {@link
 * #bindIntents()} and {@link #unbindIntents()} should be enough.
 * </p>
 */
public abstract class MviPresenter<V extends RenderableView<VS>, VS extends ViewState>
    implements BasePresenter<V> {

  /**
   * Bind an internal relay to the observable from a view "action" or "intent"
   */
  public interface ViewIntentBinder<V, I> {
    Observable<I> bind(V view);
  }

  /**
   * Bind an internal viewState relay to the view. This "renders" the view
   */
  public interface ViewStateConsumer<V, VS> {
    void accept(V view, VS viewState);
  }

  private boolean viewAttachedFirstTime = true;

  /**
   * This relay is the bridge to the viewState observable. It is subscribed to during {@linkplain
   * #attachView(RenderableView)} and unsubscribed from during {@linkplain #detachView(boolean)} to
   * avoid memory leaks
   *
   * Whenever the viewState get's reattached, the latest cached state will be re-emitted to the
   * view since this is a {@linkplain BehaviorRelay}.
   */
  @SuppressWarnings("WeakerAccess") BehaviorRelay<VS> viewStateInternalRelay;

  /**
   * A {@linkplain ViewStateConsumer} which gets subscribed to the {@linkplain
   * #viewStateInternalRelay} every time the view is attached and unsubscribed when the view is
   * detached to avoid memory leaks
   */
  @SuppressWarnings("WeakerAccess") ViewStateConsumer<V, VS> viewStateConsumer;

  /**
   * Internal relays to hold the link between a view's intent binders to the actual
   * viewStateObservable
   */
  private List<Pair<PublishRelay<?>, ViewIntentBinder<V, ?>>> intentInternalRelayBinderPairs;

  /**
   * The subscription of the stream from the viewState {@linkplain Observable} to the internal
   * {@linkplain BehaviorRelay}
   *
   * @see #viewStateInternalRelay
   */
  private Subscription viewStateObservableToInternalRelaySubscription;

  /**
   * The subscription of the stream from the internal viewState relay to the viewState consumer
   * passed during {@link #subscribeViewState(Observable, ViewStateConsumer)} ()}
   *
   * @see #viewStateConsumer
   */
  private Subscription viewStateInternalRelayToViewStateConsumerSubscription;

  /**
   * The subscription of the stream from the view's intent observables to the internal intent
   * relays
   *
   * @see #intentInternalRelayBinderPairs
   */
  private CompositeSubscription intentInternalRelaysSubscriptions;

  public MviPresenter() {
    Timber.d("MviPresenter: Creating Presenter");
    viewStateInternalRelay = BehaviorRelay.create();
    intentInternalRelaysSubscriptions = Subscriptions.from();
    intentInternalRelayBinderPairs = new ArrayList<>();
  }

  @Override @CallSuper public void attachView(@NonNull final V view) {
    // If this is the first time we are attaching the view then we bind the intents
    if (viewAttachedFirstTime) {
      Timber.d("attachView: First time attaching view %s and binding intents.", view);
      bindIntents();
    }

    Timber.d("attachView: subscribing internal relay binders with size: %d",
        intentInternalRelayBinderPairs.size());

    for (Pair<PublishRelay<?>, ViewIntentBinder<V, ?>> intentBinderPair : intentInternalRelayBinderPairs) {
      subscribeInternalIntentRelayToIntentBinders(intentBinderPair, view);
    }

    Timber.d("attachView: subscribing viewStateConsumer to internalViewStateRelay ");
    // Subscribe the viewStateConsumer to the internalViewStateRelay stream
    subscribeViewStateConsumerToInternalViewStateRelay(view);

    // Not the first time anymore
    viewAttachedFirstTime = false;
  }

  private void subscribeViewStateConsumerToInternalViewStateRelay(@NonNull final V renderableView) {

    requireNonNull(viewStateConsumer);

    viewStateInternalRelayToViewStateConsumerSubscription = viewStateInternalRelay.subscribe(vs -> {
      Timber.d("calling viewStateConsumer from viewStateInternalRelay: %s", vs);
      viewStateConsumer.accept(renderableView, vs);
    });
  }

  private <I> void subscribeInternalIntentRelayToIntentBinders(
      @NonNull Pair<PublishRelay<?>, ViewIntentBinder<V, ?>> intentBinderPair,
      @NonNull V renderableView) {

    Timber.d("subscribeInternalIntentRelayToIntentBinders: %s, %s", intentBinderPair,
        renderableView);

    //noinspection unchecked
    final PublishRelay<I> relay = (PublishRelay<I>) intentBinderPair.first;
    //noinspection unchecked
    ViewIntentBinder<V, I> intentBinder = ((ViewIntentBinder<V, I>) intentBinderPair.second);
    Observable<I> boundIntent = intentBinder.bind(renderableView);

    requireNonNull(boundIntent, "Your bound intent is null!!!");
    intentInternalRelaysSubscriptions.add(boundIntent.subscribe(value -> {
      Timber.d("calling internalIntentRelay with value from boundIntent: %s:", value);
      relay.call(value);
    }));
  }

  /**
   * This method is called one the view is attached for the very first time to this presenter.
   * It will not called again for instance during screen orientation changes when the view will be
   * detached temporarily.
   *
   * <p>
   * The counter part of this method is  {@link #unbindIntents()}.
   * This method and {@link #unbindIntents()} are kind of representing the
   * lifecycle of this Presenter.
   *
   * This is called the first time the view is attached {@link #unbindIntents()} is called once the
   * view is detached permanently because it has been destroyed and hence this presenter is not
   * needed anymore and will also be destroyed afterwards
   * </p>
   */
  protected void bindIntents() {
  }

  /**
   * This method will be called once the view has been detached permanently and hence the presenter
   * will be "destroyed" too. This is the correct time for doing some cleanup like unsubscribe from
   * some RxSubscriptions etc.
   *
   * * <p>
   * The counter part of this method is  {@link #bindIntents()} ()}.
   * This {@link #bindIntents()} and this method are kind of representing the
   * lifecycle of this Presenter.
   *
   * {@link #bindIntents()} is called the first time the view is attached
   * and this method is called once the view is detached permanently because it has
   * been destroyed and hence this presenter is not needed anymore and will also be destroyed
   * afterwards
   * </p>
   */
  protected void unbindIntents() {
  }

  /**
   * This method creates a decorator around the original view's "intent". This method ensures that
   * no memory leak by using a {@link ViewIntentBinder} is caused by the subscription to the
   * original view's intent when the view gets detached.
   *
   * @param viewIntentBinder The {@link ViewIntentBinder} from where the the real view's intent
   * will be bound
   * @param <I> The type of the intent
   * @return The decorated intent Observable emitting the intent
   */
  protected <I> Observable<I> intent(@NonNull ViewIntentBinder<V, I> viewIntentBinder) {
    PublishRelay<I> intentRelay = PublishRelay.create();
    intentInternalRelayBinderPairs.add(Pair.create(intentRelay, viewIntentBinder));
    return intentRelay;
  }

  /**
   * This method subscribes the Observable emitting {@code ViewState} over time to the passed
   * consumer.
   *
   * <b>Do only invoke this method once! Typically in {@link #bindIntents()}</b>
   *
   * <p>
   * Internally Mosby will hold some relays to ensure that no items emitted from the ViewState
   * Observable will be lost while viewState is not attached nor that the subscriptions to
   * viewState
   * intents will cause memory leaks while viewState detached.
   * </p>
   *
   * @param viewStateObservable The Observable emitting new ViewState. Typically an intent {@link
   * #intent(ViewIntentBinder)} causes the underlying business logic to do a change and eventually
   * create a new ViewState.
   * @param viewStateConsumer {@link ViewStateConsumer} The consumer that will update ("render")
   * the view.
   */
  protected void subscribeViewState(@NonNull Observable<VS> viewStateObservable,
      @NonNull ViewStateConsumer<V, VS> viewStateConsumer) {

    Timber.d("subscribeViewState: ViewStateConsumer is %s", viewStateConsumer);
    this.viewStateConsumer = viewStateConsumer;

    viewStateObservableToInternalRelaySubscription = viewStateObservable.subscribe(vs -> {
      Timber.d("Calling viewStateInternalRelay from viewStateObservable with newestState: %s", vs);
      viewStateInternalRelay.call(vs);
    }, error -> {
      throw new IllegalStateException("The viewStateObservable cannot throw any exceptions!!!");
    }, () -> {
      // Ignore complete events
    });
  }

  @Override @CallSuper public void detachView(boolean retainPresenter) {
    // If the presenter is not being retained
    // Unsubscribe the stream viewStateObservable -> viewStateInternalRelay
    Timber.d("detachView: Detaching View");
    if (!retainPresenter) {
      Timber.d(
          "detachView: Not retaining presenter. Unsubscribing viewStateObservable to internalRelay stream");
      unsubscribe(viewStateObservableToInternalRelaySubscription);
    }

    // Unbind any other intents
    Timber.d("detachView: Unbinding Any intents");
    unbindIntents();

    // Unsubscribe the streams intentObservable -> internalRelay
    Timber.d("detachView: Clearing internal intentRelays");
    intentInternalRelaysSubscriptions.clear();

    // Unsubscribe the stream viewStateInternalRelay -> viewStateConsumer
    Timber.d("detachView: Unsubscribing the viewStateConsumer from the internalRelay");
    unsubscribe(viewStateInternalRelayToViewStateConsumerSubscription);
  }
}
