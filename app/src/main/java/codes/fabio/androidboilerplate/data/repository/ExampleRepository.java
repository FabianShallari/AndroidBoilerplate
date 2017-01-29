package codes.fabio.androidboilerplate.data.repository;

import codes.fabio.androidboilerplate.data.model.ExampleModel;
import codes.fabio.androidboilerplate.data.preferences.PreferencesHelper;
import codes.fabio.androidboilerplate.data.remote.Api;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

import static rx.Observable.defer;
import static rx.Observable.just;

@Singleton public class ExampleRepository {

  private final Api api;
  private final BriteDatabase briteDatabase;
  private final ExampleModel.InsertRow insertRow;
  private final PreferencesHelper preferencesHelper;

  @Inject ExampleRepository(Api api, BriteDatabase briteDatabase,
      PreferencesHelper preferencesHelper) {
    this.api = api;
    this.briteDatabase = briteDatabase;
    this.preferencesHelper = preferencesHelper;
    insertRow = new ExampleModel.InsertRow(briteDatabase.getWritableDatabase());
  }

  public Observable<List<ExampleModel>> modelObservable() {
    return briteDatabase.createQuery(ExampleModel.TABLE_NAME, ExampleModel.SELECTALL, new String[0])
        .mapToList(ExampleModel.SELECT_ALL_MAPPER::map);
  }

  // TODO: 1/29/17 This kind of code should be moved to a ModelDataSource for example
  public Observable<Long> insertModel(int val) {
    insertRow.bind(val);
    return defer(
        () -> just(briteDatabase.executeInsert(ExampleModel.TABLE_NAME, insertRow.program)));
  }
}
