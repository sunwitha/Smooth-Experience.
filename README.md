# Smooth Experience — Fabric 1.21.11

Client-side Fabric project for:
- Inventory screen scale-in animation.
- Player-list (Tab) scale-in animation.
- Camera position interpolation when switching perspective with F5.

Default UI animation: 250 ms, scale 0.90 → 1.00, cubic ease-out.

## Build
1. Install JDK 21.
2. Install Gradle (or add the Gradle wrapper).
3. Open this folder in IntelliJ IDEA as a Gradle project.
4. Run `gradle build`.
5. The jar should appear in `build/libs/`.
6. Put the jar in `.minecraft/mods` together with Fabric API.

## Important
This is a source project, not a precompiled/tested JAR. Minecraft 1.21.11 uses changing client rendering internals; compile it first and test on a copy of your profile. The initial implementation animates the opening transition. Closing animation for Tab and the inventory is not yet implemented.
