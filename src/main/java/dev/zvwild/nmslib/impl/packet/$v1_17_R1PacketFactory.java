package dev.zvwild.nmslib.impl.packet;

import dev.zvwild.nmslib.api.packet.PacketFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;

/**
 * Implementation class, don't use directly
 */
public final class $v1_17_R1PacketFactory implements PacketFactory {

    private static final String ONLY_TEXT_FORMAT = "{\"text\": \"%s\"}";

    private final Constructor<?> packetPlayOutEntityDestroyConstructor;
    private final Constructor<?> packetPlayOutPlayerInfoConstructor;
    private final Constructor<?> packetPlayOutNamedEntitySpawnConstructor;
    private final Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor;

    private final Method chatSerializerSerialize;

    private final EnumMap<PlayerInfoAction, Object> playerInfoActionMappings = new EnumMap<>(PlayerInfoAction.class);
    private final Class<?> entityPlayerClass;

    public $v1_17_R1PacketFactory() {
        Class<?> entityPlayerClass = null;

        Constructor<?> packetPlayOutEntityDestroyConstructor = null;
        Constructor<?> packetPlayOutPlayerInfoConstructor = null;
        Constructor<?> packetPlayOutNamedEntitySpawnConstructor = null;
        Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor = null;

        Field packetPlayOutPlayerListHeaderFooterHeaderField = null;
        Field packetPlayOutPlayerListHeaderFooterFooterField = null;

        Method chatSerializerSerialize = null;

        try {
            packetPlayOutEntityDestroyConstructor = Class
                    .forName("net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy")
                    .getDeclaredConstructor(int[].class);

            Class<?> enumPlayerInfoActionClass = Class
                    .forName("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            playerInfoActionMappings.put(PlayerInfoAction.ADD_PLAYER, enumPlayerInfoActionClass.getDeclaredField("a").get(null));
            playerInfoActionMappings.put(PlayerInfoAction.REMOVE_PLAYER, enumPlayerInfoActionClass.getDeclaredField("e").get(null));

            Class<?> entityHumanClass = Class
                    .forName("net.minecraft.world.entity.player.EntityHuman");

            entityPlayerClass = Class
                    .forName("net.minecraft.server.level.EntityPlayer");

            Class<?> entityPlayerArrayClass = Class
                    .forName("[Lnet.minecraft.server.level.EntityPlayer;");

            packetPlayOutPlayerInfoConstructor = Class
                    .forName("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo")
                    .getDeclaredConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);

            packetPlayOutNamedEntitySpawnConstructor = Class
                    .forName("net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn")
                    .getDeclaredConstructor(entityHumanClass);

            Class<?> packetPlayOutPlayerListHeaderFooterClass = Class
                    .forName("net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter");

            Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.network.chat.IChatBaseComponent");

            packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooterClass
                    .getDeclaredConstructor(iChatBaseComponentClass, iChatBaseComponentClass);

            chatSerializerSerialize = Class.forName("net.minecraft.network.chat.IChatBaseComponent$ChatSerializer")
                    .getDeclaredMethod("a", String.class);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyConstructor;
        this.packetPlayOutPlayerInfoConstructor = packetPlayOutPlayerInfoConstructor;
        this.packetPlayOutNamedEntitySpawnConstructor = packetPlayOutNamedEntitySpawnConstructor;
        this.entityPlayerClass = entityPlayerClass;
        this.packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooterConstructor;

        this.chatSerializerSerialize = chatSerializerSerialize;
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
    public Object createPacketPlayOutEntityDestroy(int... entityIds) {
        try {
            return packetPlayOutEntityDestroyConstructor.newInstance(new Object[]{entityIds});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutPlayerInfo(PlayerInfoAction action, Object... handles) {
        try {
            Object array = Array.newInstance(entityPlayerClass, handles.length);
            for (int i = 0; i < handles.length; i++) {
                Array.set(array, i, handles[i]);
            }

            return packetPlayOutPlayerInfoConstructor.newInstance(playerInfoActionMappings.get(action), array);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutNamedEntitySpawn(Object handle) {
        try {
            return packetPlayOutNamedEntitySpawnConstructor.newInstance(handle);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutPlayerListHeaderFooter(String header, String footer) {
        try {
            return packetPlayOutPlayerListHeaderFooterConstructor.newInstance(
                    createChatComponent(header),
                    createChatComponent(footer)
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}
