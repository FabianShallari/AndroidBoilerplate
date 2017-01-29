package codes.fabio.androidboilerplate.data.remote;

import codes.fabio.androidboilerplate.data.model.ModelsTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A Dagger module that provides dependencies related to network access such as the okhttp and
 * retrofit clients, and the gson parser
 */

// TODO: 1/29/17 Move loggers to debug scope, create real api
@Module public class NetworkModule {

  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().registerTypeAdapterFactory(ModelsTypeAdapterFactory.create()).create();
  }

  @Provides @Singleton HttpLoggingInterceptor.Logger provideHttpLogger() {
    return message -> Timber.tag("OkHttp").d(message);
  }

  @Provides @Singleton HttpLoggingInterceptor provideHttpLoggingInterceptor(
      HttpLoggingInterceptor.Logger logger) {

    return new HttpLoggingInterceptor(logger).setLevel(HttpLoggingInterceptor.Level.HEADERS);
  }

  @Provides @Singleton List<Interceptor> provideInterceptors(
      HttpLoggingInterceptor httpLoggingInterceptor) {
    List<Interceptor> interceptors = new ArrayList<>();
    interceptors.add(httpLoggingInterceptor);
    return interceptors;
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(List<Interceptor> interceptorList) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(10, SECONDS)
        .writeTimeout(20, SECONDS)
        .connectTimeout(20, SECONDS);

    for (Interceptor interceptor : interceptorList) {
      builder.addInterceptor(interceptor);
    }

    return builder.build();
  }

  @Provides @Singleton Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return new Retrofit.Builder().baseUrl("https://www.example.jobs/api/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build();
  }

  @Provides @Singleton Api provideExampleApi(Retrofit retrofit) {
    return retrofit.create(Api.class);
  }
}
