package tasks.roller.util;


import java.util.List;
import java.util.Map;

public interface CsvReader {
	// Retrieve the table from the csv-file to the listed table of maps 
	public List<Map<String,String>> getListedTable();
}
