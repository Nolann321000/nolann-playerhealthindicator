package me.andrew.healthindicators;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * Only loaded/called by Mod Menu itself when it's installed - Fabric Loader doesn't instantiate
 * "modmenu" entrypoints unless something (Mod Menu) actually asks for them, so this class being
 * compileOnly-dependent on Mod Menu's API is safe even when Mod Menu is absent.
 *
 * The actual screen-building code still lives entirely in ClothConfigIntegration, and we only
 * reach it if Cloth Config is also present - otherwise clicking the mod in the list just won't
 * show a config button at all (returning null here means "no config screen").
 */
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!HealthIndicatorsMod.CLOTH_CONFIG_PRESENT) return null;
        return ClothConfigIntegration::buildConfigScreen;
    }
}
