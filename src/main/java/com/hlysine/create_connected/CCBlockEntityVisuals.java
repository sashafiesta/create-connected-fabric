package com.hlysine.create_connected;

import com.hlysine.create_connected.content.brassgearbox.BrassGearboxBlockEntity;
import com.hlysine.create_connected.content.brassgearbox.BrassGearboxVisual;
import com.hlysine.create_connected.content.crankwheel.CrankWheelBlockEntity;
import com.hlysine.create_connected.content.crankwheel.CrankWheelVisual;
import com.hlysine.create_connected.content.kineticbattery.KineticBatteryBlockEntity;
import com.hlysine.create_connected.content.kineticbattery.KineticBatteryVisual;
import com.hlysine.create_connected.content.kineticbridge.KineticBridgeBlockEntity;
import com.hlysine.create_connected.content.kineticbridge.KineticBridgeDestinationBlockEntity;
import com.hlysine.create_connected.content.kineticbridge.KineticBridgeVisual;
import com.hlysine.create_connected.content.linkedtransmitter.LinkedAnalogLeverBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxVisual;
import com.hlysine.create_connected.content.shearpin.ShearPinBlockEntity;
import com.hlysine.create_connected.content.shearpin.ShearPinVisual;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxBlockEntity;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxVisual;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverVisual;
import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.infrastructure.fabric.SimpleBlockEntityVisualFactory;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Client-only visual registrations for block entities.
 * This class is only loaded on the client side (guarded by EnvType.CLIENT check in CCBlockEntityTypes).
 * Keeps flywheel Visual classes off the server classpath where flywheel is not available.
 */
public class CCBlockEntityVisuals {

    public static void encasedChainCogwheel(CreateBlockEntityBuilder<SimpleKineticBlockEntity, CreateRegistrate> b) {
        b.visual(() -> EncasedCogVisual::small, false);
    }

    public static void crankWheel(CreateBlockEntityBuilder<CrankWheelBlockEntity, CreateRegistrate> b) {
        b.visual(() -> CrankWheelVisual::new);
    }

    public static void parallelGearbox(CreateBlockEntityBuilder<ParallelGearboxBlockEntity, CreateRegistrate> b) {
        b.visual(() -> ParallelGearboxVisual::new, false);
    }

    public static void sixWayGearbox(CreateBlockEntityBuilder<SixWayGearboxBlockEntity, CreateRegistrate> b) {
        b.visual(() -> SixWayGearboxVisual::new, false);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BlockEntity> void splitShaft(CreateBlockEntityBuilder<T, CreateRegistrate> b) {
        SimpleBlockEntityVisualFactory factory = (ctx, be, pt) -> new SplitShaftVisual(ctx, (SplitShaftBlockEntity) be, pt);
        ((CreateBlockEntityBuilder) b).visual((NonNullSupplier) () -> factory, false);
    }

    public static void shearPin(CreateBlockEntityBuilder<ShearPinBlockEntity, CreateRegistrate> b) {
        b.visual(() -> ShearPinVisual::new, false);
    }

    public static void kineticBridgeSource(CreateBlockEntityBuilder<KineticBridgeBlockEntity, CreateRegistrate> b) {
        b.visual(() -> (ctx, blockEntity, partialTick) -> new KineticBridgeVisual(ctx, blockEntity, partialTick, false), false);
    }

    public static void kineticBridgeDestination(CreateBlockEntityBuilder<KineticBridgeDestinationBlockEntity, CreateRegistrate> b) {
        b.visual(() -> (ctx, blockEntity, partialTick) -> new KineticBridgeVisual(ctx, blockEntity, partialTick, true), false);
    }

    public static void brassGearbox(CreateBlockEntityBuilder<BrassGearboxBlockEntity, CreateRegistrate> b) {
        b.visual(() -> BrassGearboxVisual::new, false);
    }

    public static void kineticBattery(CreateBlockEntityBuilder<KineticBatteryBlockEntity, CreateRegistrate> b) {
        b.visual(() -> KineticBatteryVisual::new, false);
    }

    public static void analogLever(CreateBlockEntityBuilder<LinkedAnalogLeverBlockEntity, CreateRegistrate> b) {
        b.visual(() -> AnalogLeverVisual::new);
    }
}
