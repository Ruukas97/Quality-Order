package ruukas.qualityorder.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntityBanner;

public class GuiHelper {

	/**
	 * Draws an entity on the screen looking toward the cursor.
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, Entity ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.1F, 0.0F, 1.0F);
		float f1 = ent.rotationYaw;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F, 1.0F, 0.0F, 0.0F);
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.rotationYaw = f1;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		// float f = ActiveRenderInfo.getRotationX();
		// float f11 = ActiveRenderInfo.getRotationZ();
		// float f2 = ActiveRenderInfo.getRotationYZ();
		// float f3 = ActiveRenderInfo.getRotationXY();
		// float f4 = ActiveRenderInfo.getRotationXZ();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(516, 0.003921569F);

	}

	/**
	 * Draws an entity on the screen looking toward the cursor.
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.1F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		// GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 25.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	/**
	 * Draws a banner facing the mouse cursor.
	 * 
	 * @param posX
	 * @param posY
	 * @param scale
	 * @param mouseX
	 * @param mouseY
	 * @param banner
	 */
	public static void renderBanner(int posX, int posY, int scale, float mouseX, float mouseY, TileEntityBanner banner) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		// float f = ent.renderYawOffset;
		// float f1 = ent.rotationYaw;
		// float f2 = ent.rotationPitch;
		// float f3 = ent.prevRotationYawHead;
		// float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan((double) (mouseX / 40.0F))) * 20.5F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 2.0F, 1.0F, 0.0F, 0.0F);
		// ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) *
		// 20.0F;
		// ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
		// ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) *
		// 20.0F;
		// ent.rotationYawHead = ent.rotationYaw;
		// ent.prevRotationYawHead = ent.rotationYaw;
		// GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		// rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		TileEntityRendererDispatcher.instance.render(banner, 0, 0, 0, Minecraft.getMinecraft().getRenderPartialTicks());
		rendermanager.setRenderShadow(true);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
