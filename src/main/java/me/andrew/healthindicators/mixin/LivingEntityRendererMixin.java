package me.andrew.healthindicators.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.andrew.healthindicators.Config;
import me.andrew.healthindicators.HealthIndicatorsStateAccessor;
import me.andrew.healthindicators.HeartType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Targets the generic LivingEntityRenderer (not AvatarRenderer directly) because in 26.2
 * AvatarRenderer no longer overrides extractRenderState/submit itself - both are only declared
 * (and overridden) on LivingEntityRenderer, so that's where Mixin can actually inject. Every
 * injected block is guarded with `instanceof AbstractClientPlayer` / `instanceof
 * HealthIndicatorsStateAccessor` checks so non-player entities are untouched.
 */
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void healthIndicators$extractRenderState(T entity, S state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof AbstractClientPlayer player && state instanceof HealthIndicatorsStateAccessor accessor) {
            accessor.healthIndicators$setData(
                    player.getHealth(),
                    player.getMaxHealth(),
                    player.getAbsorptionAmount(),
                    hasArmorEquipped(player),
                    player.isLocalPlayer()
            );
        }
    }

    @Inject(method = "submit", at = @At("TAIL"))
    private void healthIndicators$submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
        if (!(state instanceof HealthIndicatorsStateAccessor accessor)) return;
        if (!Config.getRenderingEnabled()) return;
        boolean thirdPerson = !Minecraft.getInstance().options.getCameraType().isFirstPerson();
        if (accessor.healthIndicators$isLocalPlayer()) {
            if (!Config.getShowOwnHeartsInThirdPerson() || !thirdPerson) return;
        }
        if (state.isInvisibleToPlayer) {
            if (!Config.getInvisibleHeartsEnabled()) return;
            if (!accessor.healthIndicators$hasArmor()) return;
        }

        poseStack.pushPose();
        poseStack.translate(0, state.boundingBoxHeight + 0.5f, 0);
        poseStack.mulPose(camera.orientation);
        poseStack.scale(-1, 1, 1);

        float pixelSize = 0.025F;
        poseStack.scale(pixelSize, pixelSize, pixelSize);
        int offset = Config.getHeartOffset()
                + (state.isInvisibleToPlayer ? Config.getInvisibleHeartOffset() : 0)
                + (accessor.healthIndicators$isLocalPlayer() && thirdPerson ? Config.getThirdPersonHeartOffset() : 0);
        poseStack.translate(0, offset, 0);

        renderHeartRow(
                poseStack,
                submitNodeCollector,
                accessor.healthIndicators$getHealth(),
                accessor.healthIndicators$getMaxHealth(),
                accessor.healthIndicators$getAbsorption()
        );

        poseStack.popPose();
    }

    @Unique
    private static void renderHeartRow(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float health, float maxHealth, float absorption) {
        TextureAtlasSprite emptySprite = Minecraft.getInstance().getAtlasManager().get(new SpriteId(Sheets.GUI_SHEET, HeartType.EMPTY.texture));
        Identifier atlasTexture = emptySprite.atlasLocation();
        RenderType renderType = RenderTypes.entityTranslucent(atlasTexture);

        int healthRed = Mth.ceil(health);
        int maxHealthInt = Mth.ceil(maxHealth);
        int healthYellow = Mth.ceil(absorption);

        int heartsRed = Mth.ceil(healthRed / 2.0f);
        boolean lastRedHalf = (healthRed & 1) == 1;
        int heartsNormal = Mth.ceil(maxHealthInt / 2.0f);
        int heartsYellow = Mth.ceil(healthYellow / 2.0f);
        boolean lastYellowHalf = (healthYellow & 1) == 1;
        int heartsTotal = heartsNormal + heartsYellow;

        int heartsPerRow = Config.getHeartStackingEnabled() ? 10 : Math.max(heartsTotal, 1);
        int rowsTotal = (heartsTotal + heartsPerRow - 1) / heartsPerRow;
        int rowOffset = Math.max(10 - (rowsTotal - 2), 3);

        int pixelsTotal = Math.min(heartsTotal, heartsPerRow) * 8 + 1;
        float maxX = pixelsTotal / 2.0f;

        submitNodeCollector.submitCustomGeometry(poseStack, renderType, (drawPose, buffer) -> {
            for (int heart = 0; heart < heartsTotal; heart++) {
                int row = heart / heartsPerRow;
                int col = heart % heartsPerRow;

                float x = maxX - col * 8;
                float y = row * rowOffset;
                float z = row * 0.01F;
                drawHeart(drawPose, buffer, x, y, z, HeartType.EMPTY);

                HeartType type;
                if (heart < heartsRed) {
                    type = HeartType.RED_FULL;
                    if (heart == heartsRed - 1 && lastRedHalf) {
                        type = HeartType.RED_HALF;
                    }
                } else if (heart < heartsNormal) {
                    type = HeartType.EMPTY;
                } else {
                    type = HeartType.YELLOW_FULL;
                    if (heart == heartsTotal - 1 && lastYellowHalf) {
                        type = HeartType.YELLOW_HALF;
                    }
                }
                if (type != HeartType.EMPTY) {
                    drawHeart(drawPose, buffer, x, y, z, type);
                }
            }
        });
    }

    @Unique
    private static void drawHeart(PoseStack.Pose pose, VertexConsumer buffer, float x, float y, float z, HeartType type) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasManager().get(new SpriteId(Sheets.GUI_SHEET, type.texture));

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        float heartSize = 9F;

        // The ENTITY vertex format needs Position, Color, UV0 (texture), UV1 (overlay),
        // UV2 (lightmap) and Normal on every vertex - a bare position+uv isn't enough
        // (that's what crashed before). Full-bright + no overlay since these are drawn like a
        // HUD icon, not lit scenery.
        vertex(pose, buffer, x, y - heartSize, z, minU, maxV);
        vertex(pose, buffer, x - heartSize, y - heartSize, z, maxU, maxV);
        vertex(pose, buffer, x - heartSize, y, z, maxU, minV);
        vertex(pose, buffer, x, y, z, minU, minV);
    }

    @Unique
    private static void vertex(PoseStack.Pose pose, VertexConsumer buffer, float x, float y, float z, float u, float v) {
        buffer.addVertex(pose.pose(), x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightCoordsUtil.FULL_BRIGHT)
                .setNormal(pose, 0, 0, 1);
    }

    @Unique
    private static boolean hasArmorEquipped(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) return true;
        }
        return false;
    }
}
