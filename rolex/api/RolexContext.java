package rolex.api;

import rolex.object.QueryOptions;
import rolex.object.RolexObject;
import rolex.tools.GeneralException;
import java.util.ArrayList;
import java.util.List;

// mock
public class RolexContext <T> {
    // mock
    public int countObjects(Class<T> c, QueryOptions qo) throws GeneralException {
        // mock
        return 0;
    }
    // mock
    public List<T> getObjects(Class<T> cl) throws GeneralException {
        // mock
        return new ArrayList<>();
    }
    // mock
    public RolexObject getObjectByName(Class<T> cl, String name) throws GeneralException {
        // mock
        return new RolexObject();
    }
    // mock
    public void saveObject(RolexObject rolexObject) {
        // mock
    }
    // mock
    public void commitTransaction() {
        // mock
    }
}
