package taskle.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import taskle.commons.core.LogsCenter;
import taskle.commons.exceptions.DuplicateDataException;
import taskle.commons.util.CollectionUtil;
import taskle.ui.CommandBox;

import java.util.*;
import java.util.logging.Logger;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {
    
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    
    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck); 
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Edits the equivalent task in the list.
     * @param toEdit
     * @return
     */
    public void edit(int index, Name newName) throws UniqueTaskList.DuplicateTaskException {
        Task toEdit = internalList.get(index - 1);
        FloatTask testTask = new FloatTask(toEdit);
        testTask.setName(newName);
        if(contains(testTask)) {
            throw new DuplicateTaskException();
        }
        toEdit.setName(newName);
        internalList.set(index - 1, toEdit);
        logger.info("Task " + index + " edited to " + newName);
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }
    
    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}