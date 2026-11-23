package onepiom.smooth;

import net.fabricmc.api.ClientModInitializer;

public final class SmoothExperienceClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Client-only mod. Animation state is managed by the mixins.
    }
}
