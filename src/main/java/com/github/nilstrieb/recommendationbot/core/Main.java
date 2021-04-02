package com.github.nilstrieb.recommendationbot.core;

import com.github.nilstrieb.recommendationbot.cofig.Config;
import com.github.nilstrieb.recommendationbot.cofig.Secrets;
import com.github.nilstrieb.recommendationbot.commands.info.HelpCommand;
import com.github.nilstrieb.recommendationbot.commands.info.InfoCommand;
import com.github.nilstrieb.recommendationbot.commands.info.InviteCommand;
import com.github.nilstrieb.recommendationbot.commands.info.RebootCommand;
import com.github.nilstrieb.recommendationbot.commands.reco.RecommendCommand;
import com.github.nilstrieb.recommendationbot.commands.reco.SubCommand;
import com.github.nilstrieb.recommendationbot.core.command.CommandListener;
import com.github.nilstrieb.recommendationbot.core.reactions.ReactionEventListener;
import com.github.nilstrieb.recommendationbot.core.sections.ChannelMessageListener;
import com.github.nilstrieb.recommendationbot.db.Neo4jConnection;
import com.github.nilstrieb.recommendationbot.listeners.StartUpListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder =
                JDABuilder.createDefault(Secrets.BOT_TOKEN);

        builder.setCompression(Compression.ZLIB);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setAutoReconnect(true);

        builder.setActivity(Activity.listening(Config.PREFIX + "help"));
        builder.setDisabledIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);
        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ROLE_TAGS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOTE, CacheFlag.VOICE_STATE);
        builder.addEventListeners(
                new StartUpListener(),
                new ChannelMessageListener(),
                new CommandListener(),
                new ReactionEventListener()
        );


        JDA jda = builder.build();
        Config.setJda(jda);
        setupCommands();
        Neo4jConnection.create("bolt://localhost:7687", "neo4j", Secrets.NEO4J_PW);
    }

    private static void setupCommands() {
        new HelpCommand();
        new InviteCommand();
        new InfoCommand();
        new RebootCommand();
        new RecommendCommand();
        new SubCommand();
    }
}
