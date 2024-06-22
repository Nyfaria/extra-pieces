package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.api.EPInitializer;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.debug.DebugItem;
import com.shnupbups.extrapieces.register.ModBlocks;
import com.shnupbups.extrapieces.register.ModConfigs;
import com.shnupbups.extrapieces.register.ModModels;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class ExtraPieces implements ModInitializer {
    public static final String mod_id = "extrapieces";
    public static final RuntimeResourcePack PACK = RuntimeResourcePack.create(mod_id + ":pack");
    public static final String mod_name = "Extra Pieces";
    public static final String piece_pack_version = "2.9.0";
    public static final Logger logger = LogManager.getFormatterLogger(mod_name);

    public static ItemGroup columnGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.COLUMN).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.column"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.COLUMN).asItem());
                        }
                    }
            ).build();

    public static ItemGroup slabGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.SLAB).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.slab"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.SLAB).asItem());
                        }
                    }
            ).build();

    public static ItemGroup stairGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.STAIRS).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.stairs"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.STAIRS).asItem());
                        }
                    }
            ).build();

    public static ItemGroup wallGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.WALL).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.wall"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.WALL).asItem());
                        }
                    }
            ).build();

    public static ItemGroup fenceGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.FENCE).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.fence"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.FENCE).asItem());
                        }
                    }
            ).build();

    public static ItemGroup fenceGateGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.FENCE_GATE).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.fence_gate"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.FENCE_GATE).asItem());
                        }
                    }
            ).build();

    public static ItemGroup layerGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.LAYER).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.layer"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.LAYER).asItem());
                        }
                    }
            ).build();

    public static ItemGroup cornerGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.CORNER).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.corner"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.CORNER).asItem());
                        }
                    }
            ).build();

    public static ItemGroup pillarGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.POST).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.post"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.POST).asItem());
                        }
                    }
            ).build();

    public static ItemGroup sidingGroup = ItemGroup.create(null, -1)
            .icon(() ->
                    new ItemStack(PieceSets.registry.values().stream().findFirst().get().getPieces().get(PieceTypes.SIDING).asItem())
            ).displayName(Text.translatable("itemGroup.extrapieces.siding"))
            .entries(
                    (displayContext, entries) -> {
                        for (PieceSet set : PieceSets.registry.values()) {
                            entries.add(set.getPieces().get(PieceTypes.SIDING).asItem());
                        }
                    }
            ).build();

    

    public static File configDir;
    public static File ppDir;

    public static Identifier getID(String path) {
        return new Identifier(mod_id, path);
    }

    public static void log(String out) {
        logger.info("[" + mod_name + "] " + out);
    }

    public static void debugLog(String out) {
        if (ModConfigs.debugOutput) log("[DEBUG] " + out);
    }

    public static void moreDebugLog(String out) {
        if (ModConfigs.moreDebugOutput) debugLog(out);
    }

    public static Identifier prependToPath(Identifier id, String prep) {
        return new Identifier(id.getNamespace(), prep + id.getPath());
    }

    public static Identifier appendToPath(Identifier id, String app) {
        return new Identifier(id.getNamespace(), id.getPath() + app);
    }

    public static File getConfigDirectory() {
        if (configDir == null) {
            configDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), mod_id);
            configDir.mkdirs();
        }
        return configDir;
    }

    public static File getPiecePackDirectory() {
        if (ppDir == null) {
            ppDir = new File(getConfigDirectory(), "piecepacks");
            ppDir.mkdirs();
        }
        return ppDir;
    }

    public static boolean isWoodmillInstalled() {
        return FabricLoader.getInstance().isModLoaded("woodmill");
    }

    public static void dump() {
//		if (ModConfigs.dumpData) {
//			try {
//				datapack..dumpResources(FabricLoader.getInstance().getConfigDirectory().getParent() + "/dump","dump");
//			} catch (Exception e) {
//				ExtraPieces.debugLog("BIG OOF: " + e.getMessage());
//			}
//		}
    }

    @Override
    public void onInitialize() {
//		RRPPreTest.main();
        ModConfigs.init();
        PieceTypes.init();
        FabricLoader.getInstance().getEntrypoints("extrapieces", EPInitializer.class).forEach(api -> {
            debugLog("EPInitializer " + api.toString());
            api.onInitialize();
        });
        Block block = new Block(AbstractBlock.Settings.create());
        Registry.register(Registries.BLOCK, getID("test_block"), block);


        ModConfigs.initPiecePacks();
        ModBlocks.init(PACK);

        ModModels.init(ExtraPieces.PACK);
//		ModBlocks.finish(PACK);
//		datapack = Artifice.registerData(getID("ep_data"), new ArtificeResourcePackImpl(ResourceType.SERVER_DATA,null, blah->ModBlocks.init((RuntimeResourcePack) blah)){});
        RRPCallback.BEFORE_USER.register(a -> a.add(PACK));

        Registry.register(Registries.ITEM, getID("debug_item"), new DebugItem());
        PACK.dumpDirect(Path.of("C:/Users/kinar/Desktop/ExtraPieces"));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (ModBlocks.setBuilders.size() != PieceSets.registry.size()) {
                for (PieceSet.Builder psb : ModBlocks.setBuilders.values()) {
                    if (!psb.isBuilt())
                        System.out.println("Piece Set " + psb.toString() + " could not be built, make sure the base and any vanilla pieces actually exist!");
                }
            }
        });
        Registry.register(Registries.ITEM_GROUP, getID("column_group"), columnGroup);
        Registry.register(Registries.ITEM_GROUP, getID("slab_group"), slabGroup);
        Registry.register(Registries.ITEM_GROUP, getID("stair_group"), stairGroup);
        Registry.register(Registries.ITEM_GROUP, getID("wall_group"), wallGroup);
        Registry.register(Registries.ITEM_GROUP, getID("fence_group"), fenceGroup);
        Registry.register(Registries.ITEM_GROUP, getID("fence_gate_group"), fenceGateGroup);
        Registry.register(Registries.ITEM_GROUP, getID("layer_group"), layerGroup);
        Registry.register(Registries.ITEM_GROUP, getID("corner_group"), cornerGroup);
        Registry.register(Registries.ITEM_GROUP, getID("pillar_group"), pillarGroup);
        Registry.register(Registries.ITEM_GROUP, getID("siding_group"), sidingGroup);

    }
}
