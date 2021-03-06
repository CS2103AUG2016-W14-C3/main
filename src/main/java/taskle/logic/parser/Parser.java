package taskle.logic.parser;

import static taskle.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static taskle.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import taskle.logic.commands.Command;
import taskle.logic.commands.HelpCommand;
import taskle.logic.commands.IncorrectCommand;

//@@author A0141780J

/**
 * Parses user input.
 */
public class Parser {

    /** Used for initial separation of command word and args. */
    private static final Pattern BASIC_COMMAND_FORMAT = 
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
        
    private List<CommandParser> commandParsers;

    public Parser() {
        setupParsers();
    }

    /**
     * Sets up the list of command parsers required.
     * All commands have to implement a command parser and instantiate it
     * in this method for parser to parse it as a command.
     */
    private void setupParsers() {
        // Generate a list of command parsers here, every new 
        // command added must be added to the commandParsers list here
        commandParsers = new ArrayList<>(
                Arrays.asList(new AddCommandParser(),
                              new RemoveCommandParser(),
                              new EditCommandParser(),
                              new FindCommandParser(),
                              new ListCommandParser(),
                              new UndoCommandParser(),
                              new RedoCommandParser(),
                              new DoneCommandParser(),
                              new HelpCommandParser(),
                              new ClearCommandParser(),
                              new ExitCommandParser(),
                              new RescheduleCommandParser(),
                              new RemindCommandParser(),
                              new OpenFileCommandParser(),
                              new ChangeDirectoryCommandParser()));
        
        /** Parse a date using date parser on start up to reduce
         * command delay on first parse. (Natty library constraints) */
        DateParser.parse("today");
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                                  HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return prepareCommand(commandWord, arguments);
    }
    
    /**
     * Prepares commmand based on command word and arguments.
     * 
     * @param commandWord command word from user input.
     * @param args arguments after command word from user input.
     * @return The corresponding command to the command word after parsing. 
     */
    private Command prepareCommand(String commandWord, String args) {
        if (commandWord == null || commandWord.isEmpty()) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        
        for (CommandParser commandParser : commandParsers) {
            if (commandParser.canParse(commandWord)) {
                return commandParser.parseCommand(args);
            }
        }
        
        return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
    }

}
