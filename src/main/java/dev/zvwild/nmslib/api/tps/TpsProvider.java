package dev.zvwild.nmslib.api.tps;

/**
 * Interface for tps-related functionality
 */
public interface TpsProvider {

    /**
     * Get the tps from 1m, 5m, and 15m
     *
     * @return array where the layout is (0 - 1m, 1 - 5m, 2 - 15m)
     */
    double[] getTps();

}
