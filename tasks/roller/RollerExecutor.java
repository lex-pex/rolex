package tasks.roller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import rolex.api.RolexContext;
import rolex.object.Attributes;
import rolex.object.TaskResult;
import rolex.object.TaskSchedule;
import rolex.task.AbstractTaskExecutor;

public class RollerExecutor extends AbstractTaskExecutor {

	private String spreadSheetCsvFilePath;

	@Override
	public void execute(RolexContext context, TaskSchedule ts, TaskResult res, Attributes<String, Object> attr) throws Exception {
		this.parseArguments( attr, res );
		RollerConfig.context = context;
		RollerConfig.taskResult = res;
		RollerConfig.csvPath = this.spreadSheetCsvFilePath;
		// Main Code entry point
		this.launchRolerPackage();
	}

	/**
	 * The feature library entry point
	 */
	private void launchRolerPackage() {

		AbstractApplicationContext abstractApplicationContext = 
				new AnnotationConfigApplicationContext( RollerConfig.class );

		Roller roller = abstractApplicationContext . getBean( Roller.class );

		roller.buildRoles();

		abstractApplicationContext.close();
	}

	private void parseArguments(Attributes<String, Object> args, TaskResult taskResult) {
		String path = args.getString("filePath");
		if(path == null) {
			taskResult.setCompletionStatus( TaskResult.CompletionStatus.Error );
			taskResult.addMessage( "Error setting the path to the CSV-file: File-Path = null" );
		} else {
			this.spreadSheetCsvFilePath = path;
		}
	}

	@Override
	public boolean terminate() {
		return false;
	}

}






