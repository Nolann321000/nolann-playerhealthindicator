package me.andrew.healthindicators;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class HealthIndicatorsMod implements ModInitializer {
    public static final String MOD_ID = "healthindicators";

    public static final String CONFIG_FILE = "healthindicators.json";

    public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

    public static final KeyMapping RENDERING_ENABLED_KEY_BINDING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key." + MOD_ID + ".renderingEnabled",
            InputConstants.UNKNOWN.getValue(),
            KEY_CATEGORY
    ));
    public static final KeyMapping INCREASE_HEART_OFFSET_KEY_BINDING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key." + MOD_ID + ".increaseHeartOffset",
            InputConstants.UNKNOWN.getValue(),
            KEY_CATEGORY
    ));
    public static final KeyMapping DECREASE_HEART_OFFSET_KEY_BINDING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key." + MOD_ID + ".decreaseHeartOffset",
            InputConstants.UNKNOWN.getValue(),
            KEY_CATEGORY
    ));
    public static final KeyMapping OPEN_CONFIG_KEY_BINDING = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key." + MOD_ID + ".openConfig",
            InputConstants.UNKNOWN.getValue(),
            KEY_CATEGORY
    ));

    public static final boolean CLOTH_CONFIG_PRESENT = FabricLoader.getInstance().isModLoaded("cloth-config");

    @Override
    public void onInitialize() {
        Config.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (RENDERING_ENABLED_KEY_BINDING.consumeClick()) {
                Config.setRenderingEnabled(!Config.getRenderingEnabled());
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.literal((Config.getRenderingEnabled() ? "Enabled" : "Disabled") + "Nolann's Player Health Indicators"));
                }
            }

            while (INCREASE_HEART_OFFSET_KEY_BINDING.consumeClick()) {
                Config.setHeartOffset(Config.getHeartOffset() + 1);
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.literal("Set heart offset to " + Config.getHeartOffset()));
                }
            }

            while (DECREASE_HEART_OFFSET_KEY_BINDING.consumeClick()) {
                Config.setHeartOffset(Config.getHeartOffset() - 1);
                if (client.player != null) {
                    client.player.sendOverlayMessage(Component.literal("Set heart offset to " + Config.getHeartOffset()));
                }
            }

            while (OPEN_CONFIG_KEY_BINDING.consumeClick()) {
                if (CLOTH_CONFIG_PRESENT) {
                    Minecraft.getInstance().gui.setScreen(ClothConfigIntegration.buildConfigScreen(Minecraft.getInstance().gui.screen()));
                } else if (client.player != null) {
                    client.player.sendOverlayMessage(Component.literal("Install Cloth Config to access the config screen !"));
                }
            }
        });
    }
}
