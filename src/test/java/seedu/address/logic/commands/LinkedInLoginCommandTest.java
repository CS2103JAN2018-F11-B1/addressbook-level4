package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import java.io.IOException;

public class LinkedInLoginCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, LinkedInLoginCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code LinkedInCommand} which upon execution,
     * does nothing for now but will eventually log a user in to LinkedIn.
     */
    private LinkedInLoginCommand prepareCommand(Model model) {
        LinkedInLoginCommand command = null;
        try {
            command = new LinkedInLoginCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
