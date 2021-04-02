package com.github.nilstrieb.recommendationbot.commands.info;


import com.github.nilstrieb.recommendationbot.cofig.Config;
import com.github.nilstrieb.recommendationbot.core.command.Command;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.text.NumberFormat;
import java.util.Locale;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info");
        setDescription("Info about me!");
    }

    @Override
    public void called(String args) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);


        MessageEmbed embed = Config.getDefaultEmbed()
                .setTitle("Info about me")
                .setDescription("Get recommendations for things from you friends and people who like the same things!")
                .setFooter("Version v" + Config.VERSION).build();
        reply(embed);
    }
}
