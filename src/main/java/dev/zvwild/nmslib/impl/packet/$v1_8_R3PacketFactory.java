package dev.zvwild.nmslib.impl.packet;

import dev.zvwild.nmslib.api.packet.PacketFactory;

import java.lang.reflect.*;
import java.util.EnumMap;

/**
 * Implementation class, don't use directly
 */
public final class $v1_8_R3PacketFactory implements PacketFactory
{

    private static final String ONLY_TEXT_FORMAT = "{\"text\": \"%s\"}";

    private final Constructor<?> packetPlayOutEntityDestroyConstructor;
    private final Constructor<?> packetPlayOutPlayerInfoConstructor;
    private final Constructor<?> packetPlayOutNamedEntitySpawnConstructor;
    private final Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor;

    // Actually, "b"
    private final Field packetPlayOutPlayerListHeaderFooterFooterField;

    private final Method chatSerializerSerialize;

    private final EnumMap<PlayerInfoAction, Object> playerInfoActionMappings = new EnumMap<>(PlayerInfoAction.class);
    private final Class<?> entityPlayerClass;

    public $v1_8_R3PacketFactory()
    {
        Class<?> entityPlayerClass = null;

        Constructor<?> packetPlayOutEntityDestroyConstructor = null;
        Constructor<?> packetPlayOutPlayerInfoConstructor = null;
        Constructor<?> packetPlayOutNamedEntitySpawnConstructor = null;
        Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor = null;

        Field packetPlayOutPlayerListHeaderFooterFooterField = null;

        Method chatSerializerSerialize = null;

        try
        {
            packetPlayOutEntityDestroyConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy")
                    .getDeclaredConstructor(int[].class);

            Class<?> enumPlayerInfoActionClass = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            for (PlayerInfoAction playerInfoAction : PlayerInfoAction.values())
            {
                Field enumPlayerInfoActionVariantField = enumPlayerInfoActionClass
                        .getDeclaredField(playerInfoAction.name());
                playerInfoActionMappings.put(playerInfoAction, enumPlayerInfoActionVariantField.get(null));
            }

            Class<?> entityHumanClass = Class
                    .forName("net.minecraft.server.v1_8_R3.EntityHuman");

            entityPlayerClass = Class
                    .forName("net.minecraft.server.v1_8_R3.EntityPlayer");

            Class<?> entityPlayerArrayClass = Class
                    .forName("[Lnet.minecraft.server.v1_8_R3.EntityPlayer;");

            packetPlayOutPlayerInfoConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo")
                    .getDeclaredConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);

            packetPlayOutNamedEntitySpawnConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn")
                    .getDeclaredConstructor(entityHumanClass);

            Class<?> iChatBaseComponentClass = Class
                    .forName("net.minecraft.server.v1_8_R3.IChatBaseComponent");

            Class<?> packetPlayOutPlayerListHeaderFooterClass = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter");

            packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooterClass
                    .getDeclaredConstructor(iChatBaseComponentClass);

            chatSerializerSerialize = Class.forName("net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer")
                    .getDeclaredMethod("a", String.class);

            packetPlayOutPlayerListHeaderFooterFooterField = packetPlayOutPlayerListHeaderFooterClass
                    .getDeclaredField("b");
            packetPlayOutPlayerListHeaderFooterFooterField.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        this.packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyConstructor;
        this.packetPlayOutPlayerInfoConstructor = packetPlayOutPlayerInfoConstructor;
        this.packetPlayOutNamedEntitySpawnConstructor = packetPlayOutNamedEntitySpawnConstructor;
        this.packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooterConstructor;

        this.entityPlayerClass = entityPlayerClass;

        this.packetPlayOutPlayerListHeaderFooterFooterField = packetPlayOutPlayerListHeaderFooterFooterField;

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
    public Object createPacketPlayOutEntityDestroy(int... entityIds)
    {
        try
        {
            return packetPlayOutEntityDestroyConstructor.newInstance(new Object[]{entityIds});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutPlayerInfo(PlayerInfoAction action, Object... handles)
    {
        try
        {
            Object array = Array.newInstance(entityPlayerClass, handles.length);
            for (int i = 0; i < handles.length; i++)
            {
                Array.set(array, i, handles[i]);
            }

            return packetPlayOutPlayerInfoConstructor.newInstance(playerInfoActionMappings.get(action), array);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutNamedEntitySpawn(Object handle)
    {
        try
        {
            return packetPlayOutNamedEntitySpawnConstructor.newInstance(handle);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutPlayerListHeaderFooter(String header, String footer)
    {
        Object headerComponent = createChatComponent(header);
        try
        {
            Object packet =  packetPlayOutPlayerListHeaderFooterConstructor.newInstance(headerComponent);

            if (footer != null)
            {
                Object footerComponent = createChatComponent(footer);
                packetPlayOutPlayerListHeaderFooterFooterField.set(packet, footerComponent);
            }

            return packet;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
