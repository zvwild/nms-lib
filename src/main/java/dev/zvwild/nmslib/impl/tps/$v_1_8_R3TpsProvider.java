package dev.zvwild.nmslib.impl.tps;

import dev.zvwild.nmslib.api.tps.TpsProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class $v_1_8_R3TpsProvider implements TpsProvider
{

    private final Field recentTpsField;
    private final Method getServerMethod;

    public $v_1_8_R3TpsProvider()
    {
        Field recentTpsField = null;
        Method getServerMethod = null;

        try
        {
            Class<?> serverClass = Class.forName("net.minecraft.server.v1_8_R3.MinecraftServer");
            recentTpsField = serverClass.getDeclaredField("recentTps");
            getServerMethod = serverClass.getDeclaredMethod("getServer");
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        this.recentTpsField = recentTpsField;
        this.getServerMethod = getServerMethod;
    }

    @Override
    public double[] getTps()
    {
        try
        {
            Object server = getServerMethod.invoke(null);
            return (double[]) recentTpsField.get(server);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
