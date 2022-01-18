package creoii.origin.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.origin.player.Class;

import java.io.Reader;

public class ClassBuilder extends AbstractDataBuilder<Class> {
    public ClassBuilder() {
        super("classes", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Class.class, new Class.Serializer()).create());
    }

    @Override
    Class getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Class.class);
    }
}
