package onepiom.smooth.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.Screen;
import onepiom.smooth.Animation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends Screen {
    @Unique private long smoothExperience$openedAt = -1L;

    protected InventoryScreenMixin() {
        super(null);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void smoothExperience$begin(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (smoothExperience$openedAt < 0L) smoothExperience$openedAt = System.nanoTime();

        var matrices = context.getMatrices();
        float s = Animation.scale(smoothExperience$openedAt);
        matrices.pushMatrix();
        matrices.translate(this.width / 2.0f, this.height / 2.0f);
        matrices.scale(s, s);
        matrices.translate(-this.width / 2.0f, -this.height / 2.0f);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void smoothExperience$end(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.getMatrices().popMatrix();
    }
}
