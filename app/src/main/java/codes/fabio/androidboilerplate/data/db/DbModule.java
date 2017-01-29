package codes.fabio.androidboilerplate.data.db;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import timber.log.Timber;

import static rx.schedulers.Schedulers.io;

// TODO - Move Logger to debug version
@Module public class DbModule {
  @Provides @Singleton SqlBrite.Logger provideSqlBriteLogger() {
    return message -> Timber.tag("SqlBrite").d(message);
  }

  @Provides @Singleton SqlBrite provideSqlBrite(SqlBrite.Logger logger) {
    return new SqlBrite.Builder().logger(logger).build();
  }

  @Provides @Singleton BriteDatabase provideBriteDatabase(SqlBrite sqlBrite,
      DbOpenHelper dbOpenHelper) {

    return sqlBrite.wrapDatabaseHelper(dbOpenHelper, io());
  }
}
