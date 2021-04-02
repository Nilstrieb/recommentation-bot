package com.github.nilstrieb.recommendationbot.commands.reco;

import com.github.nilstrieb.recommendationbot.core.command.Command;
import com.github.nilstrieb.recommendationbot.db.Neo4jConnection;

public class RecommendCommand extends Command {

    public RecommendCommand() {
        super("recommend");
        setAlias("reco");
        setAlias("r");
        setDescription("Recommend something!");
        setArguments("<name>");
        setExampleUsage("recommend hunter x hunter");
    }

    @Override
    public void called(String args) {
        String uid = event.getAuthor().getId();

        Neo4jConnection.getInstance().createRecommendation(uid, args);
    }
}
