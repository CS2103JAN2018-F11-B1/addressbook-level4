package seedu.address.logic.parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Oauth2Client;
import seedu.address.logic.commands.ShareToLinkedInCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses user input
 */
public class ShareToLinkedInCommandParser implements Parser<ShareToLinkedInCommand>{

    public ShareToLinkedInCommand parse(String args) throws ParseException {
        if(args == null || args.length() == 0){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareToLinkedInCommand.MESSAGE_USAGE));
        }
        Logger logger = LogsCenter.getLogger(Oauth2Client.class);
        logger.info("SHARE TO LINKEDIN PARSER RUN" + args);
        return new ShareToLinkedInCommand(args);

    }

}
