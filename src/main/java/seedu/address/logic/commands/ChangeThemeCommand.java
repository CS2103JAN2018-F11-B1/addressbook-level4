package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Exception.InputThemeEqualsCurrentThemeException;
import seedu.address.model.Theme;

//@@author A0155428B
/**
 * Changes theme of CRM Book to the specified theme.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "changetheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes to the specified theme.\n"
            + "Example: " + COMMAND_WORD + " light";

    public static final String MESSAGE_CHANGE_THEME_SUCCESS =
            "Theme changed to %1$s. Please restart to effect the change.";
    public static final String MESSAGE_CHANGE_THEME_FAIL = "Current theme is %1$s.";
    private final Theme targetTheme;

    public ChangeThemeCommand(Theme targetTheme) {
        this.targetTheme = targetTheme;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(targetTheme);
        try {
            model.updateTheme(targetTheme.theme);
        } catch (InputThemeEqualsCurrentThemeException e) {
            return new CommandResult(String.format(MESSAGE_CHANGE_THEME_FAIL, targetTheme));
        }
        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS, targetTheme));
    }
}
