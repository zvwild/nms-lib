package dev.zvwild.nmslib.impl.packet;

import dev.zvwild.nmslib.api.packet.PacketFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

public final class $v1_8_R3PacketFactory implements PacketFactory
{

    private final Constructor<?> packetPlayOutEntityDestroyConstructor;
    private final Constructor<?> packetPlayOutPlayerInfoConstructor;
    private final Constructor<?> packetPlayOutNamedEntitySpawnConstructor;

    private final EnumMap<PlayerInfoAction, Object> playerInfoActionMappings = new EnumMap<>(PlayerInfoAction.class);

    public $v1_8_R3PacketFactory()
    {
        Constructor<?> packetPlayOutEntityDestroyConstructor = null;
        Constructor<?> packetPlayOutPlayerInfoConstructor = null;
        Constructor<?> packetPlayOutNamedEntitySpawnConstructor = null;

        try
        {
            packetPlayOutEntityDestroyConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy")
                    .getDeclaredConstructor(int.class);

            Class<?> enumPlayerInfoActionClass = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo$EnumPlayerInfoAction");

            for (PlayerInfoAction playerInfoAction : PlayerInfoAction.values())
            {
                Field enumPlayerInfoActionVariantField = enumPlayerInfoActionClass
                        .getDeclaredField(playerInfoAction.name());
                playerInfoActionMappings.put(playerInfoAction, enumPlayerInfoActionVariantField.get(null));
            }

            Class<?> entityPlayerClass = Class
                    .forName("net.minecraft.server.v1_8_R3.EntityPlayer");

            packetPlayOutPlayerInfoConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo")
                    .getDeclaredConstructor(enumPlayerInfoActionClass, entityPlayerClass);

            packetPlayOutNamedEntitySpawnConstructor = Class
                    .forName("net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn")
                    .getDeclaredConstructor(entityPlayerClass);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        this.packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroyConstructor;
        this.packetPlayOutPlayerInfoConstructor = packetPlayOutPlayerInfoConstructor;
        this.packetPlayOutNamedEntitySpawnConstructor = packetPlayOutNamedEntitySpawnConstructor;
    }

    @Override
    public Object createPacketPlayOutEntityDestroy(int entityId)
    {
        try
        {
            return packetPlayOutEntityDestroyConstructor.newInstance(entityId);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createPacketPlayOutPlayerInfo(PlayerInfoAction action, Object handle)
    {
        try
        {
            return packetPlayOutPlayerInfoConstructor.newInstance(playerInfoActionMappings.get(action), handle);
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
