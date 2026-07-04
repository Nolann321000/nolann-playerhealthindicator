package me.andrew.healthindicators;

/**
 * Implemented by a mixin on AvatarRenderState (see AvatarRenderStateMixin). The new 26.2
 * rendering pipeline extracts entity data into an immutable-ish "render state" object on the
 * main thread, then renders/submits it later - so the entity itself isn't available anymore at
 * submit time. We stash the health/armor data we need onto the state object during
 * extractRenderState, and read it back here during submit.
 */
public interface HealthIndicatorsStateAccessor {
    void healthIndicators$setData(float health, float maxHealth, float absorption, boolean hasArmor, boolean isLocalPlayer);

    float healthIndicators$getHealth();

    float healthIndicators$getMaxHealth();

    float healthIndicators$getAbsorption();

    boolean healthIndicators$hasArmor();

    boolean healthIndicators$isLocalPlayer();
}
