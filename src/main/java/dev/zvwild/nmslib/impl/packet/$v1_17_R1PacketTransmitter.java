package dev.zvwild.nmslib.impl.packet;

import dev.zvwild.nmslib.api.packet.PacketTransmitter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implementation class, don't use directly
 */
public final class $v1_17_R1PacketTransmitter implements PacketTransmitter {

    private final Field PLAYER_CONNECTION_FIELD;
    private final Method GET_HANDLE_METHOD;
    private final Method SEND_PACKET_METHOD;

    public $v1_17_R1PacketTransmitter() {
        Field playerConnectionField = null;
        Method getHandleMethod = null;
        Method sendPacketMethod = null;

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer");
            getHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");

            Class<?> entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
            playerConnectionField = entityPlayerClass.getDeclaredField("b");

            Class<?> playerConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
            sendPacketMethod = playerConnectionClass.getDeclaredMethod("sendPacket", Class.forName("net.minecraft.network.protocol.Packet"));
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        PLAYER_CONNECTION_FIELD = playerConnectionField;
        GET_HANDLE_METHOD = getHandleMethod;
        SEND_PACKET_METHOD = sendPacketMethod;
    }

    @Override
    public void transmitPacket(Object receiver, Object packet) {
        try {
            Object handle = GET_HANDLE_METHOD.invoke(receiver);
            Object playerConnection = PLAYER_CONNECTION_FIELD.get(handle);
            SEND_PACKET_METHOD.invoke(playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
