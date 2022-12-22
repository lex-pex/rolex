package tasks.roller.util;

import java.util.HashMap;
import java.util.Map;

import tasks.roller.models.BirthrightsRoleModel;
import tasks.roller.models.ITRoleModel;
import tasks.roller.models.RoleModel;
import tasks.roller.models.OrgRoleModel;
import tasks.roller.validators.RollerValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CsvConverterImpl implements CsvConverter {

	private final CsvReader csvReader;
	private final RollerValidator validator;
	
	private Map<String, RoleModel> models = new HashMap<>();
	private static final String IT_START_CONTAINER = "Application";
	
	// Converts the table records into Role Models 
	public Map<String, RoleModel> getModels() {

		if( validator.validate(csvReader) )
			csvReader.getListedTable().stream().forEach(row -> processTableRow(row));
		return models;
	}

	// Parse the csv-file line accordingly by headers 
	private void processTableRow( Map<String, String> row ) {

		// IT-roles structure 
		String itRoleFirstLevelContainerString = IT_START_CONTAINER;
		String itRoleSecondLevelContainerString = row.get(FileHeaderEnum.applicationName.toString());
		String itRoleName = row.get(FileHeaderEnum.itRole.toString());
		// BR-roles structure 
		String brRoleFirstLevelContainerString = row.get(FileHeaderEnum.roleType.toString());
		String brRoleSecondLevelContainerString = row.get(FileHeaderEnum.Market.toString());
		String brRoleName = row.get(FileHeaderEnum.roleName.toString());
		// IT-first org 
		if (models.get(itRoleFirstLevelContainerString) == null) {
			OrgRoleModel orgModel = new OrgRoleModel();
			orgModel.setRoleName(itRoleFirstLevelContainerString);
			orgModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
			models.put(itRoleFirstLevelContainerString, orgModel);
		}
		// IT-second org 
		if (models.get(itRoleSecondLevelContainerString) == null) {
			if(itRoleSecondLevelContainerString != null && !itRoleSecondLevelContainerString.trim().equals("")) {
				OrgRoleModel orgModel = new OrgRoleModel();
				orgModel.setRoleName(itRoleSecondLevelContainerString);
				orgModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
				orgModel.setInheritedRoleName(itRoleFirstLevelContainerString);
				models.put(itRoleSecondLevelContainerString, orgModel);
			}
		}
		// BR-first org 
		if (models.get(brRoleFirstLevelContainerString) == null) {
			OrgRoleModel orgModel = new OrgRoleModel();
			orgModel.setRoleName(brRoleFirstLevelContainerString);
			orgModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
			models.put(brRoleFirstLevelContainerString, orgModel);
		}
		// BR-second org 
		if (models.get(brRoleSecondLevelContainerString) == null) {
			OrgRoleModel orgModel = new OrgRoleModel();
			orgModel.setRoleName(brRoleFirstLevelContainerString + "-" + brRoleSecondLevelContainerString);
			orgModel.setDisplayName(brRoleSecondLevelContainerString);
			orgModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
			orgModel.setInheritedRoleName(brRoleFirstLevelContainerString);
			models.put(brRoleSecondLevelContainerString, orgModel);
		}
		// IT-role 
		if (models.get(itRoleName) == null) {
			if(itRoleName != null && !itRoleName.trim().equals("")) {
				ITRoleModel itRoleModel = new ITRoleModel();
				itRoleModel.setRoleName(itRoleName);
				itRoleModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
				itRoleModel.setInheritedRoleName(itRoleSecondLevelContainerString);
				String entitlement = row.get(FileHeaderEnum.entitlement.toString());
				itRoleModel.setEntitlement(entitlement);
				String property = row.get(FileHeaderEnum.Property.toString());
				itRoleModel.setProperty(property);
				itRoleModel.setFilterExpression(property + ".containsAll({\"" + entitlement + "\"})");
				itRoleModel.setApplicationName(row.get(FileHeaderEnum.applicationName.toString()));
				models.put(itRoleName, itRoleModel);
			}
		}
		// BR-role 
		if (models.get(brRoleName) == null) {
			BirthrightsRoleModel birthrightsModel = new BirthrightsRoleModel();
			birthrightsModel.setRoleName(brRoleName);
			birthrightsModel.setOwner(row.get(FileHeaderEnum.roleOwner.toString()));
			birthrightsModel.setDescription(row.get(FileHeaderEnum.description.toString()));
			birthrightsModel.setMarket(row.get(FileHeaderEnum.Market.toString()));
			birthrightsModel.setInheritedRoleName(brRoleFirstLevelContainerString + "-" + brRoleSecondLevelContainerString);
			birthrightsModel.setAssignmentRuleName(row.get(FileHeaderEnum.assignmentRuleName.toString()));
			birthrightsModel.addRequirement(itRoleName);
			models.put(brRoleName, birthrightsModel);
		} else {
			BirthrightsRoleModel birthrightsModel = (BirthrightsRoleModel) models.get(brRoleName);
			if(itRoleName != null && !itRoleName.trim().equals(""))
				birthrightsModel.addRequirement(itRoleName);
		}
	}

	public CsvConverterImpl(CsvReader csvReader, RollerValidator validator) {
		this.csvReader = csvReader;
		this.validator = validator;
	}
}



