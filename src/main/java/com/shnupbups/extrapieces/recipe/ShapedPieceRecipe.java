package com.shnupbups.extrapieces.recipe;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ShapedPieceRecipe extends PieceRecipe {
	private ListMultimap<Character, PieceIngredient> key = MultimapBuilder.hashKeys().arrayListValues().build();
	private String[] pattern;

	public ShapedPieceRecipe(PieceType output, int count, String... pattern) {
		super(output, count);
		this.pattern = pattern;
	}

	public ShapedPieceRecipe addToKey(char c, PieceType type) {
		return addToKey(c, new PieceIngredient(type));
	}

	public ShapedPieceRecipe addToKey(char c, ItemConvertible item) {
		return addToKey(c, new PieceIngredient(item));
	}
	
	public ShapedPieceRecipe addToKey(char c, TagKey tag) {
		return addToKey(c, new PieceIngredient(tag));
	}

	public ShapedPieceRecipe addToKey(char c, PieceIngredient ingredient) {
		key.put(c, ingredient);
		return this;
	}

	public Multimap<Character, PieceIngredient> getKey() {
		return key;
	}

	public String[] getPattern() {
		return pattern;
	}

	public List<PieceIngredient> getFromKey(char c) {
		return key.get(c);
	}

	public void add(RuntimeResourcePack data, Identifier id, PieceSet set) {
		JKeys keys = JKeys.keys();
		for (Map.Entry<Character, Collection<PieceIngredient>> ingredients : this.getKey().asMap().entrySet()) {
			for (PieceIngredient pi : ingredients.getValue()) {
				if (pi.isTag())
					keys.key(ingredients.getKey().toString(), JIngredient.ingredient().tag(pi.getId(set).toString()));
				else
					keys.key(ingredients.getKey().toString(), JIngredient.ingredient().item(pi.getId(set).toString()));
			}
		}
		data.addRecipe(id, JRecipe.shaped(
				JPattern.pattern(this.getPattern()),
				keys,
				JResult.stackedResult(Registries.BLOCK.getId(this.getOutput(set)).toString(), this.getCount())
		).group(Registries.BLOCK.getId(getOutput(set)).toString()));
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		for (PieceIngredient pi : key.values()) {
			if (!pi.hasIngredientInSet(set)) return false;
		}
		return true;
	}
}
