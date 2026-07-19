package me.andrew.healthindicators;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {

    public static Screen buildConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Nolann's Player Health Indicators"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Heart Rendering"), Config.getRenderingEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Enable or disable heart rendering."))
                .setSaveConsumer(Config::setRenderingEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Show Own Hearts In Third Person"), Config.getShowOwnHeartsInThirdPerson())
                .setDefaultValue(false)
                .setTooltip(Component.literal("Show your own health bar above your head when using third-person camera."))
                .setSaveConsumer(Config::setShowOwnHeartsInThirdPerson)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Invisible Player Hearts"), Config.getInvisibleHeartsEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show hearts above invisible players only when they are wearing armor."))
                .setSaveConsumer(Config::setInvisibleHeartsEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Render Through Walls"), Config.getRenderThroughWallsEnabled())
                .setDefaultValue(false)
                .setTooltip(Component.literal("Keep hearts visible even when the player is behind walls."))
                .setSaveConsumer(Config::setRenderThroughWallsEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Poison Hearts"), Config.getPoisonHeartsEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show poison-style heart textures when the player is poisoned."))
                .setSaveConsumer(Config::setPoisonHeartsEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Wither Hearts"), Config.getWitherHeartsEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show wither-style heart textures when the player is withered."))
                .setSaveConsumer(Config::setWitherHeartsEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Hide Hearts In Creative"), Config.getHideCreativeHearts())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Hide hearts above creative-mode players."))
                .setSaveConsumer(Config::setHideCreativeHearts)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Hide Hearts In Spectator"), Config.getHideSpectatorHearts())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Hide hearts above spectator-mode players."))
                .setSaveConsumer(Config::setHideSpectatorHearts)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Heart Y Offset"), Config.getHeartOffset())
                .setDefaultValue(10)
                .setTooltip(Component.literal("Vertical offset (in pixels) applied to the heart row(s)."))
                .setSaveConsumer(Config::setHeartOffset)
                .build());

        general.addEntry(entryBuilder
                .startIntSlider(Component.literal("Heart Render Distance"), Config.getHeartRenderDistance(), 1, 64)
                .setDefaultValue(16)
                .setTooltip(Component.literal("Maximum distance in blocks at which hearts are rendered above players."))
                .setSaveConsumer(Config::setHeartRenderDistance)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Invisible Heart Extra Y Offset"), Config.getInvisibleHeartOffset())
                .setDefaultValue(-10)
                .setTooltip(Component.literal("Extra vertical offset (in pixels) added only when the target player is invisible."))
                .setSaveConsumer(Config::setInvisibleHeartOffset)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Third Person Heart Extra Y Offset"), Config.getThirdPersonHeartOffset())
                .setDefaultValue(-10)
                .setTooltip(Component.literal("Extra vertical offset (in pixels) added only to your own hearts in third person."))
                .setSaveConsumer(Config::setThirdPersonHeartOffset)
                .build());

        return builder.build();
    }
}
