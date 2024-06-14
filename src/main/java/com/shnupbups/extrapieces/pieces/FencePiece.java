package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.FencePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class FencePiece extends PieceType {
    public FencePiece() {
        super("fence");
    }

    public FencePieceBlock getNew(PieceSet set) {
        return new FencePieceBlock(set);
    }

    public Identifier getTagId() {
        return new Identifier("minecraft", "fences");
    }

    public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
        ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
        recipes.add(new ShapedPieceRecipe(this, 3, "bsb", "bsb").addToKey('b', PieceTypes.BASE).addToKey('s', Items.STICK));
        return recipes;
    }

    public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
        super.addBlockModels(pack, pb);
        addBlockModel(pack, pb, "side");
        addBlockModel(pack, pb, "inventory");
    }

    public void addItemModel(RuntimeResourcePack pack, PieceBlock pb) {
        JModel model = new JModel();
        model.parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registries.BLOCK.getId(pb.getBlock()), "_inventory"), "block/").toString());
        pack.addModel(model, Registries.ITEM.getId(getBlockItem(pb)));
    }

    public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
        JBlockModel model = JState.model(getModelPath(pb));
        JState state = JState.state(JState.multipart(model));
        for (Direction d : Direction.values()) {
            if (d != Direction.UP && d != Direction.DOWN) {
                JBlockModel var = JState.model(getModelPath(pb, "side")).uvlock();
                JMultipart part = JState.multipart();
                part.when(JState.when().add(d.asString(), "true"));
                switch (d) {
                    case EAST:
                        var.y(90);
                        break;
                    case WEST:
                        var.y(270);
                        break;
                    case SOUTH:
                        var.y(180);
                        break;
                }
                part.addModel(var);
                state.add(part);
            }
        }
        pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
    }
}
