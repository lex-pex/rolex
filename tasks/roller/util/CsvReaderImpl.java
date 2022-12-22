package tasks.roller.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import rolex.object.TaskResult;

@RequiredArgsConstructor
public class CsvReaderImpl implements CsvReader {

	private final String csvPath;
	private final TaskResult taskResult;

	public CsvReaderImpl(String csvPath, TaskResult taskResult) {
		this.csvPath = csvPath;
		this.taskResult = taskResult;
	}

	// Retrieve the data from the csv-file into the listed table of maps
	public List<Map<String, String>> getListedTable() {
		List<Map<String, String>> maps = new ArrayList<>();
		Path path = Paths.get(csvPath);
		String[] headers = null;
		try (var s = Files.lines(path)) {
			Iterator<String> it = s.iterator();
			while(it.hasNext())
				if(headers == null)
					headers = it.next().split(",");
				else 
					put(maps, headers, it.next().split(","));
		} catch (IOException e) {
			taskResult.addMessage("Error reading the file " + e.getMessage());
			taskResult.setCompletionStatus(TaskResult.CompletionStatus.Error);
		}
		return maps;
	}

	private void put(List<Map<String, String>> listMaps, String[] headers, String[] record) {
		Map<String, String> cell = new HashMap<>();
		for (int x = 0; x < headers.length; x++)
			cell.put(headers[x], (x >= record.length) ? null : record[x]);
		listMaps.add(cell);
	}
}
