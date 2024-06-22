package com.shnupbups.extrapieces.pieces;

import com.google.gson.JsonObject;
import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.SlabPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JFunction;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SlabPiece extends PieceType {
	public SlabPiece() {
		super("slab");
	}

	public SlabPieceBlock getNew(PieceSet set) {
		return new SlabPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "slabs");
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 6, "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public int getStonecuttingCount() {
		return 2;
	}

	@Override
	public void addLootTable(RuntimeResourcePack data, PieceBlock pb) {
		JLootTable loot = new JLootTable("minecraft:block");
		JPool pool = new JPool();
		pool.rolls(1);
		pool.bonus(0);
		JEntry entry = new JEntry();
		entry.type("minecraft:item");
		entry.name(Registries.BLOCK.getId(pb.getBlock()).toString());
		JFunction function = new JFunction("minecraft:set_count");
		function.parameter("add", false);
		JCondition condition = new JCondition("minecraft:block_state_property");
		condition.parameter("block", Registries.BLOCK.getId(pb.getBlock()).toString());
		function.parameter("count", 2);
		JsonObject properties = new JsonObject();
		properties.addProperty("type", "double");
		condition.parameter("properties", properties);
		function.condition(condition);
		entry.function(function);
		entry.function(new JFunction("minecraft:explosion_decay"));
		pool.entry(entry);
		loot.pool(pool);
		data.addLootTable(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "blocks/"), loot);
	}

	public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "top");
		addBlockModel(pack, pb, "double");
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JVariant var = JState.variant();
		for(boolean b : Properties.WATERLOGGED.getValues()) {
			for (SlabType t : SlabType.values()) {
				switch (t) {
					case BOTTOM:
						var.put("type=" + t.asString() +",waterlogged=" + b, JState.model(getModelPath(pb)));
						break;
					case TOP:
						var.put("type=" + t.asString() +",waterlogged=" + b, JState.model(getModelPath(pb, "top")));
						break;
					case DOUBLE:
						var.put("type=" + t.asString() +",waterlogged=" + b, JState.model(getModelPath(pb, "double")));
						break;
				}
			}
		}
		pack.addBlockState(JState.state(var),Registries.BLOCK.getId(pb.getBlock()));
	}
}
