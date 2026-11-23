package onepiom.smooth.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import onepiom.smooth.Animation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Shadow @org.spongepowered.asm.mixin.Final private net.minecraft.client.MinecraftClient client;
    @Unique private long smoothExperience$openedAt = -1L;

    @Inject(method = "render", at = @At("HEAD"))
    private void smoothExperience$begin(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard,
                                        ScoreboardObjective objective, CallbackInfo ci) {
        if (smoothExperience$openedAt < 0L) smoothExperience$openedAt = System.nanoTime();

        var matrices = context.getMatrices();
        float s = Animation.scale(smoothExperience$openedAt);
        matrices.pushMatrix();
        matrices.translate(scaledWindowWidth / 2.0f, client.getWindow().getScaledHeight() / 2.0f);
        matrices.scale(s, s);
        matrices.translate(-scaledWindowWidth / 2.0f, -client.getWindow().getScaledHeight() / 2.0f);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void smoothExperience$end(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard,
                                      ScoreboardObjective objective, CallbackInfo ci) {
        context.getMatrices().popMatrix();
    }
}
