package dev.zvwild.nmslib.api;

import dev.zvwild.nmslib.api.packet.PacketTransmitter;
import dev.zvwild.nmslib.impl.packet.$v1_8_R3PacketTransmitter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * General information holder
 */
public final class NMS
{

    private static final String VERSION_STRING;
    private static final Version VERSION;
    private static final PacketTransmitter PACKET_TRANSMITTER;

    static
    {
        String versionString = null;

        try
        {
            Class<?> bukkitClass = Class.forName("org.bukkit.Bukkit");
            Method getServerHandle = bukkitClass.getDeclaredMethod("getServer");

            boolean accessible = getServerHandle.isAccessible();
            getServerHandle.setAccessible(true);

            Object server = getServerHandle.invoke(null);
            versionString = server.getClass().getName().split("\\.")[3];

            getServerHandle.setAccessible(accessible);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        VERSION_STRING = versionString;
        VERSION = Version.fromString(versionString);

        PacketTransmitter packetTransmitter = null;
        switch (VERSION)
        {
            case v1_8_R3:
                packetTransmitter = new $v1_8_R3PacketTransmitter();
                break;

            default:
                break;
        }

        PACKET_TRANSMITTER = packetTransmitter;
    }

    private NMS()
    {
    }

    /**
     * Get this server's nms version
     * Prefer this over {@link #getVersionString()}
     *
     * @return nms version as enum variant
     */
    public static Version getVersion()
    {
        return VERSION;
    }

    /**
     * Get this server's nms version
     *
     * @return nms version as string
     */
    public static String getVersionString()
    {
        return VERSION_STRING;
    }

    /**
     * Get a packet transmitter for this version
     *
     * @return the packet transmitter
     */
    public static PacketTransmitter getPacketTransmitter()
    {
        return PACKET_TRANSMITTER;
    }

    /**
     * Used for type-safe version checking
     */
    public enum Version
    {

        v1_8_R3;

        private static Version fromString(String versionString)
        {
            return valueOf(versionString);
        }

    }

}
