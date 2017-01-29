package codes.fabio.androidboilerplate.data.model;

import codes.fabio.androidboilperplate.data.db.ModelModel;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.squareup.sqldelight.RowMapper;

@AutoValue public abstract class ExampleModel implements ModelModel {

  public static final Factory<ExampleModel> FACTORY = new Factory<>(ExampleModel::create);
  public static final RowMapper<ExampleModel> SELECT_ALL_MAPPER = FACTORY.selectAllMapper();

  @Override @SerializedName("example_value") public abstract long value();

  public static TypeAdapter<ExampleModel> typeAdapter(Gson gson) {
    return new AutoValue_ExampleModel.GsonTypeAdapter(gson);
  }

  public static ExampleModel create(long exampleValue) {
    return new AutoValue_ExampleModel(exampleValue);
  }
}
