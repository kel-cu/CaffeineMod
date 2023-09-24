package ru.kelcuprum.caffeine.screens;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.kelcuprum.caffeine.CaffeineMod;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (CaffeineMod.yetAnotherConfigLibV3) {
            return ConfigScreen::buildScreen;
        } else {
            return null;
        }
    }
}