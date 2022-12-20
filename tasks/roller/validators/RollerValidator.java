package tasks.roller.validators;

import java.util.List;
import java.util.Map;

import tasks.roller.util.CsvReader;
import rolex.tools.Message;

public interface RollerValidator {
	public void validateRowItems(Map<String, String> row);
	public List<Message> getErrorMessages();
	public boolean validate(CsvReader csvReader);
}
