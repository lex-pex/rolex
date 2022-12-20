package tasks.roller.models;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgRoleModel extends RoleModel {

	public OrgRoleModel() {
		super();
		this.setType(RoleTypeEnum.Organizational);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrgRoleModel other = (OrgRoleModel) obj;
		return Objects.equals(this.getType(), other.getType());
	}

	@Override
	public String toString() {
		return "\n" + this.getClass().getName() +" {\n    "
				+ "roleName : " + getName() + ",\n    "
				+ "displayName : " + getDisplayName() + ",\n    "
				+ "roleOwner : " + getOwner() + ",\n    "
				+ "description : " + getDescription() + ",\n    "
				+ "inheritedRoleName : " + getInheritedRoleName() + "\n}";
	}
}
