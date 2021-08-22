package dev.zvwild.nmslib.api.packet;

/**
 * Version independent factory for packets
 */
public interface PacketFactory {

    /**
     * Create an instance of PacketPlayOutEntityDestroy
     *
     * @param entityIds the entities to destroy
     * @return the packet
     */
    Object createPacketPlayOutEntityDestroy(int... entityIds);

    /**
     * Create an instance of PacketPlayOutPlayerInfo
     *
     * @param action  the action to execute
     * @param handles the players on which the action should be performed
     * @return the packet
     */
    Object createPacketPlayOutPlayerInfo(PlayerInfoAction action, Object... handles);

    /**
     * Create an instance of PacketPlayOutNamedEntitySpawn
     *
     * @param handle the player for whom to create the packet
     * @return the packet
     */
    Object createPacketPlayOutNamedEntitySpawn(Object handle);

    /**
     * Create an instance of PacketPlayOutPlayerListHeaderFooter
     *
     * @param header the tab header
     * @param footer the tab footer
     * @return the packet
     */
    Object createPacketPlayOutPlayerListHeaderFooter(String header, String footer);

    /**
     * Wrapper around EnumPlayerInfoAction, intended to be used with {@link #createPacketPlayOutPlayerInfo(PlayerInfoAction, Object[])}
     */
    enum PlayerInfoAction {
        ADD_PLAYER,
        REMOVE_PLAYER
    }

}
