package taskle.commons.util;

import taskle.model.task.DeadlineTask;
import taskle.model.task.EventTask;
import taskle.model.task.FloatTask;
//@@author A0139402M

public class TaskUtil {
    
    /**
     * Method to return an EventTask from the given DeadlineTask
     * @param source
     * @return
     */
    public static EventTask deadlineChangeToEvent(DeadlineTask source) {
        assert source != null;
        assert source.getDeadlineDate() != null;
        
        EventTask eventTask = new EventTask(source);
        eventTask.setStartDate(source.getDeadlineDate());
        eventTask.setEndDate(source.getDeadlineDate());
        return eventTask;
    }
    
    /**
     * Method to return a FloatTAsk from the given DeadlineTask
     * @param source
     * @return
     */
    public static FloatTask deadlineChangeToFloat(DeadlineTask source) {
        assert source != null;
        return new FloatTask(source);
    }
    
    /**
     * Method to return a DeadlineTask from the given EventTask
     * @param source
     * @return DeadlineTask
     */
    public static DeadlineTask eventChangeToDeadline(EventTask source) {
        assert source != null;
        assert source.getStartDate() != null || source.getEndDate() != null;
        
        DeadlineTask deadlineTask = new DeadlineTask(source);
        deadlineTask.setDeadlineDate(source.getStartDate());
        return deadlineTask;
    }
    
    /**
     * Method to return a FloatTask from the given EventTask
     * @param source
     * @return
     */
    public static FloatTask eventChangeToFloat(EventTask source) {
        assert source != null;
        return new FloatTask(source);  
    }
    
    /**
     * Method to return a float task from the given deadline task
     * @param source
     * @return DeadlineTask
     */
    public static DeadlineTask floatChangeToDeadline(FloatTask source) {
        assert source != null;
        return new DeadlineTask(source);
    }
    
    /**
     * Method to return a float task from the given event task
     * @param source
     * @return
     */
    public static EventTask floatChangeToEvent (FloatTask source) {
        assert source != null;
        return new EventTask(source);
    }
    
}
