package creoii.originofchaos.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.originofchaos.item.Item;

import java.io.Reader;

public class ItemBuilder extends AbstractDataBuilder<Item> {
    public ItemBuilder() {
        super("items", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Item.class, new Item.Serializer()).create());
    }

    @Override
    Item getObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, Item.class);
    }
}
