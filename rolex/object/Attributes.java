package rolex.object;

import java.util.Map;
import java.util.HashMap;

// mock
public class Attributes<S, O> extends RolexObject {

    Map<String, Object> attr = new HashMap<>();

    // mock
    public String getString(String key) {
        return (String) attr.get(key);
    }

}
