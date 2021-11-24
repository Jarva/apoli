package io.github.apace100.apoli;

import io.github.apace100.apoli.power.factory.condition.EntityConditionsServer;
import io.github.apace100.apoli.power.factory.condition.ItemConditionsServer;
import io.github.apace100.apoli.util.ApoliConfigServer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class ApoliServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {

		EntityConditionsServer.register();
		ItemConditionsServer.register();

		AutoConfig.register(ApoliConfigServer.class, JanksonConfigSerializer::new);
		Apoli.config = AutoConfig.getConfigHolder(ApoliConfigServer.class).getConfig();

	}
}
