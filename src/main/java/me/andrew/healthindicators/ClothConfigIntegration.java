package me.andrew.healthindicators;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * All Cloth Config API usage lives in this one class. Nothing else in the mod references
 * me.shedaniel.clothconfig2.* directly - that way, if Cloth Config isn't installed, the JVM
 * simply never loads this class (and never throws NoClassDefFoundError), because it's only
 * referenced from behind a FabricLoader.isModLoaded("cloth-config") check in
 * HealthIndicatorsMod.
 *
 * UNVERIFIED: this uses Cloth Config's classic ConfigBuilder API, which has been stable across
 * many Minecraft versions. I could not run this against the real 26.2.155 jar from this
 * sandbox, so please do a test build before relying on it - if the builder API changed, the
 * compiler will point you straight at the problem here.
 */
public class ClothConfigIntegration {

    public static Screen buildConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Player Health Indicators"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Mod Enabled"), Config.getRenderingEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Enable or disable Nolann's Player Health Indicator."))
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
                .setTooltip(Component.literal("Show hearts above invisible players wearing armor."))
                .setSaveConsumer(Config::setInvisibleHeartsEnabled)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Heart Stacking"), Config.getHeartStackingEnabled())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Wrap hearts onto new rows instead of drawing one long line."))
                .setSaveConsumer(Config::setHeartStackingEnabled)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Heart Y Offset"), Config.getHeartOffset())
                .setDefaultValue(10)
                .setTooltip(Component.literal("Vertical offset (in pixels) applied to the heart row(s)."))
                .setSaveConsumer(Config::setHeartOffset)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Invisible Heart Offset"), Config.getInvisibleHeartOffset())
                .setDefaultValue(-10)
                .setTooltip(Component.literal("Extra vertical offset (in pixels) added only when the target player is invisible."))
                .setSaveConsumer(Config::setInvisibleHeartOffset)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Component.literal("Third Person Heart Offset"), Config.getThirdPersonHeartOffset())
                .setDefaultValue(0)
                .setTooltip(Component.literal("Extra vertical offset (in pixels) added only to your own hearts in third person."))
                .setSaveConsumer(Config::setThirdPersonHeartOffset)
                .build());

        return builder.build();
    }
}
