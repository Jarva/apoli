package io.github.apace100.apoli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.OverrideHudTexturePower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.OverlayPower;
import io.github.apace100.apoli.screen.GameHudRender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;getCurrentGameMode()Lnet/minecraft/world/GameMode;", ordinal = 0))
    private void renderOnHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        boolean hudHidden = client.options.hudHidden;
        boolean thirdPerson = !client.options.getPerspective().isFirstPerson();
        PowerHolderComponent.withPower(client.getCameraEntity(), OverlayPower.class, p -> {
            if(p.getDrawPhase() != OverlayPower.DrawPhase.BELOW_HUD) {
                return false;
            }
            if(hudHidden && p.doesHideWithHud()) {
                return false;
            }
            if(thirdPerson && !p.shouldBeVisibleInThirdPerson()) {
                return false;
            }
            return true;
        }, OverlayPower::render);

        for(GameHudRender hudRender : GameHudRender.HUD_RENDERS) {
            hudRender.render(matrices, tickDelta);
        }
    }

    @ModifyArg(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V", ordinal = 1),
            index = 0
    )
    public Identifier changeStatusBarTextures(Identifier original) {
        Optional<OverrideHudTexturePower> power = PowerHolderComponent.getPowers(this.client.player, OverrideHudTexturePower.class).stream().findFirst();
        if (power.isPresent()) {
            return power.get().getStatusBarTexture();
        }
        return original;
    }
}
