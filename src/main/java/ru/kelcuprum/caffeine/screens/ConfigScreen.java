package ru.kelcuprum.caffeine.screens;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.client.gui.screens.Screen;
import ru.kelcuprum.caffeine.CaffeineMod;
import ru.kelcuprum.caffeine.localization.Localization;
import ru.kelcuprum.caffeine.screens.config.MainConfigs;

public class ConfigScreen {
    public static Screen buildScreen (Screen currentScreen) {
        YetAnotherConfigLib.Builder screen = YetAnotherConfigLib.createBuilder()
                .title(Localization.getText("caffeine.name"))
                .save(ConfigScreen::save);
        screen.category(new MainConfigs().getCategory());
        return screen.build().generateScreen(currentScreen);
    }
    private static void save(){
        CaffeineMod.log("Saving settings...");
        CaffeineMod.config.save();
    }
}
