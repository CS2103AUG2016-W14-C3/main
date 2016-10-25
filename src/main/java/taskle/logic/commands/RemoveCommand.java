package taskle.logic.commands;

import java.util.ArrayList;

import taskle.commons.core.Messages;
import taskle.commons.core.UnmodifiableObservableList;
import taskle.logic.history.History;
import taskle.model.task.Task;
import taskle.model.task.ReadOnlyTask;
import taskle.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the Task identified by the index number used in the last Task listing.\n"
            + "Format: remove task_number\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Removed Task: %1$s";

    //public final int targetIndex;
    public final String targetIndex;
    int arraySize;
    String[] s;
    int[] sInt;
    
    public RemoveCommand(String targetIndex) {
    	this.targetIndex = targetIndex;
    	
    	System.out.println("Test " + targetIndex.trim());
		
    	String argsTrim = targetIndex.trim();
    		
    	s = argsTrim.split(" ");
    	for(int i=0; i<s.length; i++)
    	{
    		System.out.println(i + ": " + s[i]);
    		sInt[i] = Integer.parseInt(s[i]);
    	}
    	arraySize = s.length;
    }


    @Override
    public CommandResult execute() {
    	for(int i=0; i<arraySize; i++)
    	{
	        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
	
	        if (lastShownList.size() < sInt[i]) {
	            indicateAttemptToExecuteIncorrectCommand();
	            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	        }
	
	        ReadOnlyTask taskToDelete = lastShownList.get(sInt[i] - 1);
	
	        try {
	            model.deleteTask(taskToDelete);
	            tasksAffected = new ArrayList<Task>();
	            tasksAffected.add((Task)taskToDelete);
	            History.insert(this);
	        } catch (TaskNotFoundException pnfe) {
	            assert false : "The target task cannot be missing";
	        }
    	}

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, 2));
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

}
