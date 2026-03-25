package com.hlysine.create_connected;

import com.hlysine.create_connected.content.brake.BrakeBlockEntity;
import com.hlysine.create_connected.content.brassgearbox.BrassGearboxBlockEntity;
import com.hlysine.create_connected.content.brassgearbox.BrassGearboxRenderer;
import com.hlysine.create_connected.content.centrifugalclutch.CentrifugalClutchBlockEntity;
import com.hlysine.create_connected.content.crankwheel.CrankWheelBlockEntity;
import com.hlysine.create_connected.content.fancatalyst.FanEndingCatalystDragonHeadBlockEntity;
import com.hlysine.create_connected.content.fancatalyst.FanEndingCatalystDragonHeadRenderer;
import com.hlysine.create_connected.content.fluidvessel.CreativeFluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselRenderer;
import com.hlysine.create_connected.content.freewheelclutch.FreewheelClutchBlockEntity;
import com.hlysine.create_connected.content.inventoryaccessport.InventoryAccessPortBlockEntity;
import com.hlysine.create_connected.content.inventorybridge.InventoryBridgeBlockEntity;
import com.hlysine.create_connected.content.invertedclutch.InvertedClutchBlockEntity;
import com.hlysine.create_connected.content.invertedgearshift.InvertedGearshiftBlockEntity;
import com.hlysine.create_connected.content.itemsilo.ItemSiloBlockEntity;
import com.hlysine.create_connected.content.kineticbattery.KineticBatteryBlockEntity;
import com.hlysine.create_connected.content.kineticbattery.KineticBatteryRenderer;
import com.hlysine.create_connected.content.kineticbridge.*;
import com.hlysine.create_connected.content.linkedtransmitter.LinkedAnalogLeverBlockEntity;
import com.hlysine.create_connected.content.linkedtransmitter.LinkedAnalogLeverRenderer;
import com.hlysine.create_connected.content.linkedtransmitter.LinkedTransmitterBlockEntity;
import com.hlysine.create_connected.content.overstressclutch.OverstressClutchBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxBlockEntity;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxRenderer;
import com.hlysine.create_connected.content.sequencedpulsegenerator.SequencedPulseGeneratorBlockEntity;
import com.hlysine.create_connected.content.shearpin.ShearPinBlockEntity;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxBlockEntity;
import com.hlysine.create_connected.content.sixwaygearbox.SixWayGearboxRenderer;
import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.SimpleKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogRenderer;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class CCBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = CreateConnected.getRegistrate();
    private static final boolean IS_CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

    public static final BlockEntityEntry<SimpleKineticBlockEntity> ENCASED_CHAIN_COGWHEEL = buildEncasedChainCogwheel();
    public static final BlockEntityEntry<CrankWheelBlockEntity> CRANK_WHEEL = buildCrankWheel();
    public static final BlockEntityEntry<ParallelGearboxBlockEntity> PARALLEL_GEARBOX = buildParallelGearbox();
    public static final BlockEntityEntry<SixWayGearboxBlockEntity> SIX_WAY_GEARBOX = buildSixWayGearbox();
    public static final BlockEntityEntry<OverstressClutchBlockEntity> OVERSTRESS_CLUTCH = buildOverstressClutch();
    public static final BlockEntityEntry<ShearPinBlockEntity> SHEAR_PIN = buildShearPin();
    public static final BlockEntityEntry<InvertedClutchBlockEntity> INVERTED_CLUTCH = buildInvertedClutch();
    public static final BlockEntityEntry<InvertedGearshiftBlockEntity> INVERTED_GEARSHIFT = buildInvertedGearshift();
    public static final BlockEntityEntry<CentrifugalClutchBlockEntity> CENTRIFUGAL_CLUTCH = buildCentrifugalClutch();
    public static final BlockEntityEntry<FreewheelClutchBlockEntity> FREEWHEEL_CLUTCH = buildFreewheelClutch();
    public static final BlockEntityEntry<KineticBridgeBlockEntity> KINETIC_BRIDGE = buildKineticBridge();
    public static final BlockEntityEntry<KineticBridgeDestinationBlockEntity> KINETIC_BRIDGE_DESTINATION = buildKineticBridgeDestination();
    public static final BlockEntityEntry<BrassGearboxBlockEntity> BRASS_GEARBOX = buildBrassGearbox();
    public static final BlockEntityEntry<BrakeBlockEntity> BRAKE = buildBrake();
    public static final BlockEntityEntry<KineticBatteryBlockEntity> KINETIC_BATTERY = buildKineticBattery();

    public static final BlockEntityEntry<ItemSiloBlockEntity> ITEM_SILO = REGISTRATE
            .blockEntity("item_silo", ItemSiloBlockEntity::new)
            .validBlocks(CCBlocks.ITEM_SILO)
            .register();

    public static final BlockEntityEntry<FluidVesselBlockEntity> FLUID_VESSEL = REGISTRATE
            .blockEntity("fluid_vessel", FluidVesselBlockEntity::new)
            .validBlocks(CCBlocks.FLUID_VESSEL)
            .renderer(() -> FluidVesselRenderer::new)
            .register();

    public static final BlockEntityEntry<CreativeFluidVesselBlockEntity> CREATIVE_FLUID_VESSEL = REGISTRATE
            .blockEntity("creative_fluid_vessel", CreativeFluidVesselBlockEntity::new)
            .validBlocks(CCBlocks.CREATIVE_FLUID_VESSEL)
            .renderer(() -> FluidVesselRenderer::new)
            .register();

    public static final BlockEntityEntry<InventoryAccessPortBlockEntity> INVENTORY_ACCESS_PORT = REGISTRATE
            .blockEntity("inventory_access_port", InventoryAccessPortBlockEntity::new)
            .validBlocks(CCBlocks.INVENTORY_ACCESS_PORT)
            .register();

    public static final BlockEntityEntry<InventoryBridgeBlockEntity> INVENTORY_BRIDGE = REGISTRATE
            .blockEntity("inventory_bridge", InventoryBridgeBlockEntity::new)
            .validBlocks(CCBlocks.INVENTORY_BRIDGE)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<SequencedPulseGeneratorBlockEntity> SEQUENCED_PULSE_GENERATOR = REGISTRATE
            .blockEntity("sequenced_pulse_generator", SequencedPulseGeneratorBlockEntity::new)
            .validBlocks(CCBlocks.SEQUENCED_PULSE_GENERATOR)
            .register();

    public static final BlockEntityEntry<LinkedTransmitterBlockEntity> LINKED_TRANSMITTER = REGISTRATE
            .blockEntity("linked_transmitter", LinkedTransmitterBlockEntity::new)
            .transform(b -> {
                CCBlocks.LINKED_BUTTONS.values().forEach(b::validBlock);
                return b;
            })
            .validBlocks(CCBlocks.LINKED_LEVER)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<LinkedAnalogLeverBlockEntity> LINKED_ANALOG_LEVER = buildLinkedAnalogLever();

    public static final BlockEntityEntry<CopycatBlockEntity> COPYCAT =
            REGISTRATE.blockEntity("copycat", CopycatBlockEntity::new)
                    .validBlocks(
                            CCBlocks.COPYCAT_BLOCK,
                            CCBlocks.COPYCAT_SLAB,
                            CCBlocks.COPYCAT_BEAM,
                            CCBlocks.COPYCAT_VERTICAL_STEP,
                            CCBlocks.COPYCAT_STAIRS,
                            CCBlocks.COPYCAT_FENCE,
                            CCBlocks.COPYCAT_FENCE_GATE,
                            CCBlocks.COPYCAT_WALL,
                            CCBlocks.COPYCAT_BOARD
                    )
                    .register();

    public static final BlockEntityEntry<FanEndingCatalystDragonHeadBlockEntity> FAN_ENDING_CATALYST_DRAGON_HEAD = REGISTRATE
            .blockEntity("fan_ending_catalyst_dragon_head", FanEndingCatalystDragonHeadBlockEntity::new)
            .validBlocks(CCBlocks.FAN_ENDING_CATALYST_DRAGON_HEAD)
            .renderer(() -> FanEndingCatalystDragonHeadRenderer::new)
            .register();

    // Builder methods that conditionally register visuals (flywheel is client-only on Fabric)
    private static BlockEntityEntry<SimpleKineticBlockEntity> buildEncasedChainCogwheel() {
        var b = REGISTRATE.blockEntity("encased_chain_cogwheel", SimpleKineticBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.encasedChainCogwheel(b);
        return b.validBlocks(CCBlocks.ENCASED_CHAIN_COGWHEEL).renderer(() -> EncasedCogRenderer::small).register();
    }

    private static BlockEntityEntry<CrankWheelBlockEntity> buildCrankWheel() {
        var b = REGISTRATE.blockEntity("crank_wheel", CrankWheelBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.crankWheel(b);
        return b.validBlocks(CCBlocks.CRANK_WHEEL, CCBlocks.LARGE_CRANK_WHEEL).renderer(() -> HandCrankRenderer::new).register();
    }

    private static BlockEntityEntry<ParallelGearboxBlockEntity> buildParallelGearbox() {
        var b = REGISTRATE.blockEntity("parallel_gearbox", ParallelGearboxBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.parallelGearbox(b);
        return b.validBlocks(CCBlocks.PARALLEL_GEARBOX).renderer(() -> ParallelGearboxRenderer::new).register();
    }

    private static BlockEntityEntry<SixWayGearboxBlockEntity> buildSixWayGearbox() {
        var b = REGISTRATE.blockEntity("six_way_gearbox", SixWayGearboxBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.sixWayGearbox(b);
        return b.validBlocks(CCBlocks.SIX_WAY_GEARBOX).renderer(() -> SixWayGearboxRenderer::new).register();
    }

    private static BlockEntityEntry<OverstressClutchBlockEntity> buildOverstressClutch() {
        var b = REGISTRATE.blockEntity("overstress_clutch", OverstressClutchBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.OVERSTRESS_CLUTCH).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<ShearPinBlockEntity> buildShearPin() {
        var b = REGISTRATE.blockEntity("shear_pin", ShearPinBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.shearPin(b);
        return b.validBlocks(CCBlocks.SHEAR_PIN).renderer(() -> BracketedKineticBlockEntityRenderer::new).register();
    }

    private static BlockEntityEntry<InvertedClutchBlockEntity> buildInvertedClutch() {
        var b = REGISTRATE.blockEntity("inverted_clutch", InvertedClutchBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.INVERTED_CLUTCH).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<InvertedGearshiftBlockEntity> buildInvertedGearshift() {
        var b = REGISTRATE.blockEntity("inverted_gearshift", InvertedGearshiftBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.INVERTED_GEARSHIFT).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<CentrifugalClutchBlockEntity> buildCentrifugalClutch() {
        var b = REGISTRATE.blockEntity("centrifugal_clutch", CentrifugalClutchBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.CENTRIFUGAL_CLUTCH).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<FreewheelClutchBlockEntity> buildFreewheelClutch() {
        var b = REGISTRATE.blockEntity("freewheel_clutch", FreewheelClutchBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.FREEWHEEL_CLUTCH).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<KineticBridgeBlockEntity> buildKineticBridge() {
        var b = REGISTRATE.blockEntity("kinetic_bridge", KineticBridgeBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.kineticBridgeSource(b);
        return b.validBlocks(CCBlocks.KINETIC_BRIDGE).renderer(() -> KineticBridgeRenderer::source).register();
    }

    private static BlockEntityEntry<KineticBridgeDestinationBlockEntity> buildKineticBridgeDestination() {
        var b = REGISTRATE.blockEntity("kinetic_bridge_destination", KineticBridgeDestinationBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.kineticBridgeDestination(b);
        return b.validBlocks(CCBlocks.KINETIC_BRIDGE_DESTINATION).renderer(() -> KineticBridgeRenderer::destination).register();
    }

    private static BlockEntityEntry<BrassGearboxBlockEntity> buildBrassGearbox() {
        var b = REGISTRATE.blockEntity("brass_gearbox", BrassGearboxBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.brassGearbox(b);
        return b.validBlocks(CCBlocks.BRASS_GEARBOX).renderer(() -> BrassGearboxRenderer::new).register();
    }

    private static BlockEntityEntry<BrakeBlockEntity> buildBrake() {
        var b = REGISTRATE.blockEntity("brake", BrakeBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.splitShaft(b);
        return b.validBlocks(CCBlocks.BRAKE).renderer(() -> SplitShaftRenderer::new).register();
    }

    private static BlockEntityEntry<KineticBatteryBlockEntity> buildKineticBattery() {
        var b = REGISTRATE.blockEntity("kinetic_battery", KineticBatteryBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.kineticBattery(b);
        return b.validBlocks(CCBlocks.KINETIC_BATTERY).renderer(() -> KineticBatteryRenderer::new).register();
    }

    private static BlockEntityEntry<LinkedAnalogLeverBlockEntity> buildLinkedAnalogLever() {
        var b = REGISTRATE.blockEntity("linked_analog_lever", LinkedAnalogLeverBlockEntity::new);
        if (IS_CLIENT) CCBlockEntityVisuals.analogLever(b);
        return b.validBlocks(CCBlocks.LINKED_ANALOG_LEVER).renderer(() -> LinkedAnalogLeverRenderer::new).register();
    }

    public static void register() {
    }
}
