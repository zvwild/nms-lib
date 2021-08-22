package dev.zvwild.nmslib.impl.chat;

import dev.zvwild.nmslib.api.NMS;
import dev.zvwild.nmslib.api.chat.ActionBarTransmitter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Implementation class, don't use directly
 */
public final class $v1_16_R3ActionBarTransmitter implements ActionBarTransmitter {

    private static final String ONLY_TEXT_FORMAT = "{\"text\": \"%s\"}";
    private static final UUID PACKET_UUID_PARAM = new UUID(0, 0);

    private final Constructor<?> packetConstructor;
    private final Method chatSerializerSerialize;
    private final Object gameInfoVariant;

    public $v1_16_R3ActionBarTransmitter() {
        Constructor<?> packetConstructor = null;
        Method chatSerializerSerializeMethod = null;
        Object gameInfoVariant = null;

        try {
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server.v1_16_R3.ChatMessageType");
            packetConstructor = Class.forName("net.minecraft.server.v1_16_R3.PacketPlayOutChat")
                    .getDeclaredConstructor(
                            Class.forName("net.minecraft.server.v1_16_R3.IChatBaseComponent"),
                            chatMessageTypeClass,
                            UUID.class
                    );

            gameInfoVariant = chatMessageTypeClass.getDeclaredField("GAME_INFO").get(null);

            chatSerializerSerializeMethod = Class.forName("net.minecraft.server.v1_16_R3.IChatBaseComponent$ChatSerializer")
                    .getDeclaredMethod("a", String.class);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.packetConstructor = packetConstructor;
        this.chatSerializerSerialize = chatSerializerSerializeMethod;
        this.gameInfoVariant = gameInfoVariant;
    }

    private Object createChatComponentRaw(String raw) {
        try {
            return chatSerializerSerialize.invoke(null, raw);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object createChatComponent(String text) {
        return createChatComponentRaw(String.format(ONLY_TEXT_FORMAT, text));
    }

    @Override
    public void send(Object receiver, String text) {
        Object component = createChatComponent(text);
        try {
            Object packet = packetConstructor.newInstance(component, gameInfoVariant, PACKET_UUID_PARAM);
            NMS.getPacketTransmitter().transmitPacket(receiver, packet);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
