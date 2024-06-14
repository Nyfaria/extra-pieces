package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public abstract class PieceRecipe {
	private PieceType output;
	private int count;

	public PieceRecipe(PieceType output, int count) {
		this.output = output;
		this.count = count;
	}
	
	public PieceRecipe(PieceType output) {
		this(output, 1);
	}

	public PieceType getOutput() {
		return output;
	}

	public int getCount() {
		return count;
	}

	public Block getOutput(PieceSet set) {
		return set.getPiece(getOutput());
	}

	public abstract void add(RuntimeResourcePack data, Identifier id, PieceSet set);

	public abstract boolean canAddForSet(PieceSet set);
}
