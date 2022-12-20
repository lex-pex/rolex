package rolex.task;

import rolex.api.RolexContext;
import rolex.object.*;

public abstract class AbstractTaskExecutor {

    public abstract void execute(RolexContext context, TaskSchedule ts, TaskResult res, Attributes<String, Object> attr) throws Exception;

    public abstract boolean terminate();
}
