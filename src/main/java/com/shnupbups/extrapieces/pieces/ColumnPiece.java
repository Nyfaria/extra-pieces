package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.ColumnPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import io.github.vampirestudios.artifice.api.builder.assets.BlockStateBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ColumnPiece extends PieceType {
	public ColumnPiece() {
		super("column");
	}

	public ColumnPieceBlock getNew(PieceSet set) {
		return new ColumnPieceBlock(set);
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		BlockStateBuilder state = new BlockStateBuilder();
		for (Direction.Axis a : Direction.Axis.values()){
			if (a != Direction.Axis.Y){
				state.variant("axis=" + a.asString(),new BlockStateBuilder.Variant()
						.uvlock(true)
						.model(getModelPath(pb)).rotationX(90));
				if (a == Direction.Axis.X) {
					state.variant("axis=" + a.asString(), new BlockStateBuilder.Variant()
							.uvlock(true)
							.model(getModelPath(pb)).rotationY(90));
				}
			}
		}
		pack.addBlockState(Registries.BLOCK.getId(pb.getBlock()), state);
	}

//
//		pack.addBlockState(Registries.BLOCK.getId(pb.getBlock()), state -> {
//			for (Direction.Axis a : Direction.Axis.values()) {
//				state.variant("axis=" + a.asString(), var -> {
//					var.uvlock(true);
//					var.model(getModelPath(pb));
//					if (a != Direction.Axis.Y) {
//						var.rotationX(90);
//						if (a == Direction.Axis.X) {
//							var.rotationY(90);
//						}
//					}
//				});
//			}
//		});
//	}
}
