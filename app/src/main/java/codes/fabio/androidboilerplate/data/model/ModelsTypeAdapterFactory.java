package codes.fabio.androidboilerplate.data.model;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by fabian on 1/29/17.
 */

@GsonTypeAdapterFactory public abstract class ModelsTypeAdapterFactory
    implements TypeAdapterFactory {

  public static TypeAdapterFactory create() {
    return new AutoValueGson_ModelsTypeAdapterFactory();
  }

}
