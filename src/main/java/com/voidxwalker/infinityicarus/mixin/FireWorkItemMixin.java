package com.voidxwalker.infinityicarus.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(FireworkItem.class)
public abstract class FireWorkItemMixin {
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    public void decrement(ItemStack instance, int amount) {
        if(!(EnchantmentHelper.getLevel(Enchantments.INFINITY, instance) > 0)){
            instance.decrement(1);
        }
    }
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    public void decrement2(ItemStack instance, int amount) {
        if(!(EnchantmentHelper.getLevel(Enchantments.INFINITY, instance) > 0)){
            instance.decrement(1);
        }
    }
}
