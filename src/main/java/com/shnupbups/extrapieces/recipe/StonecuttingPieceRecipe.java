package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class StonecuttingPieceRecipe extends PieceRecipe {
	private PieceIngredient input;

	public StonecuttingPieceRecipe(PieceType output, int count, PieceIngredient input) {
		super(output, count);
		this.input = input;
	}

	public StonecuttingPieceRecipe(PieceType output, int count, PieceType input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public StonecuttingPieceRecipe(PieceType output, int count, ItemConvertible input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public StonecuttingPieceRecipe(PieceType output, int count, TagKey input) {
		this(output, count, new PieceIngredient(input));
	}

	public PieceIngredient getInput() {
		return input;
	}

	public void add(RuntimeResourcePack data, Identifier id, PieceSet set) {
		JIngredient ingredient;
		if(input.isTag()) ingredient = JIngredient.ingredient().tag(input.getId(set).toString());
		else ingredient = JIngredient.ingredient().item(input.getId(set).toString());
		data.addRecipe(id,
				JRecipe.stonecutting(
						ingredient,
						JResult.stackedResult(Registries.BLOCK.getId(getOutput(set)).toString(),getCount())
				).group(Registries.BLOCK.getId(getOutput(set)).toString()));
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		return input.hasIngredientInSet(set);
	}
}
