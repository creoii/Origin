package creoii.origin.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.origin.item.Item;

import java.io.Reader;

public class ItemBuilder extends DataBuilder<Item> {
    public ItemBuilder() {
        super("items");
    }

    @Override
    Item getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Item.class);
    }
}
