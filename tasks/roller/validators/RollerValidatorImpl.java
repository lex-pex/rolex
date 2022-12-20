package tasks.roller.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rolex.object.*;
import tasks.roller.util.CsvReader;
import tasks.roller.util.FileHeaderEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rolex.api.RolexContext;
import rolex.object.Patcher;
import rolex.tools.GeneralException;
import rolex.tools.Message;

@Getter
@RequiredArgsConstructor
public class RollerValidatorImpl implements RollerValidator {

	private final RolexContext context;
	private final TaskResult taskResult;

	public RollerValidatorImpl(RolexContext context, TaskResult taskResult) {
		this.context = context;
		this.taskResult = taskResult;
	}

	private List<Message> errorMessages;
	private List<Message> warningMessages;
	private List<Message> infoMessages;

	// Validated Objects Pools
	private List<String> validatedOwners = new ArrayList<>();
	private List<String> validatedAssignmentsRule = new ArrayList<>();
	private List<String> validatedEntitlements = new ArrayList<>();
	private List<String> validatedApplications = new ArrayList<>();

	public boolean validate(CsvReader csvReader) {
		// Process CsvReader's table
		csvReader.getListedTable().stream().forEach(row -> validateRowItems(row));
		// Validation Fail Logic 
		if (getErrorMessages() != null && ! getErrorMessages().isEmpty()) {
			taskResult.setMessages(getErrorMessages());
			taskResult.setCompletionStatus(TaskResult.CompletionStatus.Error);
			taskResult.setTerminated(true);
			return false;
		}
		return true;
	}

	public void validateRowItems(Map<String, String> row) {
		validateOwner(row.get(FileHeaderEnum.roleOwner.toString()));
		String application = row.get(FileHeaderEnum.applicationName.toString());
		String entitlement = row.get(FileHeaderEnum.entitlement.toString());
		String property = row.get(FileHeaderEnum.Property.toString());
		String rule = row.get(FileHeaderEnum.assignmentRuleName.toString());
		if(application != null && !application.equals("")) {
			validateApplication(application);
			if(entitlement != null && !entitlement.equals(""))
				validateEntitlement(entitlement, property, application);
		}
		if(rule != null && !rule.equals(""))
			validateAssignmentRule(rule);
	}

	@Override
	public List<Message> getErrorMessages() {
		return this.errorMessages;
	}

	private void addErrorMessage(String message) {
		Message msg = new Message();
		msg.setType(Message.MessageType.Error);
		msg.setKey(message);
		if (errorMessages == null)
			errorMessages = new ArrayList<>();
		errorMessages.add(msg);
	}

	private void validateOwner(String ownerName) {
		if (!validatedOwners.contains(ownerName)) {
			Filter filter = Filter.eq("name", ownerName);
			QueryOptions qo = new QueryOptions(filter);
			int i = 0;
			try {
				i = context.countObjects(Identity.class, qo);
			} catch (GeneralException e) {
				addErrorMessage("Error getting identity from DB " + e.getMessage());
			}
			if (i == 0) {
				addErrorMessage("Role owner does not exists for the name " + ownerName);
			} else {
				validatedOwners.add(ownerName);
			}
		}
	}

	private void validateEntitlement(String entitlementName, String property, String application) {
		if (!validatedEntitlements.contains(entitlementName)) {
			try {
				boolean failToFindEntitlement = true;
				List<ManagedAttribute> manageAttributes = context.getObjects(ManagedAttribute.class);
				for(ManagedAttribute manageAttribute : manageAttributes) 
					if(manageAttribute.getValue().equals(entitlementName))
						 if (manageAttribute.getAttribute().equals(property)) { // "memberOf" Entitlement-property check 
						validatedEntitlements.add(entitlementName);
						failToFindEntitlement = false;
					}
				if(failToFindEntitlement)
					addErrorMessage("Managed Attribute\""+ entitlementName +"\" does not exists in application " + application);
			} catch (GeneralException e) {
				addErrorMessage("Error getting entitlement from DB " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void validateAssignmentRule(String assignmentRule) {
		if (!validatedAssignmentsRule.contains(assignmentRule)) {
			Filter filter = Filter.eq("name", assignmentRule);
			QueryOptions qo = new QueryOptions(filter);
			int i = 0;
			try {
				i = context.countObjects(Patcher.class, qo);
			} catch (GeneralException e) {
				addErrorMessage("Error getting assignment rule from DB " + e.getMessage());
			}
			if (i == 0) {
				addErrorMessage("Patcher " + assignmentRule + " does not exists");
			} else {
				validatedAssignmentsRule.add(assignmentRule);
			}
		}
	}

	private void validateApplication(String applicationName) {
		if (!validatedApplications.contains(applicationName)) {
			Filter filter = Filter.eq("name", applicationName);
			QueryOptions qo = new QueryOptions(filter);
			int i = 0;
			try {
				i = context.countObjects(Application.class, qo);
			} catch (GeneralException e) {
				addErrorMessage("Error getting application from DB " + e.getMessage());
			}
			if (i == 0) {
				addErrorMessage("Application " + applicationName + " does not exists");
			} else {
				validatedApplications.add(applicationName);
			}
		}
	}

	public List<Message> getWarningMessages() {
		return warningMessages;
	}

	public List<Message> getInfoMessages() {
		return infoMessages;
	}
}
