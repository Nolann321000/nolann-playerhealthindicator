package me.andrew.healthindicators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigTest {
    @Test
    void throughWallsRenderingCanBeToggled() {
        Config.setRenderThroughWallsEnabled(false);
        assertFalse(Config.getRenderThroughWallsEnabled());

        Config.setRenderThroughWallsEnabled(true);
        assertTrue(Config.getRenderThroughWallsEnabled());
    }

    @Test
    void heartRenderDistanceUsesConfiguredThreshold() {
        Config.setHeartRenderDistance(16);
        assertTrue(Config.isWithinHeartRenderDistance(16.0));
        assertTrue(Config.isWithinHeartRenderDistance(15.9));
        assertFalse(Config.isWithinHeartRenderDistance(16.1));
    }
}
