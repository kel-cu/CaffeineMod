package ru.kelcuprum.caffeine.info;

import net.minecraft.client.Minecraft;

public class World {
    static Minecraft CLIENT = Minecraft.getInstance();
    public static String getParticles(){
        return CLIENT.level == null ? "-" : CLIENT.particleEngine.countParticles();
    }
    public static String getEntities(){
        return CLIENT.level == null ? "-" : String.valueOf(CLIENT.level.getEntityCount());
    }
    public static String getChunksRenderer(){
        return CLIENT.level == null ? "-" : String.valueOf(CLIENT.levelRenderer.countRenderedChunks());
    }
    public static String getChunksSettings(){
        return CLIENT.level == null ? "-" : String.valueOf(CLIENT.options.renderDistance().get());
    }
    public static String getGraphicMode(){
        return CLIENT.options.graphicsMode().get().name();
    }
    public static String getGamma(){
        return String.valueOf(CLIENT.options.gamma().get()*100);
    }
}
