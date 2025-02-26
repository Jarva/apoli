package io.github.apace100.apoli.power;

import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.function.Predicate;

public class PreventBlockSelectionPower extends Power {

    private final Predicate<CachedBlockPosition> predicate;

    public PreventBlockSelectionPower(PowerType<?> type, LivingEntity entity, Predicate<CachedBlockPosition> predicate) {
        super(type, entity);
        this.predicate = predicate;
    }

    public boolean doesPrevent(WorldView world, BlockPos pos) {
        CachedBlockPosition cbp = new CachedBlockPosition(world, pos, true);
        return predicate == null || predicate.test(cbp);
    }
}
