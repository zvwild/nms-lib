package dev.zvwild.nmslib.impl.packet;

import dev.zvwild.nmslib.api.packet.PacketFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

/**
 * Implementation class, don't use directly
 */
public final class $v1_8_R3PacketFactory implements PacketFactory
{

    private final Constructor<?> packetPlayOutEntityDestroyConstructor;
    private final Constructor<?> packetPlayOutPlayerInfoConstructor;
    private final Constructor<?> packetPlayOutNamedEntitySpawnConstructor;

    private final EnumMap<PlayerInfoAction, Object> playerInfoActionMappings = new EnumMap<>(PlayerInfoAction.class);
    private final Class<?> entityPlayerClass;

    public $v1_8_R3PacketFactory()
    {
        Class<?> entityPlayerClass = null;

        Constructor<?> packetPlayOutEntityDestroyConstructor = null;
        Constructor<?> packetPlayOutPlayerInfoConstructor = null;
        Constructor<?> packetPlayOutNamedEntitySpawnConstructor = null;

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
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        this.packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyConstructor;
        this.packetPlayOutPlayerInfoConstructor = packetPlayOutPlayerInfoConstructor;
        this.packetPlayOutNamedEntitySpawnConstructor = packetPlayOutNamedEntitySpawnConstructor;
        this.entityPlayerClass = entityPlayerClass;
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

}
