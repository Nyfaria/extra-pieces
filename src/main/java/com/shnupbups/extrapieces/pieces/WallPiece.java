package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.WallPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class WallPiece extends PieceType {
	public WallPiece() {
		super("wall");
	}

	public WallPieceBlock getNew(PieceSet set) {
		return new WallPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "walls");
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 6, "bbb", "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "side");
		addBlockModel(pack, pb, "side_tall");
		addBlockModel(pack, pb, "inventory");
	}

	public void addItemModel(RuntimeResourcePack pack, PieceBlock pb) {
		JModel model = new JModel();
		model.parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registries.BLOCK.getId(pb.getBlock()), "_inventory"), "block/").toString());
		pack.addModel(model, Registries.ITEM.getId(getBlockItem(pb)));
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JBlockModel model = JState.model(getModelPath(pb));
		JState state = JState.state(JState.multipart(model).when(JState.when().add(Direction.UP.asString(),"true")));
		for (Direction d : Direction.values()) {
			if (d != Direction.UP && d != Direction.DOWN) {
				JBlockModel sideModel = JState.model(getModelPath(pb, "side")).uvlock();
				JMultipart part = JState.multipart();
				part.when(JState.when().add(d.asString(), "low"));
				switch (d) {
					case EAST:
						sideModel.y(90);
						break;
					case WEST:
						sideModel.y(270);
						break;
					case SOUTH:
						sideModel.y(180);
						break;
				}
				part.addModel(sideModel);
				state.add(part);
				JBlockModel sideTallModel = JState.model(getModelPath(pb, "side_tall")).uvlock();
				JMultipart partTall = JState.multipart();
				partTall.when(JState.when().add(d.asString(), "tall"));
				switch (d) {
					case EAST:
						sideTallModel.y(90);
						break;
					case WEST:
						sideTallModel.y(270);
						break;
					case SOUTH:
						sideTallModel.y(180);
						break;
				}
				partTall.addModel(sideTallModel);
				state.add(partTall);
			}
		}
		pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
	}
}
