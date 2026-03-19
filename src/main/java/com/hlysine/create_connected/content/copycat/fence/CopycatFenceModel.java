package com.hlysine.create_connected.content.copycat.fence;

import com.hlysine.create_connected.content.copycat.ISimpleCopycatModel;
import com.simibubi.create.content.decoration.copycat.CopycatModel;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

import static com.hlysine.create_connected.content.copycat.ISimpleCopycatModel.MutableCullFace.*;
import static com.hlysine.create_connected.content.copycat.fence.CopycatFenceBlock.byDirection;

public class CopycatFenceModel extends CopycatModel implements ISimpleCopycatModel {

    public CopycatFenceModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected void emitBlockQuadsInner(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context, BlockState material, CullFaceRemovalData cullFaceRemovalData, OcclusionData occlusionData) {
        BakedModel model = getModelOf(material);

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

            for (Direction direction : Iterate.horizontalDirections) {
                assemblePiece(quad, emitter, spriteFinder, (int) direction.toYRot(), false,
                        vec3(6, 0, 6),
                        aabb(2, 16, 2),
                        cull(SOUTH | EAST)
                );
            }

            for (Direction direction : Iterate.horizontalDirections) {
                if (!state.getValue(byDirection(direction))) continue;

                int rot = (int) direction.toYRot();
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(7, 6, 10),
                        aabb(1, 1, 6),
                        cull(UP | NORTH | EAST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(8, 6, 10),
                        aabb(1, 1, 6).move(15, 0, 0),
                        cull(UP | NORTH | WEST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(7, 7, 10),
                        aabb(1, 2, 6).move(0, 14, 0),
                        cull(DOWN | NORTH | EAST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(8, 7, 10),
                        aabb(1, 2, 6).move(15, 14, 0),
                        cull(DOWN | NORTH | WEST)
                );

                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(7, 12, 10),
                        aabb(1, 1, 6),
                        cull(UP | NORTH | EAST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(8, 12, 10),
                        aabb(1, 1, 6).move(15, 0, 0),
                        cull(UP | NORTH | WEST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(7, 13, 10),
                        aabb(1, 2, 6).move(0, 14, 0),
                        cull(DOWN | NORTH | EAST)
                );
                assemblePiece(quad, emitter, spriteFinder, rot, false,
                        vec3(8, 13, 10),
                        aabb(1, 2, 6).move(15, 14, 0),
                        cull(DOWN | NORTH | WEST)
                );
            }

            return false;
        });
        model.emitBlockQuads(blockView, material, pos, randomSupplier, context);
        context.popTransform();
        meshBuilder.build().outputTo(context.getEmitter());
    }

}
