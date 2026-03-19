package com.hlysine.create_connected;

import com.hlysine.create_connected.content.kineticbattery.KineticBatteryInteractionPoint;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.Registry;

public class CCArmInteractionPointTypes {

    public static ArmInteractionPointType KINETIC_BATTERY =
            register("kinetic_battery", new KineticBatteryInteractionPoint.Type());

    private static <T extends ArmInteractionPointType> T register(String key, T type) {
        return Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, CreateConnected.asResource(key), type);
    }

    public static void register() {
    }
}
