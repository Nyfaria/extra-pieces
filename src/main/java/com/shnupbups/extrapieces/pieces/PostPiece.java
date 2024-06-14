package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.PostPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;

public class PostPiece extends PieceType {
	public PostPiece() {
		super("post");
	}

	public PostPieceBlock getNew(PieceSet set) {
		return new PostPieceBlock(set);
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JState state = JState.state();
		for (Direction.Axis a : Direction.Axis.values()) {
			JVariant var = JState.variant();
			JBlockModel model = JState.model(getModelPath(pb)).uvlock();
			if (a != Direction.Axis.Y) {
				model.x(90);
				if (a == Direction.Axis.X) {
					model.y(90);
				}
			}
			state.add(var);
		}
		pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
	}
}
