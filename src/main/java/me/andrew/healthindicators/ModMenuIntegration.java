package me.andrew.healthindicators;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!HealthIndicatorsMod.CLOTH_CONFIG_PRESENT) return null;
        return ClothConfigIntegration::buildConfigScreen;
    }
}
