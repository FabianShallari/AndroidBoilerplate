package codes.fabio.androidboilerplate.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import codes.fabio.androidboilperplate.data.db.ModelModel;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton class DbOpenHelper extends SQLiteOpenHelper {

  // TODO: 1/29/17 Rename db
  private static final String DATABASE_NAME = "ExampleAppDatabase";
  private static final int DATABASE_VERSION = 1;

  @Inject public DbOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    // TODO: 1/29/17 Populate Database
    db.execSQL(ModelModel.CREATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // no-op for v1
  }

  @Override public void onConfigure(SQLiteDatabase db) {
    db.setForeignKeyConstraintsEnabled(true);
  }
}
