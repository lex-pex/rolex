package tasks.roller.models;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModel {

	private RoleTypeEnum type;
	private String name;
	private String displayName;
	private String description;
	private String inheritedRoleName;
	private String owner;

	@Override
	public int hashCode() {
		return Objects.hash(type, displayName, name, inheritedRoleName, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleModel other = (RoleModel) obj;
		return Objects.equals(this.getType(), other.getType())
				&& Objects.equals(this.name, other.getName())
				&& Objects.equals(this.displayName, other.getDisplayName())
				&& Objects.equals(this.inheritedRoleName, other.getInheritedRoleName()) 
				&& Objects.equals(this.owner, other.getOwner());
	}

	@Override
	public String toString() {
		return "\n" + this.getClass().getName() +" {\n    "
				+ "roleName : " + getName() + ",\n    "
				+ "displayName : " + getDisplayName() + ",\n    "
				+ "description : " + getDescription() + ",\n    "
				+ "inheritedRoleName : " + getInheritedRoleName() + "\n}";
	}

	public RoleTypeEnum getType() {
		return type;
	}

	public void setType(RoleTypeEnum type) {
		this.type = type;
	}

	public RoleTypeEnum getType(RoleTypeEnum type) {
		return this.type;
	}

	public String getName() {
		return name;
	}

	public void setRoleName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInheritedRoleName() {
		return inheritedRoleName;
	}

	public void setInheritedRoleName(String inheritedRoleName) {
		this.inheritedRoleName = inheritedRoleName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}

