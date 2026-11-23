package onepiom.smooth;

public final class Animation {
    public static final long DURATION_MS = 250L;
    public static final float START_SCALE = 0.90f;

    private Animation() {}

    public static float progress(long startNanos) {
        float t = (System.nanoTime() - startNanos) / (DURATION_MS * 1_000_000.0f);
        t = Math.max(0.0f, Math.min(1.0f, t));
        // Smooth cubic ease-out.
        return 1.0f - (float) Math.pow(1.0f - t, 3.0);
    }

    public static float scale(long startNanos) {
        return START_SCALE + (1.0f - START_SCALE) * progress(startNanos);
    }
}
