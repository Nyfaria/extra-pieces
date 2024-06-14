package com.shnupbups.extrapieces.pieces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.LayerPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class LayerPiece extends PieceType {
    public LayerPiece() {
        super("layer");
    }

    public LayerPieceBlock getNew(PieceSet set) {
        return new LayerPieceBlock(set);
    }

    public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
        ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
        recipes.add(new ShapedPieceRecipe(this, 12, "bbb").addToKey('b', PieceTypes.SLAB));
        return recipes;
    }

    public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
        addBlockModel(pack, pb, "height_2");
        addBlockModel(pack, pb, "height_4");
        addBlockModel(pack, pb, "height_6");
        addBlockModel(pack, pb, "height_8");
        addBlockModel(pack, pb, "height_10");
        addBlockModel(pack, pb, "height_12");
        addBlockModel(pack, pb, "height_14");
        addBlockModel(pack, pb, "height_16");
    }

    public void addItemModel(RuntimeResourcePack pack, PieceBlock pb) {
        JModel model = new JModel();
        model.parent(getModelPath(pb, "block/layer/height_2").toString());
        pack.addModel(model, Registries.ITEM.getId(getBlockItem(pb)));
    }

    public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
        JState state = new JState();
        for (Direction dir : Direction.values()) {
            for (int i = 1; i <= 8; i++) {
                final int j = i * 2;
                JVariant var = JState.variant();
                JBlockModel model = JState.model(getModelPath(pb, "height_" + j));
                model.uvlock();
                switch (dir) {
                    case DOWN:
                        model.x(180);
                        break;
                    case NORTH:
                        model.x(90);
                        break;
                    case SOUTH:
                        model.x(90);
                        model.y(180);
                        break;
                    case EAST:
                        model.y(90);
                        model.x(90);
                        break;
                    case WEST:
                        model.x(90);
                        model.y(270);
                        break;
                }
                var.put("facing=" + dir.asString() + ",layers=" + i, model);
                state.add(var);
            }
        }
        pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
    }

    public int getStonecuttingCount() {
        return 8;
    }

    @Override
    public void addLootTable(RuntimeResourcePack data, PieceBlock pb) {
        JLootTable loot = new JLootTable("block");
        JPool pool = new JPool();
        pool.rolls(1);
        JEntry entry = new JEntry();
        entry.type("minecraft:item");
        entry.name(Registries.BLOCK.getId(pb.getBlock()).toString());
        for (int i = 1; i <= 8; i++) {
            JFunction function = new JFunction("set_count");
            function.parameter("count",i);
            JCondition condition = JLootTable.predicate("block_state_property");
            condition.parameter("block", Registries.BLOCK.getId(pb.getBlock()));
            JsonObject properties = new JsonObject();
            properties.addProperty("layers", i);
            condition.parameter("properties", properties);
            entry.function(function);
        }
        entry.function("explosion_decay");
        pool.entry(entry);
        loot.pool(pool);
        data.addLootTable(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "blocks/"), loot);
    }
}
