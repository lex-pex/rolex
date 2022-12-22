package tasks.roller.util;

import java.util.Map;

import tasks.roller.models.RoleModel;

public interface CsvConverter {
	// Retrieve the roles from listed table of maps 
	public Map<String, RoleModel> getModels();
}
