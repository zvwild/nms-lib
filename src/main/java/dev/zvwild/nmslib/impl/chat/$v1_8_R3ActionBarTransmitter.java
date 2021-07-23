package dev.zvwild.nmslib.impl.chat;

import dev.zvwild.nmslib.api.NMS;
import dev.zvwild.nmslib.api.chat.ActionBarTransmitter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implementation class, don't use directly
 */
public final class $v1_8_R3ActionBarTransmitter implements ActionBarTransmitter
{

    private static final byte CHAT_MESSAGE_TYPE = 2;
    private static final String ONLY_TEXT_FORMAT = "{\"text\": \"%s\"}";

    private final Constructor<?> packetConstructor;

    private final Method chatSerializerSerialize;

    public $v1_8_R3ActionBarTransmitter()
    {
        Constructor<?> packetConstructor = null;
        Method chatSerializerSerialize = null;

        try
        {
            Class<?> packetClass = Class.forName("net.minecraft.server.v1_8_R3.PacketPlayOutChat");
            Class<?> chatComponentClass = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent");

            packetConstructor = packetClass.getDeclaredConstructor(chatComponentClass, byte.class);
            chatSerializerSerialize = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer")
                    .getDeclaredMethod("a", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        this.packetConstructor = packetConstructor;
        this.chatSerializerSerialize = chatSerializerSerialize;
    }

    private Object createChatComponentRaw(String raw)
    {
        try
        {
            return chatSerializerSerialize.invoke(null, raw);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private Object createChatComponent(String text)
    {
        return createChatComponentRaw(String.format(ONLY_TEXT_FORMAT, text));
    }

    @Override
    public void send(Object receiver, String text)
    {
        Object chatComponent = createChatComponent(text);

        try
        {
            Object packet = packetConstructor.newInstance(chatComponent, CHAT_MESSAGE_TYPE);
            NMS.getPacketTransmitter().transmitPacket(receiver, packet);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

}
