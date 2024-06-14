package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import net.devtech.arrp.api.RuntimeResourcePack;

public class ModLootTables {

	static int l;

	public static void init(RuntimeResourcePack data) {
		for (PieceSet ps : PieceSets.registry.values()) {
			ps.addLootTables(data);
		}
		ExtraPieces.debugLog("Registered " + l + " loot tables!");
	}

	public static void incrementLootTables() {
		l++;
	}
}
