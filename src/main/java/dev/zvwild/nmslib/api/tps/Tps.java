package dev.zvwild.nmslib.api.tps;

import dev.zvwild.nmslib.api.NMS;
import dev.zvwild.nmslib.impl.tps.$v_1_16_R3TpsProvider;
import dev.zvwild.nmslib.impl.tps.$v_1_8_R3TpsProvider;

/**
 * Supplier for tps-related functionality
 */
public final class Tps
{

    private static final TpsProvider PROVIDER;

    static
    {
        switch (NMS.getVersion())
        {
            case v1_8_R3:
                PROVIDER = new $v_1_8_R3TpsProvider();
                break;

            case v1_16_R3:
                PROVIDER = new $v_1_16_R3TpsProvider();
                break;

            default:
                PROVIDER = null;
                break;
        }
    }

    private Tps()
    {
    }

    /**
     * Get the correct tps provider for this version
     *
     * @return the tps provider
     */
    public static TpsProvider getProvider()
    {
        return PROVIDER;
    }

}
