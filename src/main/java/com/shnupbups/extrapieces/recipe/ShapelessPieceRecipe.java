package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ShapelessPieceRecipe extends PieceRecipe {
	private PieceIngredient[] inputs;

	public ShapelessPieceRecipe(PieceType output, int count, PieceIngredient... inputs) {
		super(output, count);
		this.inputs = inputs;
	}

	public PieceIngredient[] getInputs() {
		return inputs;
	}

	public void add(RuntimeResourcePack data, Identifier id, PieceSet set) {
		JIngredients ingredients = JIngredients.ingredients();
		for (PieceIngredient pi : getInputs()) {
			if(pi.isTag()) ingredients.add(JIngredient.ingredient().tag(pi.getId(set).toString()));
			else ingredients.add(JIngredient.ingredient().item(pi.getId(set).toString()));
		}
		data.addRecipe(id, JRecipe.shapeless(ingredients,
				JResult.stackedResult(Registries.BLOCK.getId(getOutput(set)).toString(), getCount()))
				.group(Registries.BLOCK.getId(getOutput(set))
						.toString()));
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		for (PieceIngredient pi : inputs) {
			if (!pi.hasIngredientInSet(set)) return false;
		}
		return true;
	}
}
