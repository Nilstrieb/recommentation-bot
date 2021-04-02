package com.github.nilstrieb.recommendationbot.commands.info;


import com.github.nilstrieb.recommendationbot.core.command.Command;

public class RebootCommand extends Command {
    public RebootCommand() {
        super("reboot");
        setHidden();
    }

    @Override
    public void called(String args) {
        long id = event.getMember().getUser().getIdLong();
        if (id == 414755070161453076L) {
            event.getTextChannel().sendMessage("Rebooting!").queue();
            System.exit(409);
        } else {
            event.getTextChannel().sendMessage("Sorry, you don't seem to have sufficient Permissions for this!").queue();
        }
    }
}
