package com.hlysine.create_connected.content.copycat.verticalstep;

import com.simibubi.create.content.decoration.copycat.CopycatModel;
import com.simibubi.create.foundation.model.BakedModelHelper;
import net.createmod.catnip.data.Iterate;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

import static net.minecraft.core.Direction.Axis;

public class CopycatVerticalStepModel extends CopycatModel {
    protected static final AABB CUBE_AABB = new AABB(BlockPos.ZERO);

    public CopycatVerticalStepModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected void emitBlockQuadsInner(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context, BlockState material, CullFaceRemovalData cullFaceRemovalData, OcclusionData occlusionData) {
        Direction facing = state.getOptionalValue(CopycatVerticalStepBlock.FACING).orElse(Direction.NORTH);
        Direction perpendicular = facing.getCounterClockWise();

        int xOffset = (facing.getAxis() == Axis.X ? facing : perpendicular).getAxisDirection().getStep();
        int zOffset = (facing.getAxis() == Axis.Z ? facing : perpendicular).getAxisDirection().getStep();

        BakedModel model = getModelOf(material);

        Vec3 rowNormal = new Vec3(1, 0, 0);
        Vec3 columnNormal = new Vec3(0, 0, 1);
        AABB bb = CUBE_AABB.contract(12 / 16.0, 0, 12 / 16.0);

        SpriteFinder spriteFinder = SpriteFinder.get(Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS));

        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        context.pushTransform(quad -> {
            if (cullFaceRemovalData.shouldRemove(quad.cullFace())) {
                quad.cullFace(null);
            } else if (occlusionData.isOccluded(quad.cullFace())) {
                emitter.copyFrom(quad).emit();
                return false;
            }

            // 4 Pieces
            for (boolean row : Iterate.trueAndFalse) {
                for (boolean column : Iterate.trueAndFalse) {

                    AABB bb1 = bb;
                    if (row)
                        bb1 = bb1.move(rowNormal.scale(12 / 16.0));
                    if (column)
                        bb1 = bb1.move(columnNormal.scale(12 / 16.0));

                    Vec3 offset = Vec3.ZERO;
                    Vec3 rowShift = rowNormal.scale(row
                            ? 8 * Mth.clamp(xOffset, -1, 0) / 16.0
                            : 8 * Mth.clamp(xOffset, 0, 1) / 16.0
                    );
                    Vec3 columnShift = columnNormal.scale(column
                            ? 8 * Mth.clamp(zOffset, -1, 0) / 16.0
                            : 8 * Mth.clamp(zOffset, 0, 1) / 16.0
                    );
                    offset = offset.add(rowShift);
                    offset = offset.add(columnShift);

                    rowShift = rowShift.normalize();
                    columnShift = columnShift.normalize();
                    Vec3i rowShiftNormal = new Vec3i((int) rowShift.x, (int) rowShift.y, (int) rowShift.z);
                    Vec3i columnShiftNormal = new Vec3i((int) columnShift.x, (int) columnShift.y, (int) columnShift.z);

                    Direction direction = quad.lightFace();

                    if (direction.getAxis() == Axis.X && row == (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE))
                        continue;
                    if (direction.getAxis() == Axis.Z && column == (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE))
                        continue;

                    emitter.copyFrom(quad);
                    BakedModelHelper.cropAndMove(emitter, spriteFinder.find(emitter), bb1, offset);
                    emitter.emit();
                }
            }

            return false;
        });
        model.emitBlockQuads(blockView, material, pos, randomSupplier, context);
        context.popTransform();
        meshBuilder.build().outputTo(context.getEmitter());
    }

}
