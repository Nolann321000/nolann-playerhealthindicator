package me.andrew.healthindicators;

public interface HealthIndicatorsStateAccessor {
    void healthIndicators$setData(float health, float maxHealth, float absorption, boolean hasArmor, boolean isLocalPlayer, boolean poisoned, boolean withered, boolean creative, boolean spectator);

    float healthIndicators$getHealth();

    float healthIndicators$getMaxHealth();

    float healthIndicators$getAbsorption();

    boolean healthIndicators$hasArmor();

    boolean healthIndicators$isLocalPlayer();

    boolean healthIndicators$isPoisoned();

    boolean healthIndicators$isWithered();

    boolean healthIndicators$isCreative();

    boolean healthIndicators$isSpectator();
}
