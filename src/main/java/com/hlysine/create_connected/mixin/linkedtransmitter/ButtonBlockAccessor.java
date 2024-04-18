package com.hlysine.create_connected.mixin.linkedtransmitter;

import net.minecraft.world.level.block.ButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.world.level.block.state.properties.BlockSetType;

@Mixin(value = ButtonBlock.class, remap = true)
public interface ButtonBlockAccessor {
    
	@Accessor
	BlockSetType getType();
	@Accessor
    int getTicksToStayPressed();
	@Accessor
    boolean getArrowsCanPress();
}
