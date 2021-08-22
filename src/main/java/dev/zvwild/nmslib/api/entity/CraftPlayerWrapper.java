package dev.zvwild.nmslib.api.entity;

import dev.zvwild.nmslib.api.NMS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wrapper for important CraftPlayer functionality
 */
public final class CraftPlayerWrapper {

    private static Methods methods;

    private final Object wrapped;

    private CraftPlayerWrapper(Object toWrap) {
        if (methods == null) {
            Class<?> craftPlayerClass = toWrap.getClass();

            Method getHandleMethod = null;
            Method getProfileMethod = null;

            try {
                getHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
                getProfileMethod = craftPlayerClass.getDeclaredMethod("getProfile");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            methods = new Methods(getHandleMethod, getProfileMethod);
        }

        this.wrapped = toWrap;
    }

    /**
     * Get the internal wrapped object
     *
     * @return the wrapped object
     */
    public Object getWrapped() {
        return wrapped;
    }

    /**
     * Get the nms handle (EntityPlayer)
     *
     * @return the minecraft server handle
     */
    public Object getHandle() {
        try {
            return methods.getHandle.invoke(wrapped);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the mojang GameProfile
     *
     * @return this players profile
     */
    public Object getProfile() {
        try {
            return methods.getProfile.invoke(wrapped);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convenience method to send a packet
     *
     * @param packet the packet to send
     */
    public void sendPacket(Object packet) {
        NMS.getPacketTransmitter().transmitPacket(wrapped, packet);
    }

    /**
     * Wrap an instance of Player and check whether it is safe
     *
     * @param toWrap the player to wrap
     * @return the wrapped player
     */
    public static CraftPlayerWrapper wrap(Object toWrap) {
        if (!toWrap.getClass().getSimpleName().equals("CraftPlayer")) {
            throw new IllegalArgumentException(toWrap.getClass().getSimpleName() + " is not a CraftPlayer");
        }

        return new CraftPlayerWrapper(toWrap);
    }

    /**
     * Wrap an instance of Player but without any safety checks
     *
     * @param toWrap the player to wrap
     * @return the unchecked wrapped player
     */
    public static CraftPlayerWrapper unsafe(Object toWrap) {
        return new CraftPlayerWrapper(toWrap);
    }

    private static final class Methods {

        public final Method getHandle;
        public final Method getProfile;

        public Methods(Method getHandle, Method getProfile) {
            this.getHandle = getHandle;
            this.getProfile = getProfile;
        }

    }

}
