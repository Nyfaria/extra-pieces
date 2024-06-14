package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;

public final class BasePiece extends PieceType {
	public BasePiece() {
		super("base");
	}

	public String getBlockId(String baseName) {
		return baseName;
	}

	public PieceBlock getNew(PieceSet set) {
		return (PieceBlock) set.getBase();
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
	}
}
