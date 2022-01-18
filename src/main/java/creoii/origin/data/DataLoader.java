package creoii.origin.data;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static final List<AbstractDataBuilder<?>> BUILDERS = new ArrayList<>();
    private boolean complete = true;

    public DataLoader() {
        ItemBuilder itemBuilder = new ItemBuilder();
        if (!itemBuilder.isLoaded()) complete = false;
    }

    public boolean isComplete() {
        return complete;
    }
}
