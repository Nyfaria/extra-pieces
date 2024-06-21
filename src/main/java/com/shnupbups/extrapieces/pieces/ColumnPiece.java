package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.ColumnPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Direction;

public class ColumnPiece extends PieceType {
    public ColumnPiece() {
        super("column");
    }

    public ColumnPieceBlock getNew(PieceSet set) {
        return new ColumnPieceBlock(set);
    }

    public void addBlockstate(RuntimeResourcePack pack, PieceBlock pb) {
        JState state = new JState();
        JVariant var = JState.variant();
        for (Direction.Axis a : Direction.Axis.values()) {
            JBlockModel model = JState.model(getModelPath(pb)).uvlock();
            if (a != Direction.Axis.Y) {
                model.x(90);
                if (a == Direction.Axis.X) {
                    model.y(90);
                }
            }
            var.put("axis=" + a.asString(), model);
        }
        state.add(var);
        pack.addBlockState(state,Registries.BLOCK.getId(pb.getBlock()));
    }
}
