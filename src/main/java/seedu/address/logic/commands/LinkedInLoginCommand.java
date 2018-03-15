package seedu.address.logic.commands;

import seedu.address.commons.core.Oauth2Client;
import seedu.address.logic.commands.exceptions.CommandException;

import java.io.IOException;

/**
 * Allows a user to login to their LinkedIn account
 */
public class LinkedInLoginCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "linkedin_login";
    public static final String MESSAGE_FAILED_LOGIN = "Unable to login, please try again";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in to your LinkedIn account ";
    //TODO: Add username and password as parameter

    public static final String MESSAGE_SUCCESS = "Successfully logged in";

    /**
     * Creates a LinkedInLogin log a Salesperson in to LinkedIn
     */
    public LinkedInLoginCommand() throws IOException {
        try {
            Oauth2Client.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            //TODO: login to linkedIn via API
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILED_LOGIN);
        }
    }
}
