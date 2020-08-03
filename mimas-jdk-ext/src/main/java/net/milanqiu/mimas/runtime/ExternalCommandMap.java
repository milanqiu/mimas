package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.lang.runnable.RunnableWithExceptionAndReturn;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A map of commands with command name as key and command execution as value.
 * Used to execute a command by name and announce to external.
 * <p>
 * Creation Date: 2020-02-19
 * @author Milan Qiu
 */
public class ExternalCommandMap {

    private Map<String, RunnableWithExceptionAndReturn<String>> commands = new HashMap<>();

    /**
     * Puts the specified command into map.
     * @param commandName the command name
     * @param commandExecution the command execution, should not be null
     */
    public void put(String commandName, RunnableWithExceptionAndReturn<String> commandExecution) {
        Objects.requireNonNull(commandExecution, "command execution should not be null");
        commands.put(commandName, commandExecution);
    }

    /**
     * Executes a command by name, and then announces to external.
     * If the command is executed successfully, it will announce the returned result.
     * If the command is failed to be executed, it will announce the thrown exception.
     * @param commandName the name of command to be executed
     * @param announcementDir the directory to put the announcement file
     * @throws IOException if an I/O error occurs when announcing
     */
    public void executeAndAnnounce(String commandName, File announcementDir) throws IOException {
        if (!commands.containsKey(commandName))
            throw new IllegalArgumentException("command name not found: " + commandName);
        try {
            String commandResult = commands.get(commandName).run();
            RuntimeUtils.announceFinished(announcementDir, commandResult);
        } catch (Exception e) {
            RuntimeUtils.announceException(announcementDir, e);
        }
    }

    /**
     * Executes a command by name, and then announces to external.
     * If the command is executed successfully, it will announce the returned result.
     * If the command is failed to be executed, it will announce the thrown exception.
     * @param commandName the name of command to be executed
     * @param announcementDirName the name of directory to put the announcement file
     * @throws IOException if an I/O error occurs when announcing
     */
    public void executeAndAnnounce(String commandName, String announcementDirName) throws IOException {
        executeAndAnnounce(commandName, new File(announcementDirName));
    }

    /**
     * Executes a command by name, and then announces to external.
     * If the command is executed successfully, it will announce the returned result.
     * If the command is failed to be executed, it will announce the thrown exception.
     * The command name and announcement directory are fetched from a string array.
     * @param args with first element is the name of command to be executed, and second element is the name of directory to put the announcement file
     * @throws IOException if an I/O error occurs when announcing
     */
    public void executeAndAnnounce(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("at least two arguments are required");
        }
        executeAndAnnounce(args[0], args[1]);
    }

    /**
     * Announces one execution of command is halted.
     * Notice it doesn't mean a predictable and catchable exception in execution process, but an unpredictable exception causing halt of the execution itself.
     * @param announcementDir the directory to put the announcement file
     * @param e the unpredictable exception causing halt of the execution itself
     */
    public void announceHalted(File announcementDir, Exception e) {
        try {
            RuntimeUtils.announceHalted(announcementDir, e);
        } catch (IOException ee) {
            e.printStackTrace();
            ee.printStackTrace();
        }
    }

    /**
     * Announces one execution of command is halted.
     * Notice it doesn't mean a predictable and catchable exception in execution process, but an unpredictable exception causing halt of the execution itself.
     * @param announcementDirName the name of directory to put the announcement file
     * @param e the unpredictable exception causing halt of the execution itself
     */
    public void announceHalted(String announcementDirName, Exception e) {
        announceHalted(new File(announcementDirName), e);
    }

    /**
     * Announces one execution of command is halted.
     * Notice it doesn't mean a predictable and catchable exception in execution process, but an unpredictable exception causing halt of the execution itself.
     * The announcement directory is fetched from a string array.
     * @param args with second element is the name of directory to put the announcement file
     * @param e the unpredictable exception causing halt of the execution itself
     */
    public void announceHalted(String[] args, Exception e) {
        if (args.length < 2) {
            throw new IllegalArgumentException("at least two arguments are required");
        }
        announceHalted(args[1], e);
    }
}
