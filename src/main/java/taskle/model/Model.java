package taskle.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import taskle.commons.core.UnmodifiableObservableList;
import taskle.model.task.Name;
import taskle.model.task.ReadOnlyTask;
import taskle.model.task.Task;
import taskle.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Edits the given task. */
    void editTask(int index, Name newName) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /** Edits the date / time of the task */
    void editTaskDate(int index, List<Date> dates) throws UniqueTaskList.TaskNotFoundException;
   
    //@@author A0125509H
    /** Marks the task as done*/
    void doneTask(int index, boolean targetDone) throws UniqueTaskList.TaskNotFoundException;
    //@@author
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    //@@author A0140047U
    /** Stores current TaskManager state */
    void storeTaskManager();
    
    /** Restores most recently stored TaskManager state */
    boolean restoreTaskManager();
    
    /** Undo most recently restored TaskManager state */
    boolean revertTaskManager();
    
    //@@author
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show tasks that are not done*/
    void updateFilteredListToShowAllNotDone();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
