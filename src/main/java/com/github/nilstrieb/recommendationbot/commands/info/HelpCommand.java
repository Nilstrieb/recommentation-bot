package com.github.nilstrieb.recommendationbot.commands.info;

import com.github.nilstrieb.recommendationbot.core.command.Command;
import com.github.nilstrieb.recommendationbot.core.command.CommandHandler;
import net.dv8tion.jda.api.entities.MessageEmbed;


public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
        setDescription("Shows this message");
        setExampleUsage("help invite");
        setArguments("(command name)");
    }

    @Override
    public void called(String args) {
        if (args.length() == 0) {
            MessageEmbed[] helpList = CommandHandler.getHelpList();
            if (helpList.length == 1) {
                reply(helpList[0]);
            } else {
                reply(helpList);
            }
        } else {
            MessageEmbed help = CommandHandler.getCommandHelp(args);
            if (help != null) {
                reply(help);
            }
        }
    }
}