package tasks.roller.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRoleModel extends RoleModel {

	private String market;
	private List<String> requirements;
	private  String assignmentRuleName;

	public BusinessRoleModel() {
		super.setType( RoleTypeEnum.Birthrights );
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
				&& Objects.equals(this.assignmentRuleName, other.getAssignmentRuleName()) ;
	}

	public void addRequirement(String itRoleName) {
		if(requirements==null)
			requirements=new ArrayList<>();
		requirements.add(itRoleName);
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
