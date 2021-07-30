package dev.zvwild.nmslib.api.entity;

import dev.zvwild.nmslib.api.NMS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wrapper for important CraftPlayer functionality
 */
public final class CraftPlayerWrapper
{

    private final Object wrapped;

    private final Method getHandleMethod;
    private final Method getProfileMethod;

    private CraftPlayerWrapper(Object toWrap)
    {
        Class<?> craftPlayerClass = toWrap.getClass();

        Method getHandleMethod = null;
        Method getProfileMethod = null;

        try
        {
            getHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            getProfileMethod = craftPlayerClass.getDeclaredMethod("getProfile");
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        this.wrapped = toWrap;
        this.getHandleMethod = getHandleMethod;
        this.getProfileMethod = getProfileMethod;
    }

    public Object getHandle()
    {
        try
        {
            return getHandleMethod.invoke(wrapped);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Object getProfile()
    {
        try
        {
            return getProfileMethod.invoke(wrapped);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void sendPacket(Object packet)
    {
        NMS.getPacketTransmitter().transmitPacket(wrapped, packet);
    }

    public static CraftPlayerWrapper wrap(Object toWrap)
    {
        if (!toWrap.getClass().getSimpleName().equals("CraftPlayer"))
        {
            throw new IllegalArgumentException(toWrap.getClass().getSimpleName() + " is not a CraftPlayer");
        }

        return new CraftPlayerWrapper(toWrap);
    }

    public static CraftPlayerWrapper unsafe(Object toWrap)
    {
        return new CraftPlayerWrapper(toWrap);
    }

}
