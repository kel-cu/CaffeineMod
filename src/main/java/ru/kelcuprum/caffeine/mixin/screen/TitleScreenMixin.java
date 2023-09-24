package ru.kelcuprum.caffeine.mixin.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.caffeine.localization.Localization;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin() {
        super(null);
    }
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo cl) {
        guiGraphics.drawString(this.font, Localization.getParsedText("Caffeine {caffeine.version}", false), 2, this.height - (10*2), 16777215);
    }
}
