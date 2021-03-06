package com.github.alexthe666.iceandfire.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.util.IAFBiomeUtil;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public enum EnumHippogryphTypes {
    BLACK(false),
    BROWN(false),
    GRAY(false),
    CHESTNUT(false),
    CREAMY(false),
    DARK_BROWN(false),
    WHITE(false),
    RAPTOR(true),
    ALEX(true),
    DODO(true);

    public boolean developer;
    public ResourceLocation TEXTURE;
    public ResourceLocation TEXTURE_BLINK;

    EnumHippogryphTypes(boolean developer) {
        this.developer = developer;
        this.TEXTURE = new ResourceLocation("iceandfire:textures/models/hippogryph/" + name().toLowerCase(Locale.ROOT) + ".png");
        this.TEXTURE_BLINK = new ResourceLocation("iceandfire:textures/models/hippogryph/" + name().toLowerCase(Locale.ROOT) + "_blink.png");

    }

    public static EnumHippogryphTypes[] getWildTypes() {
        return new EnumHippogryphTypes[]{BLACK, BROWN, GRAY, CHESTNUT, CREAMY, DARK_BROWN, WHITE};
    }

    public static EnumHippogryphTypes getRandomType() {
        return getWildTypes()[new Random().nextInt(getWildTypes().length - 1)];
    }

    public static EnumHippogryphTypes getBiomeType(Biome biome) {
        List<EnumHippogryphTypes> types = new ArrayList<EnumHippogryphTypes>();
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.blackHippogryphBiomes, biome)) {
            types.add(BLACK);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.brownHippogryphBiomes, biome)) {
            types.add(BROWN);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.grayHippogryphBiomes, biome)) {
            types.add(BROWN);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.chestnutHippogryphBiomes, biome)) {
            types.add(CHESTNUT);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.creamyHippogryphBiomes, biome)) {
            types.add(CREAMY);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.darkBrownHippogryphBiomes, biome)) {
            types.add(DARK_BROWN);
        }
        if (IAFBiomeUtil.parseListForBiomeCheck(BiomeConfig.whiteHippogryphBiomes, biome)) {
            types.add(WHITE);
        }
        if (types.isEmpty()) {
            return getRandomType();
        } else {
            if (types.contains(GRAY) && types.contains(CHESTNUT)) {
                return GRAY;
            }
            return types.get(new Random().nextInt(types.size()));
        }

    }
}
