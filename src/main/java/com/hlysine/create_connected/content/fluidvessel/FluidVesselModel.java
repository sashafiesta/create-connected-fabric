package com.hlysine.create_connected.content.fluidvessel;

import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.createmod.catnip.data.Iterate;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.function.Supplier;

import static net.minecraft.core.Direction.Axis;

public class FluidVesselModel extends CTModel {

	public static FluidVesselModel standard(BakedModel originalModel) {
		return new FluidVesselModel(originalModel, AllSpriteShifts.FLUID_TANK, AllSpriteShifts.FLUID_TANK_TOP,
			AllSpriteShifts.FLUID_TANK_INNER);
	}

	public static FluidVesselModel creative(BakedModel originalModel) {
		return new FluidVesselModel(originalModel, AllSpriteShifts.CREATIVE_FLUID_TANK, AllSpriteShifts.CREATIVE_CASING,
			AllSpriteShifts.CREATIVE_CASING);
	}

	private FluidVesselModel(BakedModel originalModel, CTSpriteShiftEntry side, CTSpriteShiftEntry top,
							 CTSpriteShiftEntry inner) {
		super(originalModel, new FluidVesselCTBehaviour(side, top, inner));
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
		CullData cullData = new CullData();
		Axis axis = state.getValue(FluidVesselBlock.AXIS);
		for (Direction d : Iterate.directions) {
			if (d.getAxis() == axis)
				continue;
			cullData.setCulled(d, ConnectivityHandler.isConnected(blockView, pos, pos.relative(d)));
		}

		context.pushTransform(quad -> {
			Direction cullFace = quad.cullFace();
			if (cullFace != null && cullData.isCulled(cullFace)) {
				return false;
			}
			quad.cullFace(null);
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	protected static class CullData {
		boolean[] culledFaces;

		public CullData() {
			culledFaces = new boolean[6];
			Arrays.fill(culledFaces, false);
		}

		void setCulled(Direction face, boolean cull) {
			culledFaces[face.get3DDataValue()] = cull;
		}

		boolean isCulled(Direction face) {
			return culledFaces[face.get3DDataValue()];
		}
	}

}
