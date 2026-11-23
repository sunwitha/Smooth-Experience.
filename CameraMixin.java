package onepiom.smooth.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private Vec3d pos;
    @Shadow protected abstract void setPos(double x, double y, double z);

    @Unique private static boolean smoothExperience$hadPerspective = false;
    @Unique private static boolean smoothExperience$lastThirdPerson = false;
    @Unique private static long smoothExperience$startedAt = 0L;
    @Unique private static Vec3d smoothExperience$from = null;
    @Unique private static Vec3d smoothExperience$lastRendered = null;

    @Inject(method = "update", at = @At("TAIL"))
    private void smoothExperience$animateCamera(net.minecraft.world.World area,
                                                 net.minecraft.entity.Entity focusedEntity,
                                                 boolean thirdPerson, boolean inverseView,
                                                 float tickProgress, CallbackInfo ci) {
        Vec3d target = this.pos;

        if (!smoothExperience$hadPerspective || thirdPerson != smoothExperience$lastThirdPerson) {
            smoothExperience$hadPerspective = true;
            smoothExperience$lastThirdPerson = thirdPerson;
            smoothExperience$startedAt = System.nanoTime();
            smoothExperience$from = smoothExperience$lastRendered != null
                    ? smoothExperience$lastRendered : target;
        }

        if (smoothExperience$from != null) {
            float t = (System.nanoTime() - smoothExperience$startedAt) / 250_000_000.0f;
            t = Math.max(0.0f, Math.min(1.0f, t));
            float eased = 1.0f - (float) Math.pow(1.0f - t, 3.0);
            Vec3d blended = smoothExperience$from.lerp(target, eased);
            this.setPos(blended.x, blended.y, blended.z);
        }

        smoothExperience$lastRendered = this.pos;
    }
}
