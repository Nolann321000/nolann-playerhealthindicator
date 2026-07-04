package me.andrew.healthindicators.mixin;

import me.andrew.healthindicators.HealthIndicatorsStateAccessor;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements HealthIndicatorsStateAccessor {
    @Unique
    private float healthIndicators$health;
    @Unique
    private float healthIndicators$maxHealth;
    @Unique
    private float healthIndicators$absorption;
    @Unique
    private boolean healthIndicators$hasArmor;
    @Unique
    private boolean healthIndicators$isLocalPlayer;

    @Override
    public void healthIndicators$setData(float health, float maxHealth, float absorption, boolean hasArmor, boolean isLocalPlayer) {
        this.healthIndicators$health = health;
        this.healthIndicators$maxHealth = maxHealth;
        this.healthIndicators$absorption = absorption;
        this.healthIndicators$hasArmor = hasArmor;
        this.healthIndicators$isLocalPlayer = isLocalPlayer;
    }

    @Override
    public float healthIndicators$getHealth() {
        return healthIndicators$health;
    }

    @Override
    public float healthIndicators$getMaxHealth() {
        return healthIndicators$maxHealth;
    }

    @Override
    public float healthIndicators$getAbsorption() {
        return healthIndicators$absorption;
    }

    @Override
    public boolean healthIndicators$hasArmor() {
        return healthIndicators$hasArmor;
    }

    @Override
    public boolean healthIndicators$isLocalPlayer() {
        return healthIndicators$isLocalPlayer;
    }
}
