package com.github.nilstrieb.recommendationbot.commands.reco;

import com.github.nilstrieb.recommendationbot.core.command.Command;
import com.github.nilstrieb.recommendationbot.db.Neo4jConnection;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class SubCommand extends Command {


    public SubCommand() {
        super("subscribe");
        setAlias("sub");
        setAlias("s");

    }

    @Override
    public void called(String args) {
        String uid = event.getAuthor().getId();

        List<Member> mentions = event.getMessage().getMentionedMembers();

        if (mentions.size() < 1) {
            reply("Mention at least one member");
            return;
        }

        Neo4jConnection connection = Neo4jConnection.getInstance();
        String target = mentions.get(0).getId();

        if (connection.isSubbed(uid, target)) {
            reply("You are already subscribed to the user");
            return;
        }

        connection.createSub(uid, target);
    }
}
