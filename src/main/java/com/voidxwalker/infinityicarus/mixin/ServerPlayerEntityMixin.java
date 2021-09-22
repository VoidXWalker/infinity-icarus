package com.voidxwalker.infinityicarus.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.voidxwalker.infinityicarus.Main;
import net.minecraft.command.arguments.ItemStackArgumentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    private ServerStatHandler statHandler;
    public ServerPlayerEntityMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }
    private ItemStack itemStackFromString(String string, int count) throws CommandSyntaxException {
        return new ItemStackArgumentType().parse(new StringReader(string)).createStack(count, false);
    }
    @Inject(at = @At("TAIL"), method = "<init>")
    private void initInfiniIcarus(MinecraftServer server, ServerWorld world, GameProfile profile, ServerPlayerInteractionManager interactionManager, CallbackInfo ci) throws CommandSyntaxException {
        if (statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_ONE_MINUTE)) == 0) {

            Main.LOGGER.info( "New player detected, activating InfinityIcarus.");
            ItemStack elytra = itemStackFromString("minecraft:elytra{Unbreakable:1b}", 1);
            ItemStack infinityRockets = itemStackFromString("minecraft:firework_rocket{Fireworks:{Flight:3b}}", 64);
            infinityRockets.addEnchantment(Enchantments.INFINITY, 1);
            infinityRockets.getOrCreateTag().putInt("HideFlags", 1);
            LiteralText text = new LiteralText("InfinityRockets™");
            text.setStyle(text.getStyle().withItalic(false));
            infinityRockets.setCustomName(text);
            inventory.armor.set(2, elytra);
            inventory.main.set(0, infinityRockets);
        }
    }
}
