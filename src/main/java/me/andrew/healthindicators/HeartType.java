package me.andrew.healthindicators;

import net.minecraft.resources.Identifier;

public enum HeartType {
    EMPTY(Identifier.withDefaultNamespace("hud/heart/container")),
    RED_FULL(Identifier.withDefaultNamespace("hud/heart/full")),
    RED_HALF(Identifier.withDefaultNamespace("hud/heart/half")),
    YELLOW_FULL(Identifier.withDefaultNamespace("hud/heart/absorbing_full")),
    YELLOW_HALF(Identifier.withDefaultNamespace("hud/heart/absorbing_half")),
    POISONED_FULL(Identifier.withDefaultNamespace("hud/heart/poisoned_full")),
    POISONED_HALF(Identifier.withDefaultNamespace("hud/heart/poisoned_half")),
    WITHERED_FULL(Identifier.withDefaultNamespace("hud/heart/withered_full")),
    WITHERED_HALF(Identifier.withDefaultNamespace("hud/heart/withered_half"));

    public final Identifier texture;

    HeartType(Identifier texture) {
        this.texture = texture;
    }

    public static HeartType applyStatus(HeartType baseType, boolean poisoned, boolean withered) {
        if (withered) {
            return switch (baseType) {
                case RED_FULL, YELLOW_FULL -> WITHERED_FULL;
                case RED_HALF, YELLOW_HALF -> WITHERED_HALF;
                default -> baseType;
            };
        }
        if (poisoned) {
            return switch (baseType) {
                case RED_FULL, YELLOW_FULL -> POISONED_FULL;
                case RED_HALF, YELLOW_HALF -> POISONED_HALF;
                default -> baseType;
            };
        }
        return baseType;
    }
}
