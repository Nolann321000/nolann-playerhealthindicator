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
    @Unique
    private boolean healthIndicators$poisoned;
    @Unique
    private boolean healthIndicators$withered;
    @Unique
    private boolean healthIndicators$creative;
    @Unique
    private boolean healthIndicators$spectator;

    @Override
    public void healthIndicators$setData(float health, float maxHealth, float absorption, boolean hasArmor, boolean isLocalPlayer, boolean poisoned, boolean withered, boolean creative, boolean spectator) {
        this.healthIndicators$health = health;
        this.healthIndicators$maxHealth = maxHealth;
        this.healthIndicators$absorption = absorption;
        this.healthIndicators$hasArmor = hasArmor;
        this.healthIndicators$isLocalPlayer = isLocalPlayer;
        this.healthIndicators$poisoned = poisoned;
        this.healthIndicators$withered = withered;
        this.healthIndicators$creative = creative;
        this.healthIndicators$spectator = spectator;
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

    @Override
    public boolean healthIndicators$isPoisoned() {
        return healthIndicators$poisoned;
    }

    @Override
    public boolean healthIndicators$isWithered() {
        return healthIndicators$withered;
    }

    @Override
    public boolean healthIndicators$isCreative() {
        return healthIndicators$creative;
    }

    @Override
    public boolean healthIndicators$isSpectator() {
        return healthIndicators$spectator;
    }
}
