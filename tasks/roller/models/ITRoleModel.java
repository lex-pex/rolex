package tasks.roller.models;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ITRoleModel extends RoleModel {

	private String applicationName;
	private String filterExpression;
	private String entitlement;
	private String property;

	public ITRoleModel() {
		super();
		this.setType( RoleTypeEnum.It );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(applicationName, filterExpression);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ITRoleModel other = (ITRoleModel) obj;
		return Objects.equals(this.getType(), other.getType())
				&& Objects.equals(this.getApplicationName(), other.getApplicationName())
				&& Objects.equals(this.getFilterExpression(), other.getFilterExpression());
	}

	@Override
	public String toString() {
		return "\n" + this.getClass().getName() +" {\n    "
				+ "roleName : " + getName() + ",\n    "
				+ "displayName : " + getDisplayName() + ",\n    "
				+ "roleOwner : " + getOwner() + ",\n    "
				+ "description : " + getDescription() + ",\n    "
				+ "inheritedRoleName : " + getInheritedRoleName() + ",\n    "
				+ "application : " + getApplicationName() + ",\n    "
				+ "filterExpression : " + getFilterExpression() + "\n    "
				+ "entitlements : " + getEntitlement() + "\n    "
				+ "property : " + getProperty() + "\n}";
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public String getEntitlement() {
		return entitlement;
	}

	public void setEntitlement(String entitlement) {
		this.entitlement = entitlement;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}


