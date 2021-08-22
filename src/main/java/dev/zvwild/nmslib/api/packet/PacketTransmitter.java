package dev.zvwild.nmslib.api.packet;

/**
 * Send a packet without any information about the server version
 */
public interface PacketTransmitter {

    /**
     * Send a packet
     *
     * @param receiver the player to receive the packet
     * @param packet   the packet
     */
    void transmitPacket(Object receiver, Object packet);

}
