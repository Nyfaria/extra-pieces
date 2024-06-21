package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.FenceGatePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class FenceGatePiece extends PieceType {
	public FenceGatePiece() {
		super("fence_gate");
	}

	public FenceGatePieceBlock getNew(PieceSet set) {
		return new FenceGatePieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 1, "sbs", "sbs").addToKey('b', PieceTypes.BASE).addToKey('s', Items.STICK));
		return recipes;
	}

	public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "wall");
		addBlockModel(pack, pb, "open");
		addBlockModel(pack, pb, "wall_open");
	}

	//todo: make sure correct
	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JState state = JState.state();
		JVariant var = new JVariant();
		for (Direction d : Direction.values()) {
			if (d != Direction.UP && d != Direction.DOWN) {
				JBlockModel model = JState.model(getModelPath(pb)).uvlock();
				setYRot(d, var, model);
				var.put("facing=" + d.asString() + ",in_wall=false,open=false", model);
				JBlockModel model4 = JState.model(getModelPath(pb, "wall")).uvlock();
				setYRot(d, var, model4);
				var.put("facing=" + d.asString() + ",in_wall=true,open=false", model4);
				JBlockModel model3 = JState.model(getModelPath(pb, "open")).uvlock();
				setYRot(d, var, model3);
				var.put("facing=" + d.asString() + ",in_wall=false,open=true", model3);
				JBlockModel model2 = JState.model(getModelPath(pb, "wall_open")).uvlock();
				setYRot(d, var, model2);
				var.put("facing=" + d.asString() + ",in_wall=true,open=true", model2);
			}
		}
		state.add(var);
		pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
	}

	private static void setYRot(Direction d, JVariant var, JBlockModel model) {
		switch (d) {
			case NORTH:
				model.y( 180);
				break;
			case WEST:
				model.y( 90);
				break;
			case EAST:
				model.y( 270);
				break;
		}
	}
}
