package com.github.nilstrieb.recommendationbot.core.command;

import com.github.nilstrieb.recommendationbot.cofig.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

/**
 * The CommandHandler handles everything about the commands
 */
public class CommandHandler {

    public static final int MAX_HELP_PAGE_LENGTH = 10;

    private static final Map<String, Command> commands = new HashMap<>();
    private static final HashSet<Command> uniqueVisibleCommands = new HashSet<>();

    private static final CommandParser parser = new CommandParser();

    /**
     * Add a new command to the handler. This is normally done by the{@code Command}.
     *
     * @param key The key (command name)
     * @param cmd The command object
     */
    static void addCommand(String key, Command cmd) {
        commands.put(key, cmd);

        if (!cmd.isHidden()) {
            uniqueVisibleCommands.add(cmd);
        }
    }

    /**
     * Add an alias for a command
     *
     * @param alias   The alias
     * @param command The command object
     */
    static void addAlias(String alias, Command command) {
        commands.put(alias, command);
    }

    /**
     * Hide a command
     *
     * @param command The command to be hidden
     */
    static void hide(Command command) {
        uniqueVisibleCommands.remove(command);
    }

    /**
     * This method is called by the{@code CommandListener}
     *
     * @param event The {@code MessageReceivedEvent}
     */
    static void call(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(Config.PREFIX)) {
            String[] split = parser.splitOffCommandName(event.getMessage().getContentRaw());
            String command = split[0];
            if (commands.containsKey(command)) {
                commands.get(command).onMessageReceived(event, split[1]);
            }
        }
    }

    /**
     * Get a list of all commands, returns many pages if there are lots of commands
     *
     * @return The pages
     */
    public static MessageEmbed[] getHelpList() {

        List<MessageEmbed> embeds = new ArrayList<>();

        EmbedBuilder builder = Config.getDefaultEmbed()
                .setTitle("Touka's Commands");

        int length = 0;
        for (Command cmd : uniqueVisibleCommands) {
            if (length == MAX_HELP_PAGE_LENGTH) {
                embeds.add(builder.build());
                builder = Config.getDefaultEmbed()
                        .setTitle("Touka's Commands");
            }

            builder.addField(Config.PREFIX + cmd.getName(), "`" + cmd.getDescription() + "`", false);
            length++;
        }
        if (builder.getFields().size() != 0) {
            embeds.add(builder.build());
        }

        MessageEmbed[] array = new MessageEmbed[embeds.size()];
        for (int i = 0; i < embeds.size(); i++) {
            array[i] = embeds.get(i);
        }
        return array;
    }


    /**
     * Returns the help page for a single command
     *
     * @param command The command name
     * @return The help page
     */
    public static MessageEmbed getCommandHelp(String command) {
        Command cmd = commands.get(command);
        if (cmd != null) {
            EmbedBuilder builder = Config.getDefaultEmbed()
                    .setTitle("Touka help for: " + Config.PREFIX + cmd.getName())
                    .addField("Name", cmd.getName(), true);


            if (!cmd.getDescription().equals("")) {
                builder.addField("Description:", cmd.getDescription(), true);
            }
            if (cmd.getExampleUsage().equals("")) {
                builder.addField("Example usage", "`" + Config.PREFIX + cmd.getName() + "`", true);
            } else {
                builder.addField("Example usage", "`" + Config.PREFIX + cmd.getExampleUsage() + "`", true);
            }
            if (!cmd.getArguments().equals("")) {
                builder.addField("Arguments", "`" + cmd.getArguments() + "`", true);
            }
            if (!cmd.getDetailDescription().equals("")) {
                builder.addField("Details:", "`" + cmd.getDetailDescription() + "`", true);
            }
            return builder.build();
        }

        return null;
    }
}
