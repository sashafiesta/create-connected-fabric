package com.hlysine.create_connected.content.copycat.stairs;

import com.hlysine.create_connected.content.copycat.ISimpleCopycatModel;
import com.simibubi.create.content.decoration.copycat.CopycatModel;
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
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;

import java.util.function.Supplier;

import static com.hlysine.create_connected.content.copycat.ISimpleCopycatModel.MutableCullFace.*;

public class CopycatStairsModel extends CopycatModel implements ISimpleCopycatModel {

    public CopycatStairsModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected void emitBlockQuadsInner(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context, BlockState material, CullFaceRemovalData cullFaceRemovalData, OcclusionData occlusionData) {
        BakedModel model = getModelOf(material);

        int facing = (int) state.getValue(StairBlock.FACING).toYRot();
        boolean top = state.getValue(StairBlock.HALF) == Half.TOP;
        StairsShape shape = state.getValue(StairBlock.SHAPE);

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

            switch (shape) {
                case STRAIGHT -> {
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 0),
                            aabb(16, 4, 8),
                            cull(UP | SOUTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 4, 0),
                            aabb(16, 4, 8).move(0, 12, 0),
                            cull(DOWN | SOUTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 8),
                            aabb(16, 8, 8).move(0, 0, 8),
                            cull(UP | NORTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 8),
                            aabb(16, 8, 4).move(0, 8, 0),
                            cull(DOWN | SOUTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 12),
                            aabb(16, 8, 4).move(0, 8, 12),
                            cull(DOWN | NORTH)
                    );
                }
                case INNER_LEFT -> {
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 0),
                            aabb(8, 4, 8),
                            cull(UP | SOUTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 4, 0),
                            aabb(8, 4, 8).move(0, 12, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 8),
                            aabb(16, 8, 8).move(0, 0, 8),
                            cull(UP | NORTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 8),
                            aabb(8, 8, 8).move(8, 8, 8),
                            cull(DOWN | NORTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 12),
                            aabb(8, 8, 4).move(0, 8, 12),
                            cull(DOWN | NORTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 8),
                            aabb(8, 8, 4).move(0, 8, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(12, 8, 0),
                            aabb(4, 8, 8).move(12, 8, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 0),
                            aabb(4, 8, 8).move(0, 8, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 0, 0),
                            aabb(8, 8, 8).move(8, 0, 0),
                            cull(UP | SOUTH | WEST)
                    );
                }
                case INNER_RIGHT -> {
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 0, 0),
                            aabb(8, 4, 8).move(8, 0, 0),
                            cull(UP | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 4, 0),
                            aabb(8, 4, 8).move(8, 12, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 8),
                            aabb(16, 8, 8).move(0, 0, 8),
                            cull(UP | NORTH)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 8),
                            aabb(8, 8, 8).move(0, 8, 8),
                            cull(DOWN | NORTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 12),
                            aabb(8, 8, 4).move(8, 8, 12),
                            cull(DOWN | NORTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 8),
                            aabb(8, 8, 4).move(8, 8, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(4, 8, 0),
                            aabb(4, 8, 8).move(12, 8, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 0),
                            aabb(4, 8, 8).move(0, 8, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 0),
                            aabb(8, 8, 8).move(0, 0, 0),
                            cull(UP | SOUTH | EAST)
                    );
                }
                case OUTER_LEFT -> {
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 0),
                            aabb(8, 4, 16).move(0, 0, 0),
                            cull(UP | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 4, 0),
                            aabb(8, 4, 16).move(0, 12, 0),
                            cull(DOWN | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 0, 0),
                            aabb(8, 4, 8).move(8, 0, 0),
                            cull(UP | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 4, 0),
                            aabb(8, 4, 8).move(8, 12, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 0, 8),
                            aabb(8, 8, 8).move(8, 0, 8),
                            cull(UP | NORTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(12, 8, 12),
                            aabb(4, 8, 4).move(12, 8, 12),
                            cull(DOWN | NORTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 12),
                            aabb(4, 8, 4).move(0, 8, 12),
                            cull(DOWN | NORTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(12, 8, 8),
                            aabb(4, 8, 4).move(12, 8, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 8, 8),
                            aabb(4, 8, 4).move(0, 8, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                }
                case OUTER_RIGHT -> {
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 0, 0),
                            aabb(8, 4, 16).move(8, 0, 0),
                            cull(UP | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(8, 4, 0),
                            aabb(8, 4, 16).move(8, 12, 0),
                            cull(DOWN | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 0),
                            aabb(8, 4, 8).move(0, 0, 0),
                            cull(UP | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 4, 0),
                            aabb(8, 4, 8).move(0, 12, 0),
                            cull(DOWN | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 0, 8),
                            aabb(8, 8, 8).move(0, 0, 8),
                            cull(UP | NORTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(4, 8, 12),
                            aabb(4, 8, 4).move(12, 8, 12),
                            cull(DOWN | NORTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 12),
                            aabb(4, 8, 4).move(0, 8, 12),
                            cull(DOWN | NORTH | EAST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(4, 8, 8),
                            aabb(4, 8, 4).move(12, 8, 0),
                            cull(DOWN | SOUTH | WEST)
                    );
                    assemblePiece(quad, emitter, spriteFinder, facing, top,
                            vec3(0, 8, 8),
                            aabb(4, 8, 4).move(0, 8, 0),
                            cull(DOWN | SOUTH | EAST)
                    );
                }
            }

            return false;
        });
        model.emitBlockQuads(blockView, material, pos, randomSupplier, context);
        context.popTransform();
        meshBuilder.build().outputTo(context.getEmitter());
    }

}
