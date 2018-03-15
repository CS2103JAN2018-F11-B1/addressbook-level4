package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Oauth2Client;
import seedu.address.commons.events.ui.ShowBrowserRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Allows a user to login to their LinkedIn account
 */
public class LinkedInLoginCommand extends Command {
    public static final String COMMAND_WORD = "linkedin_login";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in to your LinkedIn account ";

    public static final String MESSAGE_SUCCESS = "Browser Opened for LinkedIn Authentication";

    /**
     * Creates a LinkedInLogin log a Salesperson in to LinkedIn
     */
    public LinkedInLoginCommand(){

    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowBrowserRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
