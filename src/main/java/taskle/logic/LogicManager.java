package taskle.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import taskle.commons.core.ComponentManager;
import taskle.commons.core.LogsCenter;
import taskle.logic.commands.Command;
import taskle.logic.commands.CommandResult;
import taskle.logic.history.History;
import taskle.logic.parser.Parser;
import taskle.model.Model;
import taskle.model.task.ReadOnlyTask;
import taskle.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Storage storage;
    private final History history;
    
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        this.storage = storage;
        this.history = new History();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public void changeDirectory(String filePath) {
        logger.info("----------------[CHANGE DIRECTORY][" + filePath + "]");
        storage.setTaskManagerFilePath(filePath);
    }
}
