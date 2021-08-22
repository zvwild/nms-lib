package dev.zvwild.nmslib.api.chat;

import dev.zvwild.nmslib.api.NMS;
import dev.zvwild.nmslib.impl.chat.$v1_16_R3ActionBarTransmitter;
import dev.zvwild.nmslib.impl.chat.$v1_8_R3ActionBarTransmitter;

/**
 * Supplier for action bar functionality
 */
public final class ActionBars {

    private static final ActionBarTransmitter TRANSMITTER;

    static {
        ActionBarTransmitter transmitter = null;

        switch (NMS.getVersion()) {
            case v1_8_R3:
                transmitter = new $v1_8_R3ActionBarTransmitter();
                break;

            case v1_16_R3:
                transmitter = new $v1_16_R3ActionBarTransmitter();
                break;

            default:
                break;
        }

        TRANSMITTER = transmitter;
    }

    private ActionBars() {
    }

    /**
     * Get an instance of an ActionBarTransmitter for this server version
     *
     * @return the appropriate {@link ActionBarTransmitter} for this server version
     */
    public static ActionBarTransmitter getTransmitter() {
        return TRANSMITTER;
    }

}
