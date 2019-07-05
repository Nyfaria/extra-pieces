package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.Random;

public class PostPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final EnumProperty<Direction.Axis> AXIS;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape Y_SHAPE;
	protected static final VoxelShape X_SHAPE;
	protected static final VoxelShape Z_SHAPE;
	protected static final VoxelShape Y_COLLISION;

	static {
		AXIS = Properties.AXIS;
		WATERLOGGED = Properties.WATERLOGGED;
		Y_SHAPE = Block.createCuboidShape(6f, 0f, 6f, 10f, 16f, 10f);
		Y_COLLISION = Block.createCuboidShape(6f, 0f, 6f, 10f, 24f, 10f);
		X_SHAPE = Block.createCuboidShape(0f, 6f, 6f, 16f, 10f, 10f);
		Z_SHAPE = Block.createCuboidShape(6f, 6f, 0f, 10f, 10f, 16f);
	}

	private final PieceSet set;

	public PostPieceBlock(PieceSet set) {
		super(Block.Settings.copy(set.getBase()));
		this.set = set;
		this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(WATERLOGGED, false));
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceType.POST;
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext verticalEntityPosition_1) {
		Direction.Axis axis = blockState_1.get(AXIS);
		switch (axis) {
			case X:
				return X_SHAPE;
			case Z:
				return Z_SHAPE;
			default:
				return Y_SHAPE;
		}
	}

	public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext verticalEntityPosition_1) {
		Direction.Axis axis = blockState_1.get(AXIS);
		if (axis == Direction.Axis.Y) return Y_COLLISION;
		else return super.getCollisionShape(blockState_1, blockView_1, blockPos_1, verticalEntityPosition_1);
	}

	public BlockState rotate(BlockState blockState_1, BlockRotation rotation_1) {
		switch (rotation_1) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (blockState_1.get(AXIS)) {
					case X:
						return blockState_1.with(AXIS, Direction.Axis.Z);
					case Z:
						return blockState_1.with(AXIS, Direction.Axis.X);
					default:
						return blockState_1;
				}
			default:
				return blockState_1;
		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
		FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
		return this.getDefaultState().with(AXIS, itemPlacementContext_1.getSide().getAxis()).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(WATERLOGGED)) {
			iWorld_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(iWorld_1));
		}
		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(AXIS, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		this.set.getBase().randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
	}

	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		this.set.getBase().getDefaultState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
	}

	public void onBroken(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1) {
		this.set.getBase().onBroken(iWorld_1, blockPos_1, blockState_1);
	}

	public float getBlastResistance() {
		return this.set.getBase().getBlastResistance();
	}

	public BlockRenderLayer getRenderLayer() {
		return this.set.getBase().getRenderLayer();
	}

	public int getTickRate(ViewableWorld viewableWorld_1) {
		return this.set.getBase().getTickRate(viewableWorld_1);
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this ? true : super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
}
