package taskle.model.task;

import java.util.Date;

import taskle.commons.util.DateFormatUtil;
import taskle.model.tag.UniqueTagList;

/**
 * Deadline task object that guarantees non-null fields for task
 * and nullable field for deadlineDate.
 * @author Abel
 *
 */
public class DeadlineTask extends Task {
    
    private Date deadlineDate;

    public DeadlineTask(Name name, Date deadlineDate, UniqueTagList tags) {
        super(name, tags);
        this.deadlineDate = deadlineDate;
    }

    public DeadlineTask(ReadOnlyTask source) {
        super(source);
        if (source instanceof DeadlineTask) {
            deadlineDate = ((DeadlineTask) source).getDeadlineDate();
        }
        
    }

    public DeadlineTask(ModifiableTask source) {
        super(source);
    }

    @Override
    public String getDetailsString() {
        return DateFormatUtil.formatDate(deadlineDate);
    }
    
    public Date getDeadlineDate() {
        return deadlineDate;
    }

    @Override
    public Task copy() {
        return new DeadlineTask((ReadOnlyTask) this);
    }

}
