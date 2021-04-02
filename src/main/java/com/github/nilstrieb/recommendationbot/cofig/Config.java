package com.github.nilstrieb.recommendationbot.cofig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.awt.*;

public class Config {

    public static final String PREFIX = "r-";
    public static final Color DEFAULT_COLOR = new Color(40, 38, 38);
    public static final String VERSION = "0.1.0";
    public static long thisId = 0;
    private static JDA jda;

    private static final String GUILD_COUNT_FILE_NAME = "guild_count.txt";

    private static int guildCount;

    public static void setJda(JDA jda) {
        Config.jda = jda;
    }

    public static EmbedBuilder getDefaultEmbed() {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Config.DEFAULT_COLOR).
                setThumbnail(jda.getSelfUser().getAvatarUrl());
        return builder;
    }
}
