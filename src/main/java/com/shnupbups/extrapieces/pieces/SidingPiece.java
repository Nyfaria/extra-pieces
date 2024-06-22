package com.shnupbups.extrapieces.pieces;

import com.google.gson.JsonObject;
import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.SidingPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.register.ModProperties;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class SidingPiece extends PieceType {
    public SidingPiece() {
        super("siding");
    }

    public SidingPieceBlock getNew(PieceSet set) {
        return new SidingPieceBlock(set);
    }

    public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
        ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
        recipes.add(new ShapedPieceRecipe(this, 6, "b", "b", "b").addToKey('b', PieceTypes.BASE));
        return recipes;
    }

    public int getStonecuttingCount() {
        return 2;
    }

    @Override
    public void addLootTable(RuntimeResourcePack data, PieceBlock pb) {
        JLootTable loot = new JLootTable("minecraft:block");
        JPool pool = new JPool();
        pool.rolls(1);
        JEntry entry = new JEntry();
        entry.type("minecraft:item");
        entry.name(Registries.BLOCK.getId(pb.getBlock()).toString());
        JFunction func = new JFunction("set_count");
        func.parameter("count", 2);
        JCondition cond = new JCondition("minecraft:block_state_property");
        cond.parameter("block", Registries.BLOCK.getId(pb.getBlock()).toString());
        JsonObject prop = new JsonObject();
        prop.addProperty("type", "double");
        cond.parameter("properties", prop);
        func.condition(cond);
        entry.function(func);
        entry.function(new JFunction("minecraft:explosion_decay"));
        pool.entry(entry);
        loot.pool(pool);

        data.addLootTable(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "blocks/"), loot);
    }

    public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
        super.addBlockModels(pack, pb);
        addBlockModel(pack, pb, "double");
    }

    public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
        JState state = new JState();
        JVariant var = new JVariant();
        for (ModProperties.SidingType t : ModProperties.SidingType.values()) {
            switch (t) {
                case SINGLE:
                    for (Direction d : Direction.values()) {
                        if (!(d.equals(Direction.DOWN) || d.equals(Direction.UP))) {
                            JBlockModel model = JState.model(getModelPath(pb)).uvlock();
                            switch (d) {
                                case EAST:
                                    model.y(90);
                                    break;
                                case WEST:
                                    model.y(270);
                                    break;
                                case SOUTH:
                                    model.y(180);
                                    break;
                            }
                            var.put("type=" + t.asString() + ",facing=" + d.asString(), model);
                        }
                    }
                    break;
                case DOUBLE:
                    JBlockModel model = JState.model(getModelPath(pb, "double"));
                    var.put("type=" + t.asString(), model);
                    break;
            }

        }
        state.add(var);

        pack.addBlockState(state, Registries.BLOCK.getId(pb.getBlock()));
    }
}
