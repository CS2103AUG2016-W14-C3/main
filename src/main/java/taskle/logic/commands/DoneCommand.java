package taskle.logic.commands;

import taskle.commons.core.Messages;
import taskle.commons.core.UnmodifiableObservableList;
import taskle.model.person.ReadOnlyTask;
import taskle.model.person.UniqueTaskList.TaskNotFoundException;

public class DoneCommand extends Command {
	
	public static final String COMMAND_WORD = "done";
	public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task (identified by the index number) as done.\n"
            + "Format: done task_number\n" + "Example: " + COMMAND_WORD + " 5";
    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task Completed!";

    public final int targetIndex;
    public final boolean targetDone;
    
    public DoneCommand(int targetIndex, boolean targetDone) {
        this.targetIndex = targetIndex;
        this.targetDone = targetDone;
    }
    
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        try {
        	model.doneTask(targetIndex, targetDone);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, "Task " + targetIndex + ": Completed"));
    }
}
	