package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.StairsPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class StairsPiece extends PieceType {
	public StairsPiece() {
		super("stairs");
	}

	public StairsPieceBlock getNew(PieceSet set) {
		return new StairsPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "stairs");
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 4, "b  ", "bb ", "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockModels(RuntimeResourcePack pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "inner");
		addBlockModel(pack, pb, "outer");
	}

	public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
		JState state = new JState();
        JVariant var = JState.variant();
        for (Direction d : Direction.values()) {
            if (!(d.equals(Direction.DOWN) || d.equals(Direction.UP))) {
                for (BlockHalf h : BlockHalf.values()) {
                    for (StairShape s : StairShape.values()) {
                        String varname = "facing=" + d.asString() + ",half=" + h.asString() + ",shape=" + s.asString();
						JBlockModel model;
						int y = 0;
                        y = switch (s) {
                            case STRAIGHT -> {
                                model = JState.model(getModelPath(pb)).uvlock();
                                yield switch (d) {
                                    case EAST -> 0;
                                    case WEST -> 180;
                                    case NORTH -> 270;
                                    case SOUTH -> 90;
                                    default -> y;
                                };
                            }
                            case OUTER_RIGHT -> {
                                model = JState.model(getModelPath(pb, "outer")).uvlock();
                                yield switch (h) {
                                    case BOTTOM -> switch (d) {
                                        case EAST -> 0;
                                        case WEST -> 180;
                                        case NORTH -> 270;
                                        case SOUTH -> 90;
                                        default -> y;
                                    };
                                    case TOP -> switch (d) {
                                        case EAST -> 90;
                                        case WEST -> 270;
                                        case NORTH -> 0;
                                        case SOUTH -> 180;
                                        default -> y;
                                    };
                                };
                            }
                            case OUTER_LEFT -> {
                                model = JState.model(getModelPath(pb, "outer")).uvlock();
                                yield switch (h) {
                                    case BOTTOM -> switch (d) {
                                        case EAST -> 270;
                                        case WEST -> 90;
                                        case NORTH -> 180;
                                        case SOUTH -> 0;
                                        default -> y;
                                    };
                                    case TOP -> switch (d) {
                                        case EAST -> 0;
                                        case WEST -> 180;
                                        case NORTH -> 270;
                                        case SOUTH -> 90;
                                        default -> y;
                                    };
                                };
                            }
                            case INNER_RIGHT -> {
                                model = JState.model(getModelPath(pb, "inner"));
                                yield switch (h) {
                                    case BOTTOM -> switch (d) {
                                        case EAST -> 0;
                                        case WEST -> 180;
                                        case SOUTH -> 90;
                                        case NORTH -> 270;
                                        default -> y;
                                    };
                                    case TOP -> switch (d) {
                                        case EAST -> 90;
                                        case WEST -> 270;
                                        case NORTH -> 0;
                                        case SOUTH -> 180;
                                        default -> y;
                                    };
                                };
                            }
                            case INNER_LEFT -> {
                                model = JState.model(getModelPath(pb, "inner"));
                                yield switch (h) {
                                    case BOTTOM -> switch (d) {
                                        case EAST -> 270;
                                        case WEST -> 90;
                                        case NORTH -> 180;
                                        case SOUTH -> 0;
                                        default -> y;
                                    };
                                    case TOP -> switch (d) {
                                        case EAST -> 0;
                                        case WEST -> 180;
                                        case NORTH -> 270;
                                        case SOUTH -> 90;
                                        default -> y;
                                    };
                                };
                            }
                        };
						if (h.equals(BlockHalf.TOP)) model.x(180);
						var.put(varname, model.y(y));
					}
                }
            }
        }
        state.add(var.put("facing=east,half=bottom,shape=straight", JState.model(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/")).uvlock()).put("facing=west,half=bottom,shape=straight", JState.model(ExtraPieces.prependToPath(Registries.BLOCK.getId(pb.getBlock()), "block/")).y(180).uvlock()));
		pack.addBlockState(state, Registries.BLOCK.getId(pb.getBlock()) );
	}
}
