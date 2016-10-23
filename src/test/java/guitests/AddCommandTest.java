package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import taskle.commons.core.Messages;
import taskle.logic.commands.AddCommand;
import taskle.logic.commands.RemindCommand;
import taskle.logic.parser.DateParser;
import taskle.model.task.Task;
import taskle.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        Task[] currentList = td.getTypicalTasks();
        Task taskToAdd = td.helpFriend;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.interview;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(AddCommand.COMMAND_WORD + " " 
                + td.helpFriend.getName());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        currentList = new Task[0];
        taskToAdd = td.attendMeeting;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //valid deadline add command
        taskToAdd = td.finalExams;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //Invalid event add format
        commandBox.runCommand("add watch movie with friends by 7pm to 9pm");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, 
                AddCommand.MESSAGE_USAGE));
        
    }
    
    @Test
    public void add_float_task_with_reminders_success() {
//        Task[] currentList = td.getTypicalTasks();
//        String remindDate = "15 Oct 7pm";
//        Date date = DateParser.parse(remindDate).get(0);
//        Task taskToAdd = td.helpFriend;
//        taskToAdd.setRemindDate(date);
//        assertAddSuccess(taskToAdd, currentList);
        
        commandBox.runCommand(AddCommand.COMMAND_WORD + " Buy Groceries for home " 
                + RemindCommand.COMMAND_WORD + " 15 Oct 7pm");
        assertResultMessage("New task added: Buy Groceries for home Reminder on: 7:00PM, 15 Oct 2016");

    }
    private void assertAddSuccess(Task taskToAdd, Task... currentList) {
        commandBox.runCommand(AddCommand.COMMAND_WORD + " "
                + taskToAdd.toString());
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        System.out.println(addedCard.getDetails());
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        Task[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
