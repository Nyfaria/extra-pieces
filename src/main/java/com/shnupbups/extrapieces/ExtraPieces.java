package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.api.EPInitializer;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
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
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtraPieces implements ModInitializer {
    public static final String mod_id = "extrapieces";
    public static final RuntimeResourcePack PACK = RuntimeResourcePack.create(mod_id + ":pack");
    public static final String mod_name = "Extra Pieces";
    public static final String piece_pack_version = "2.9.0";
    public static final Logger logger = LogManager.getFormatterLogger(mod_name);

    public static ItemGroup registerItemGroup(String groupId, PieceType type) {
        return ItemGroup.create(null, -1)
                .icon(() ->
                        new ItemStack(PieceSets.registry.get(Blocks.STONE).getPieces().get(type).asItem())
                ).displayName(Text.translatable("itemGroup.extrapieces." + groupId))
                .entries(
                        (displayContext, entries) -> {
                            if (ModConfigs.columns) {
                                Registries.BLOCK.forEach(item -> {
                                    if (item instanceof PieceBlock && ((PieceBlock) item).getType() == type)
                                        try {
                                            entries.add(new ItemStack(item, 1));
                                        } catch (Exception e) {
                                        }
                                });
                            }
                        }
                ).build();
    }


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

        RRPCallback.BEFORE_USER.register(a -> {
            ModConfigs.initPiecePacks();
            ModBlocks.init(PACK);
            ModModels.init(PACK);
            a.add(PACK);
        });

        Registry.register(Registries.ITEM, getID("debug_item"), new DebugItem());
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (ModBlocks.setBuilders.size() != PieceSets.registry.size()) {
                for (PieceSet.Builder psb : ModBlocks.setBuilders.values()) {
                    if (!psb.isBuilt())
                        System.out.println("Piece Set " + psb.toString() + " could not be built, make sure the base and any vanilla pieces actually exist!");
                }
            }
        });
        if (ModConfigs.columns) {
            Registry.register(Registries.ITEM_GROUP, getID("column_group"), registerItemGroup("column", PieceTypes.COLUMN));
        }
        if (ModConfigs.slabs) {
            Registry.register(Registries.ITEM_GROUP, getID("slab_group"), registerItemGroup("slab", PieceTypes.SLAB));
        }
        if (ModConfigs.stairs) {
            Registry.register(Registries.ITEM_GROUP, getID("stair_group"), registerItemGroup("stair", PieceTypes.STAIRS));
        }
        if (ModConfigs.walls) {
            Registry.register(Registries.ITEM_GROUP, getID("wall_group"), registerItemGroup("wall", PieceTypes.WALL));
        }
        if (ModConfigs.fences) {
            Registry.register(Registries.ITEM_GROUP, getID("fence_group"), registerItemGroup("fence", PieceTypes.FENCE));
        }
        if (ModConfigs.fenceGates) {
            Registry.register(Registries.ITEM_GROUP, getID("fence_gate_group"), registerItemGroup("fence_gate", PieceTypes.FENCE_GATE));
        }
        if (ModConfigs.layers) {
            Registry.register(Registries.ITEM_GROUP, getID("layer_group"), registerItemGroup("layer", PieceTypes.LAYER));
        }
        if (ModConfigs.corners) {
            Registry.register(Registries.ITEM_GROUP, getID("corner_group"), registerItemGroup("corner", PieceTypes.CORNER));
        }
        if (ModConfigs.posts) {
            Registry.register(Registries.ITEM_GROUP, getID("pillar_group"), registerItemGroup("post", PieceTypes.POST));
        }
        if (ModConfigs.sidings) {
            Registry.register(Registries.ITEM_GROUP, getID("siding_group"), registerItemGroup("siding", PieceTypes.SIDING));
        }
    }
}
