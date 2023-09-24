package ru.kelcuprum.caffeine;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.caffeine.config.Config;
import ru.kelcuprum.caffeine.localization.StarScript;
import ru.kelcuprum.caffeine.screens.ConfigScreen;

public class CaffeineMod implements ClientModInitializer {
    public static final String MOD_ID = "caffeinemod";
    public static final Logger LOG = LogManager.getLogger("Caffeine");
    public static Config config = new Config("config/Caffeine/config.json");
    public static void log(String message) { log(message, Level.INFO);}
    public static void log(String message, Level level) { LOG.log(level, "[" + LOG.getName() + "] " + message); }
    public static Boolean yetAnotherConfigLibV3 = FabricLoader.getInstance().getModContainer("yet_another_config_lib_v3").isPresent();
    @Override
    public void onInitializeClient() {
        StarScript.init();
        if(yetAnotherConfigLibV3){
            KeyMapping openConfigKeyBind;
            openConfigKeyBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                    "caffeine.key.openConfig",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                    "caffeine.name"
            ));
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                assert client.player != null;
                while (openConfigKeyBind.consumeClick()) {
                    final Screen current = client.screen;
                    Screen configScreen = ConfigScreen.buildScreen(current);
                    client.setScreen(configScreen);
                }
            });
        }
        KeyMapping toggleKeyBind;
        toggleKeyBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "caffeine.key.debug",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "caffeine.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            while (toggleKeyBind.consumeClick()) {
                config.setBoolean("DEBUG", !config.getBoolean("DEBUG", false));
                config.save();
            }
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            config.load();
            log("Client started!");
            HUDHandler hud = new HUDHandler();
            HudRenderCallback.EVENT.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }
}