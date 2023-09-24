package ru.kelcuprum.caffeine;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.caffeine.localization.Localization;

import java.util.List;

public class HUDHandler implements HudRenderCallback, ClientTickEvents.StartTick {
    private final List<Component> textList = new ObjectArrayList<>();

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public void onStartTick(Minecraft client) {
        this.textList.clear();
        if (!CaffeineMod.config.getBoolean("DEBUG", false)) return;
        this.textList.add(Localization.toText(Localization.getParsedText("Caffeine {caffeine.version} • Minecraft: {minecraft.version} ({minecraft.loader})", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Renderer particles: {world.particles}", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Renderer chunks: {world.chunks.renderer} ", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Difficulty: {world.difficulty}", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Entities: {world.entities}", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Graphics: {minecraft.settings.graphic}", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("Gamma: {minecraft.settings.gamma}", false)));
        this.textList.add(Localization.toText(Localization.getParsedText("FPS: {fps} ", false)));
    }
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        boolean isDebugOverlay = this.client.options.renderDebug;
        if (CaffeineMod.config.getBoolean("DEBUG", false)) {
            int l = 0;
            int x;
            int y;
            for (Component text : this.textList) {
                if (isDebugOverlay) return;
                x = 10;
                y = 10 + (l*this.client.font.lineHeight);
                this.drawString(drawContext, text, x, y);
                l++;
            }
        }
    }

    private void drawString(GuiGraphics guiGraphics, Component text, int x, int y) {
        guiGraphics.drawString(this.client.font, text, x, y, 16777215);
    }
}
