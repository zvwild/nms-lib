package dev.zvwild.nmslib.api.chat;

/**
 * Version independent action bar functionality
 */
public interface ActionBarTransmitter {

    /**
     * Send an action bar to a player
     *
     * @param receiver the player
     * @param text     the text shown in the action bar
     */
    void send(Object receiver, String text);

}
