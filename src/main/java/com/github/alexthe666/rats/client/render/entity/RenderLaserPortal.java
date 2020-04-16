package com.github.alexthe666.rats.client.render.entity;

import com.github.alexthe666.rats.client.model.LaserPortalModel;
import com.github.alexthe666.rats.client.model.ModelNeoRatlantean;
import com.github.alexthe666.rats.server.entity.EntityLaserBeam;
import com.github.alexthe666.rats.server.entity.EntityLaserPortal;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderLaserPortal extends EntityRenderer<EntityLaserPortal> {

    private static final ResourceLocation TEXTURE_EYES = new ResourceLocation("rats:textures/entity/ratlantis/neo_ratlantean_glow.png");
    private static final LaserPortalModel MODEL_NEO_RATLANTEAN = new LaserPortalModel();

    public RenderLaserPortal() {
        super(Minecraft.getInstance().getRenderManager());
        MODEL_NEO_RATLANTEAN.floatyPivot.setRotationPoint(0, 0, 0);
        MODEL_NEO_RATLANTEAN.floatyPivot.rotateAngleY = 0.7853981633974483F;
    }

    public void render(EntityLaserPortal entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, RenderType.getEntityCutoutNoCull(TEXTURE_EYES), false, true);
        float d1 = this.interpolateValue(entity.scaleOfPortalPrev, entity.scaleOfPortal, (partialTicks));

        matrixStackIn.rotate(new Quaternion(Vector3f.ZP, entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, true));
        matrixStackIn.push();
        GlStateManager.scalef(1.5F * d1, 1.5F * d1, 1.5F * d1);
        matrixStackIn.translate(0, 0.5F, 0);
        matrixStackIn.translate(0, 1 - d1, 0);
        matrixStackIn.rotate(new Quaternion(Vector3f.XP, 90, true));
        matrixStackIn.rotate(new Quaternion(Vector3f.ZP, entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, true));
        matrixStackIn.rotate(new Quaternion(Vector3f.YP, (entity.ticksExisted + partialTicks) * 10, true));
        MODEL_NEO_RATLANTEAN.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private float interpolateValue(float start, float end, float pct) {
        return start + (end - start) * pct;
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityLaserPortal entity) {
        return TEXTURE_EYES;
    }
}
