package io.github.apace100.apoli.networking;

import io.github.apace100.apoli.Apoli;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier HANDSHAKE = Apoli.identifier("handshake");

    public static final Identifier USE_ACTIVE_POWERS = Apoli.identifier("use_active_powers");
    public static final Identifier POWER_LIST = Apoli.identifier("power_list");

    public static final Identifier PLAYER_LANDED = Apoli.identifier("player_landed");
}
