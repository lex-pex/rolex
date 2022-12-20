package rolex.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// mock
public class Batch extends RolexObject {

    private String name = "";
    private String type = "";
    private Identity owner = new Identity("mock");
    private String displayName = "";

    private final Map<String, String> descriptionMap = new HashMap<>();
    private final Map<String, Object> attributes = new HashMap<>();

    private IdentitySelector identitySelector;

    private List<Batch> inheritance = new ArrayList<>();
    private List<Batch> requirements = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Identity getOwner() {
        return this.owner;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription(String local) {
        return descriptionMap.get(local);
    }

    // Setter is deprecated
    public Batch addDescription(String local, String description) {
        descriptionMap.put(local, description);
        return this;
    }

    public List<Batch> getInheritance() {
        return null;
    }

    public void setInheritance(List<Batch> inheritance) {
        this.inheritance = inheritance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplayName(Identity owner) {
        this.owner = owner;
    }

    public void setOwner(Identity owner) {
        this.owner = owner;
    }

    // mock
    public Map<String, Object> getExtendedAttributes() {
        return new HashMap<>();
    }

    public Object setAttribute(String key, Object value) {
        return attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public List<Batch> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Batch> requirements) {
        this.requirements = requirements;
    }

    public IdentitySelector getSelector() {
        return this.identitySelector;
    }

    public void setSelector(IdentitySelector is) {
        this.identitySelector = is;
    }

    public List<Profile> getProfiles() {
        return new ArrayList<>();
    }

    public void assignProfiles(List<Profile> newProfiles) {
        this.profiles.addAll( newProfiles );
    }
}
