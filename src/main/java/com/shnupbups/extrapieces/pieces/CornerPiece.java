package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.CornerPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class CornerPiece extends PieceType {
	public CornerPiece() {
		super("corner");
	}

	public CornerPieceBlock getNew(PieceSet set) {
		return new CornerPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 4, "bbb", "bb ", "b  ").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JState state = new JState();
		JVariant var = JState.variant();
		for (Direction d : Direction.values()) {
			if (d != Direction.UP && d != Direction.DOWN) {
				JBlockModel model = JState.model(getModelPath(pb)).uvlock();
				switch (d) {
					case SOUTH:
						model.y(180);
						break;
					case EAST:
						model.y(90);
						break;
					case WEST:
						model.y(270);
						break;
				}
				var.put("facing=" + d.asString(), model);
			}
		}
		state.add(var);
		pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
	}
}
