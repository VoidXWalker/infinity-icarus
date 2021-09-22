package com.voidxwalker.infinityicarus.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FireworkItem.class)
public class FireworkItemMixin {
    /**
     * @author Void_X_Walker
     * @reason makes the rockets infinite
     */
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isFallFlying()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (!world.isClient) {
                world.spawnEntity(new FireworkRocketEntity(world, itemStack, user));
                if (!user.abilities.creativeMode&&!(EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0)) {
                    itemStack.decrement(1);
                }
            }
            return TypedActionResult.method_29237(user.getStackInHand(hand), world.isClient());
        } else {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
    /**
     * @author Void_X_Walker
     * @reason makes the rockets infinite
     */
    @Overwrite
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            ItemStack itemStack = context.getStack();
            Vec3d vec3d = context.getHitPos();
            Direction direction = context.getSide();
            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, context.getPlayer(), vec3d.x + (double)direction.getOffsetX() * 0.15D, vec3d.y + (double)direction.getOffsetY() * 0.15D, vec3d.z + (double)direction.getOffsetZ() * 0.15D, itemStack);
            world.spawnEntity(fireworkRocketEntity);
            if (!(EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0)) {
                itemStack.decrement(1);
            }
        }
        return ActionResult.success(world.isClient);
    }
}
