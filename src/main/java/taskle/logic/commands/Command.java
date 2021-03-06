package taskle.logic.commands;

import java.util.List;

import taskle.commons.core.EventsCenter;
import taskle.commons.core.Messages;
import taskle.commons.events.ui.IncorrectCommandAttemptedEvent;
import taskle.model.Model;
import taskle.model.TaskManager;
import taskle.model.task.Task;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected TaskManager taskManager;
    protected List<Task> tasksAffected;
    
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     * 
     * @param model Model to be used for dependency
     */
    public void setData(Model model) {
        this.model = model;
        this.taskManager = (TaskManager) model.getTaskManager();
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     * 
     * @param feedbackToUser The feedback message to be shown
     */
    protected void indicateAttemptToExecuteIncorrectCommand(String feedbackToUser) {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(feedbackToUser));
    }
    
}
