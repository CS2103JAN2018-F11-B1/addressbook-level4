package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Oauth2Client;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Allows a user to login to their LinkedIn account
 */
public class LinkedInLoginCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "linkedin_login";
    public static final String MESSAGE_FAILED_LOGIN = "Unable to login, please try again";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in to your LinkedIn account ";

    public static final String MESSAGE_SUCCESS = "Browser Opened for LinkedIn Authentication";

    /**
     * Creates a LinkedInLogin log a Salesperson in to LinkedIn
     */
    public LinkedInLoginCommand() throws IOException {
        try {
            Oauth2Client.authenticateWithLinkedIn();
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
