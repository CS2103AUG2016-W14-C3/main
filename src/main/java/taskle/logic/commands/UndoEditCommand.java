package taskle.logic.commands;

import taskle.model.Model;
import taskle.model.task.Task;
import taskle.model.task.UniqueTaskList;
import taskle.model.task.UniqueTaskList.DuplicateTaskException;
import taskle.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * UndoEditCommand to handle undo of edit commands
 * @author Muhammad Hamsyari
 *
 */
public class UndoEditCommand extends UndoCommand {
    
    public UndoEditCommand() {}
    
    /**
     * Undo Edit Command by editing tasks to its original content
     * @return feedback of undo operation
     */
    public CommandResult undoEdit(Command command, Model model) {
        assert command != null;
        
        Task task = command.getTasksAffected().get(0);
        EditCommand editCommand = (EditCommand) command;
        try {
            model.editTask(editCommand.getIndex(), task.getName());
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
        }
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, command.getCommandWord(), command.getTasksAffected().get(0).toString()));
    }
    
    @Override
    public String getCommandWord() {
        return EditCommand.COMMAND_WORD;
    }
}
