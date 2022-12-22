package tasks.roller.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BirthrightsRoleModel extends RoleModel {

	// Inner ISS Market inner Extended Attribute Key  
	public final static String marketAlias = "issIAM_roleMarket";

	private String market;
	private List<String> requirements;
	private  String assignmentRuleName;

	public BirthrightsRoleModel() {
		super();
		this.setType(RoleTypeEnum.Birthrights);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(market, requirements, assignmentRuleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BirthrightsRoleModel other = (BirthrightsRoleModel) obj;
		return Objects.equals(this.getType(), other.getType())
				&& Objects.equals(this.market, other.getMarket())
				&& Objects.equals(this.requirements, other.getRequirements())
				&& Objects.equals(this.assignmentRuleName, other.getAssignmentRuleName());
	}

	public void addRequirement(String itRoleName) {
		if(requirements==null)
			requirements=new ArrayList<>();
		requirements.add(itRoleName);
	}

	@Override
	public String toString() {
		return "\n" + this.getClass().getName() +" {\n    "
				+ "roleName : " + getName() + ",\n    "
				+ "displayName : " + getDisplayName() + ",\n    "
				+ "roleOwner : " + getOwner() + ",\n    "
				+ "description : " + getDescription() + ",\n    "
				+ "inheritedRoleName : " + getInheritedRoleName() + ",\n    "
				+ "market : " + getMarket() + ",\n    "
				+ "requirements : " + requirements + "\n    "
				+ "assignmentRuleName : " + assignmentRuleName + "\n}";
	}

	public static String getMarketAlias() {
		return marketAlias;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	public String getAssignmentRuleName() {
		return assignmentRuleName;
	}

	public void setAssignmentRuleName(String assignmentRuleName) {
		this.assignmentRuleName = assignmentRuleName;
	}
}
