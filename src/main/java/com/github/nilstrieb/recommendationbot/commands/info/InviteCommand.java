package com.github.nilstrieb.recommendationbot.commands.info;

import com.github.nilstrieb.recommendationbot.cofig.Config;
import com.github.nilstrieb.recommendationbot.core.command.Command;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class InviteCommand extends Command {

    public InviteCommand() {
        super("invite");
        setDescription("Get the invite link for this bot");
    }

    @Override
    public void called(String args) {

        MessageEmbed builder = Config.getDefaultEmbed()
                .setTitle("Invite me!")
                .setDescription("[Click here](https://www.youtube.com/watch?v=dQw4w9WgXcQ)").build();
        reply(builder);
    }
}
