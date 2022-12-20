package tasks.starter;

import rolex.api.RolexContext;
import rolex.object.Attributes;
import rolex.object.TaskResult;
import rolex.object.TaskSchedule;
import rolex.task.AbstractTaskExecutor;
import tasks.roller.RollerExecutor;

// entry scaffolding
public class Main {

    private AbstractTaskExecutor exe;
    private RolexContext context;
    private TaskSchedule ts = new TaskSchedule();
    private TaskResult res = new TaskResult();
    private Attributes<String, Object> attr = new Attributes<>();

    // environment simulation launcher
    public static void main(String... args) {
        new Main().start();
    }
    {
        exe = new RollerExecutor();
        context = new RolexContext();
        ts = new TaskSchedule();
        res = new TaskResult();
        attr = new Attributes<>();
    }
    private void start() {
        try {
            this.exe.execute(context, ts, res, attr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
