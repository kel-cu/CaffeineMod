package ru.kelcuprum.caffeine.screens.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.impl.controller.BooleanControllerBuilderImpl;
import ru.kelcuprum.caffeine.CaffeineMod;
import ru.kelcuprum.caffeine.localization.Localization;

public class MainConfigs {
    public ConfigCategory getCategory() {
        CaffeineMod.config.load();
        ConfigCategory.Builder category = ConfigCategory.createBuilder()
                .name(Localization.getText("caffeine.config"));
        category.option(Option.createBuilder(boolean.class)
                .description(OptionDescription.createBuilder().text(Localization.getText("caffeine.config.debug.tooltip")).build())
                .name(Localization.getText("caffeine.config.debug"))
                .binding(false, () -> CaffeineMod.config.getBoolean("DEBUG", false), newVal -> CaffeineMod.config.setBoolean("DEBUG", newVal))
                .controller(BooleanControllerBuilderImpl::new)
                .build());
        return category.build();
    }
}
