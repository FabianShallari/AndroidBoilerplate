package codes.fabio.androidboilerplate.di;

import com.bluelinelabs.conductor.Controller;
import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom Dagger2 Scope to indicate that dependencies are injected once per a {@link Controller}
 */
@Retention(RUNTIME) @Scope public @interface PerController {
}
