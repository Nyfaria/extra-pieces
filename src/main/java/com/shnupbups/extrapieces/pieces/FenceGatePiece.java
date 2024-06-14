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
		for (Direction d : Direction.values()) {
			if (d != Direction.UP && d != Direction.DOWN) {
				JBlockModel model = JState.model(getModelPath(pb)).uvlock();
				JVariant var = new JVariant();
				setYRot(d, var, model);
				var.put("facing=" + d.asString() + ",in_wall=false,open=false", model);
				JVariant var4 = new JVariant();
				JBlockModel model4 = JState.model(getModelPath(pb, "wall")).uvlock();
				setYRot(d, var4, model4);
				var4.put("facing=" + d.asString() + ",in_wall=true,open=false", model4);
				JVariant var3 = new JVariant();
				JBlockModel model3 = JState.model(getModelPath(pb, "open")).uvlock();
				setYRot(d, var3, model3);
				var3.put("facing=" + d.asString() + ",in_wall=false,open=true", model3);
				JVariant var2 = new JVariant();
				JBlockModel model2 = JState.model(getModelPath(pb, "wall_open")).uvlock();
				setYRot(d, var2, model2);
				var2.put("facing=" + d.asString() + ",in_wall=true,open=true", model2);
				state.add(var);
				state.add(var2);
				state.add(var3);
				state.add(var4);
			}
		}
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
