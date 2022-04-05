package jda.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageEventListenerJDA extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        System.out.println("Message received !");
    }
}
