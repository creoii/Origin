package creoii.origin.data;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static final List<AbstractDataBuilder<?>> BUILDERS = new ArrayList<>();
    public static final ItemBuilder ITEMS = new ItemBuilder();
    public static final ClassBuilder CLASSES = new ClassBuilder();
    private boolean complete = true;

    public DataLoader() {
        if (!ITEMS.isLoaded()) complete = false;
        if (!CLASSES.isLoaded()) complete = false;
    }

    public boolean isComplete() {
        return complete;
    }
}
