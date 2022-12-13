package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import io.github.vampirestudios.artifice.api.ArtificeResourcePack;

public class ModModels {

	public static void init(ArtificeResourcePack.ClientResourcePackBuilder pack) {
		int m = 0;
		for (PieceSet set : PieceSets.registry.values()) {
			for (PieceBlock pb : set.getPieceBlocks()) {
				if (!set.isVanillaPiece(pb.getType())) {
					pb.getType().addModels(pack, pb);
					pb.getType().addBlockstate(pack, pb);
					m++;
				}
			}
		}
		ExtraPieces.debugLog("Added models and blockstates for " + m + " blocks!");
	}
}
