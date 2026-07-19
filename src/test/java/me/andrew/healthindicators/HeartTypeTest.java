package me.andrew.healthindicators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeartTypeTest {
    @Test
    void poisonEffectsUsePoisonedHeartTextures() {
        assertEquals(HeartType.POISONED_FULL, HeartType.applyStatus(HeartType.RED_FULL, true, false));
        assertEquals(HeartType.POISONED_HALF, HeartType.applyStatus(HeartType.RED_HALF, true, false));
    }

    @Test
    void witherEffectsOverridePoisonAndUseWitheredHeartTextures() {
        assertEquals(HeartType.WITHERED_FULL, HeartType.applyStatus(HeartType.RED_FULL, true, true));
        assertEquals(HeartType.WITHERED_HALF, HeartType.applyStatus(HeartType.RED_HALF, false, true));
    }
}
