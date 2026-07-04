package me.andrew.healthindicators;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {
    private static final Gson GSON = new Gson();

    private static Config INSTANCE = new Config();

    private boolean renderingEnabled = true;
    private boolean heartStackingEnabled = true;
    private int heartOffset = 10;
    private boolean invisibleHeartsEnabled = true;
    private boolean showOwnHeartsInThirdPerson = false;
    private int invisibleHeartOffset = -10;
    private int thirdPersonHeartOffset = -10;

    public static boolean getRenderingEnabled() {
        return INSTANCE.renderingEnabled;
    }

    public static void setRenderingEnabled(boolean renderingEnabled) {
        INSTANCE.renderingEnabled = renderingEnabled;
        save();
    }

    public static boolean getHeartStackingEnabled() {
        return INSTANCE.heartStackingEnabled;
    }

    public static void setHeartStackingEnabled(boolean heartStackingEnabled) {
        INSTANCE.heartStackingEnabled = heartStackingEnabled;
        save();
    }

    public static int getHeartOffset() {
        return INSTANCE.heartOffset;
    }

    public static void setHeartOffset(int heartOffset) {
        INSTANCE.heartOffset = heartOffset;
        save();
    }

    public static boolean getInvisibleHeartsEnabled() {
        return INSTANCE.invisibleHeartsEnabled;
    }

    public static void setInvisibleHeartsEnabled(boolean invisibleHeartsEnabled) {
        INSTANCE.invisibleHeartsEnabled = invisibleHeartsEnabled;
        save();
    }

    public static boolean getShowOwnHeartsInThirdPerson() {
        return INSTANCE.showOwnHeartsInThirdPerson;
    }

    public static void setShowOwnHeartsInThirdPerson(boolean showOwnHeartsInThirdPerson) {
        INSTANCE.showOwnHeartsInThirdPerson = showOwnHeartsInThirdPerson;
        save();
    }

    public static int getInvisibleHeartOffset() {
        return INSTANCE.invisibleHeartOffset;
    }

    public static void setInvisibleHeartOffset(int invisibleHeartOffset) {
        INSTANCE.invisibleHeartOffset = invisibleHeartOffset;
        save();
    }

    public static int getThirdPersonHeartOffset() {
        return INSTANCE.thirdPersonHeartOffset;
    }

    public static void setThirdPersonHeartOffset(int thirdPersonHeartOffset) {
        INSTANCE.thirdPersonHeartOffset = thirdPersonHeartOffset;
        save();
    }

    public static void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FabricLoader.getInstance().getConfigDir().resolve(HealthIndicatorsMod.CONFIG_FILE).toFile()))) {
            Config config = GSON.fromJson(reader, Config.class);
            if (config != null) {
                INSTANCE = config;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FabricLoader.getInstance().getConfigDir().resolve(HealthIndicatorsMod.CONFIG_FILE).toFile()))) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
