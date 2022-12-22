package rolex.object;

// mock
public class Identity extends RolexObject {

    private String name;

    public Identity() {
        super();
    }

    public Identity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Identity setName(String name) {
        this.name = name;
        return this;
    }
}
