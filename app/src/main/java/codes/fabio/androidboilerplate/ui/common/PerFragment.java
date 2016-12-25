package codes.fabio.androidboilerplate.ui.common;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom Dagger2 Scope for better semantics
 */
@Retention(RUNTIME) @Scope public @interface PerFragment {
}
