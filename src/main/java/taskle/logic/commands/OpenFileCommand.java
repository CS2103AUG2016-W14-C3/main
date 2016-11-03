package taskle.logic.commands;

import java.io.File;

import taskle.commons.core.Config;
import taskle.commons.exceptions.DataConversionException;
import taskle.commons.util.ConfigUtil;
import taskle.commons.util.StorageUtil;

//@@author A0140047U
//Opens data from specified file
public class OpenFileCommand extends Command {
    
    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reads data from specified file.\n"
            + "Format: open file_path\n"
            + "Example: " + COMMAND_WORD + " C:" + File.separator + "Users" + File.separator + "John"
            + File.separator + "desktop" + File.separator + "taskle.xml";

    public static final String MESSAGE_SUCCESS = "Storage File has been changed.";
    
    public static final String MESSAGE_FAILURE = "Failed to open file.";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Invalid file format detected. Unable to open file";
    public static final String MESSAGE_SAME_FILE = "You are already viewing the requested file.";
    
    private final File file;
    
    public OpenFileCommand(String filePath) {
        this.file = new File(filePath);
    }
    
    @Override
    public CommandResult execute() {
        try {
            if (isSameFile()) {
                return new CommandResult(MESSAGE_FAILURE, false);
            }
            
            StorageUtil.storeConfig(true);
            if (StorageUtil.updateFile(file)) {
                return new CommandResult(MESSAGE_SUCCESS, true);
            } else {
                indicateAttemptToExecuteIncorrectCommand(MESSAGE_INVALID_FILE_FORMAT);
                StorageUtil.resolveConfig();
                return new CommandResult(MESSAGE_FAILURE, false);
            }
        } catch (DataConversionException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_FAILURE, false);
        }
        
    }

    public boolean isSameFile() throws DataConversionException {
        Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
        if (config.getTaskManagerFilePath().equalsIgnoreCase(file.getAbsolutePath())) {
            indicateAttemptToExecuteIncorrectCommand(MESSAGE_SAME_FILE);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
