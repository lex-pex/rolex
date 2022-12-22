package rolex.object;

import rolex.tools.Message;

import java.util.List;

/**
 * Mock Resulting service
 */
public class TaskResult {

    public static CompletionStatusEnum CompletionStatus;

    public void addMessage( String message ) {
        // mock
    }

    public void setCompletionStatus( CompletionStatusEnum error ) {
        // mock
    }

    public void setMessages(List<Message> errorMessages) {
        // mock
    }

    public void setTerminated(boolean b) {
        // mock
    }
}

