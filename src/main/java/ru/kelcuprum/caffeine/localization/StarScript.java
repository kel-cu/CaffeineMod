package ru.kelcuprum.caffeine.localization;

import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.Section;
import meteordevelopment.starscript.StandardLib;
import meteordevelopment.starscript.Starscript;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.Error;
import meteordevelopment.starscript.utils.StarscriptError;
import meteordevelopment.starscript.value.Value;
import meteordevelopment.starscript.value.ValueMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import org.apache.logging.log4j.Level;
import ru.kelcuprum.caffeine.CaffeineMod;
import ru.kelcuprum.caffeine.info.World;

import java.text.SimpleDateFormat;

public class StarScript {
    public static Starscript ss = new Starscript();
    static Minecraft mc = Minecraft.getInstance();

    public static void init() {
        StandardLib.init(ss);
        ss.set("caffeine", new ValueMap()
                .set("version", () -> Value.string(FabricLoader.getInstance().getModContainer(CaffeineMod.MOD_ID).get().getMetadata().getVersion().getFriendlyString()))
        );
        // General
        ss.set("minecraft", new ValueMap()
                .set("version", () -> Value.string(SharedConstants.getCurrentVersion().getName()))
                .set("loader", () -> Value.string(Minecraft.getInstance().getVersionType()))
                .set("settings", new ValueMap()
                        .set("distance", () -> Value.string(mc.level != null ? World.getChunksSettings() : "-"))
                        .set("graphic", () -> Value.string(World.getGraphicMode()))
                        .set("gamma", () -> Value.string(World.getGamma()))
                )
        );
        ss.set("fps", () -> Value.number(mc.getFps()));
        ss.set("time", () -> Value.string(new SimpleDateFormat(Localization.getLocalization("date.time", false)).format(System.currentTimeMillis())));
        // World
        ss.set("world", new ValueMap()
                .set("entities", () -> Value.string(mc.level != null ? World.getEntities() : "-"))
                .set("particles", () -> Value.string(mc.level != null ? World.getParticles() : "-"))
                .set("difficulty", () -> Value.string(mc.level != null ? mc.level.getDifficulty().getDisplayName().getString() : ""))
                .set("chunks", new ValueMap()
                        .set("renderer", () -> Value.string(mc.level != null ? World.getChunksRenderer() : "-"))
                )
        );
    }
    // Helpers

    public static Script compile(String source) {
        Parser.Result result = Parser.parse(source);

        if (result.hasErrors()) {
            for (Error error : result.errors) CaffeineMod.log(error.message, Level.ERROR);
            return null;
        }

        return Compiler.compile(result);
    }

    public static Section runSection(Script script, StringBuilder sb) {
        try {
            return ss.run(script, sb);
        }
        catch (StarscriptError error) {
            error.printStackTrace();
            return null;
        }
    }
    public static String run(Script script, StringBuilder sb) {
        Section section = runSection(script, sb);
        return section != null ? section.toString() : null;
    }

    public static Section runSection(Script script) {
        return runSection(script, new StringBuilder());
    }
    public static String run(Script script) {
        return run(script, new StringBuilder());
    }
}
