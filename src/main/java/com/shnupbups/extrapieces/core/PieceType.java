package com.shnupbups.extrapieces.core;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlockItem;
import com.shnupbups.extrapieces.recipe.PieceRecipe;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.recipe.ShapelessPieceRecipe;
import com.shnupbups.extrapieces.recipe.StonecuttingPieceRecipe;
import com.shnupbups.extrapieces.recipe.WoodmillingPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public abstract class PieceType {

    private final Identifier id;

    public PieceType(String id) {
        this(ExtraPieces.getID(id));
    }

    public PieceType(Identifier id) {
        this.id = id;
    }

    /**
     * Gets the name of this {@link PieceType} with {@code baseName_} appended to the front, in all lowercase.<br>
     * Used for registry.
     *
     * @return The name of this {@link PieceType}, in all lowercase.
     */
    public String getBlockId(String baseName) {
        return baseName.toLowerCase() + "_" + getId().getPath();
    }

    /**
     * Gets the id of this {@link PieceType}<br>
     * Used for registry.
     *
     * @return The id of this {@link PieceType}
     */
    public Identifier getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return "piece." + id.getNamespace() + "." + id.getPath();
    }

    /**
     * Gets the id of the block and item tag of this {@link PieceType}<br>
     * Used for registry.<br>
     * Defaults to {@link #getId()} wth an 's' appended
     *
     * @return The id of this {@link PieceType}'s tag
     */
    public Identifier getTagId() {
        return new Identifier(this.id.toString() + "s");
    }

    public abstract PieceBlock getNew(PieceSet set);

    public PacketByteBuf writePieceType(PacketByteBuf buf) {
        buf.writeInt(getId().toString().length());
        buf.writeString(getId().toString());
        return buf;
    }

    public String toString() {
        return getId().toString();
    }

    @Deprecated
    /**
     * Use {@link #getShapedRecipes()} or {@link #getCraftingRecipes()}
     */
    public ArrayList<ShapedPieceRecipe> getRecipes() {
        return getShapedRecipes();
    }

    public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
        return new ArrayList<>();
    }

    public ArrayList<ShapelessPieceRecipe> getShapelessRecipes() {
        return new ArrayList<>();
    }

    public ArrayList<PieceRecipe> getCraftingRecipes() {
        ArrayList<PieceRecipe> recipes = new ArrayList<>();
        recipes.addAll(getRecipes());
        recipes.addAll(getShapelessRecipes());
        return recipes;
    }

    public StonecuttingPieceRecipe getStonecuttingRecipe() {
        return new StonecuttingPieceRecipe(this, getStonecuttingCount(), PieceTypes.BASE);
    }

    public WoodmillingPieceRecipe getWoodmillingRecipe() {
        return new WoodmillingPieceRecipe(this, getStonecuttingCount(), PieceTypes.BASE);
    }

    public int getStonecuttingCount() {
        return 1;
    }

    public void addLootTable(RuntimeResourcePack data, PieceBlock pb) {
        JLootTable lootTable = new JLootTable("block");
        JPool pool = new JPool();
        pool.rolls(1);
        JEntry entry = new JEntry();
        entry.type("item");
        entry.name(Registries.BLOCK.getId(pb.getBlock()).toString());
        pool.entry(entry);
        lootTable.pool(pool);
        pool.condition(new JCondition("survives_explosion"));
        data.addLootTable(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "blocks/"),lootTable);
    }

    public void addModels(RuntimeResourcePack pack, PieceBlock pb) {
        addBlockModels(pack, pb);
        addItemModel(pack, pb);
    }

    public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
        pack.addModel(new JModel().parent(ExtraPieces.prependToPath(this.getId(), "block/dummy_").toString()).textures(
                new JTextures().particle(pb.getSet().getMainTexture().toString())
                        .var("main", pb.getSet().getMainTexture().toString())
                        .var("top", pb.getSet().getTopTexture().toString())
                        .var("bottom", pb.getSet().getBottomTexture().toString())
        ), ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/"));
    }

    public void addItemModel(RuntimeResourcePack pack, PieceBlock pb) {
        pack.addModel(new JModel().parent(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/").toString()),Registries.BLOCK.getId(pb.getBlock()));
    }

    public void addBlockModel(RuntimeResourcePack pack, PieceBlock pb, String append) {
        pack.addModel(new JModel().parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(this.getId(), "_" + append), "block/dummy_").toString()).textures(
                new JTextures().particle(pb.getSet().getMainTexture().toString())
                        .var("main", pb.getSet().getMainTexture().toString())
                        .var("top", pb.getSet().getTopTexture().toString())
                        .var("bottom", pb.getSet().getBottomTexture().toString())
        ), ExtraPieces.appendToPath(Registries.BLOCK.getId(pb.getBlock()), "_" + append));
    }

    public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
        JState state = new JState();
        JVariant var = JState.variant(JState.model(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/")));
        state.add(var);
        pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
    }

    public PieceBlockItem getBlockItem(PieceBlock pb) {
        return new PieceBlockItem(pb, new Item.Settings());
    }

    public Identifier getModelPath(PieceBlock pb) {
        return ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/");
    }

    public Identifier getModelPath(PieceBlock pb, String append) {
        return ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registries.BLOCK.getId(pb.getBlock()), "_" + append), "block/");
    }
}
