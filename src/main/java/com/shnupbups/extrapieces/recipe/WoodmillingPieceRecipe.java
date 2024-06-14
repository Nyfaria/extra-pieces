package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class WoodmillingPieceRecipe extends PieceRecipe {
	private PieceIngredient input;

	public WoodmillingPieceRecipe(PieceType output, int count, PieceIngredient input) {
		super(output, count);
		this.input = input;
	}

	public WoodmillingPieceRecipe(PieceType output, int count, PieceType input) {
		this(output, count, new PieceIngredient(input));
	}

	public WoodmillingPieceRecipe(PieceType output, int count, ItemConvertible input) {
		this(output, count, new PieceIngredient(input));
	}

	public WoodmillingPieceRecipe(PieceType output, int count, TagKey input) {
		this(output, count, new PieceIngredient(input));
	}

	public PieceIngredient getInput() {
		return input;
	}
//todo finish this
	public void add(RuntimeResourcePack data, Identifier id, PieceSet set) {
//		data.addRecipe(id,
//				JRecipe.
//				);
//        StonecuttingRecipeBuilder builder = new StonecuttingRecipeBuilder();
//        builder.type(new Identifier("woodmill", "woodmilling"));
//        builder.result(Registries.BLOCK.getId(getOutput(set)));
//        builder.group(Registries.BLOCK.getId(getOutput(set)));
//        builder.count(getCount());
//        PieceIngredient pi = getInput();
//        if(pi.isTag()) builder.ingredientTag(pi.getId(set));
//        else builder.ingredientItem(pi.getId(set));
//        data.addStonecuttingRecipe(id, builder);
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		return input.hasIngredientInSet(set);
	}
}
