package creoii.origin.data;

import com.google.gson.Gson;
import creoii.origin.player.Class;

import java.io.Reader;

public class ClassBuilder extends DataBuilder<Class> {
    public ClassBuilder() {
        super("classes");
    }

    @Override
    Class getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Class.class);
    }
}
