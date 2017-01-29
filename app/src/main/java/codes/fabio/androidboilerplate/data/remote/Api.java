package codes.fabio.androidboilerplate.data.remote;

import codes.fabio.androidboilerplate.data.model.ExampleModel;
import retrofit2.http.GET;
import rx.Observable;

// TODO: 1/29/17 Make Real Api Endpoints
public interface Api {

  @GET("exampleModel") Observable<ExampleModel> getModel();
}
