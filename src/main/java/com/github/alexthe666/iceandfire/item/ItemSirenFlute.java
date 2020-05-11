package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.entity.EntityPropertiesHandler;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.entity.MiscEntityProperties;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import com.google.common.base.Predicates;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class ItemSirenFlute extends Item {

    public ItemSirenFlute() {
        super(new Item.Properties().group(IceAndFire.TAB_ITEMS).maxDamage(200).maxStackSize(1));
        this.setRegistryName(IceAndFire.MODID, "siren_flute");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        ItemStack itemStackIn = player.getHeldItem(hand);
        player.setActiveHand(hand);
        player.getCooldownTracker().setCooldown(this, 900);
        double dist = 32;
        Vec3d vec3d = player.getEyePosition(1.0F);
        Vec3d vec3d1 = player.getLook(1.0F);
        Vec3d vec3d2 = vec3d.add(vec3d1.x * dist, vec3d1.y * dist, vec3d1.z * dist);
        double d1 = dist;
        Entity pointedEntity = null;
        List<Entity> list = player.world.getEntitiesInAABBexcluding(player, player.getBoundingBox().expand(vec3d1.x * dist, vec3d1.y * dist, vec3d1.z * dist).grow(1.0D, 1.0D, 1.0D), new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                boolean blindness = entity instanceof LivingEntity && ((LivingEntity) entity).isPotionActive(Effects.BLINDNESS) || (entity instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues) entity).canBeTurnedToStone());
                return entity != null && entity.canBeCollidedWith() && !blindness && (entity instanceof PlayerEntity || (entity instanceof LivingEntity && EntityPropertiesHandler.INSTANCE.getProperties(entity, StoneEntityProperties.class) != null && !EntityPropertiesHandler.INSTANCE.getProperties(entity, StoneEntityProperties.class).isStone));
            }
        });
        double d2 = d1;
        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = list.get(j);
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double) entity1.getCollisionBorderSize());
            Vec3d raytraceresult = axisalignedbb.rayTrace(vec3d, vec3d2).orElseGet(null);

            if (axisalignedbb.contains(vec3d)) {
                if (d2 >= 0.0D) {
                    pointedEntity = entity1;
                    d2 = 0.0D;
                }
            } else if (raytraceresult != null) {
                double d3 = vec3d.distanceTo(raytraceresult);

                if (d3 < d2 || d2 == 0.0D) {
                    if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !player.canRiderInteract()) {
                        if (d2 == 0.0D) {
                            pointedEntity = entity1;
                        }
                    } else {
                        pointedEntity = entity1;
                        d2 = d3;
                    }
                }
            }
        }
        if (pointedEntity != null) {
            if (pointedEntity instanceof LivingEntity) {
                MiscEntityProperties theirProperties = EntityPropertiesHandler.INSTANCE.getProperties(pointedEntity, MiscEntityProperties.class);
                if (theirProperties != null) {
                    theirProperties.inLoveTicks = 600;
                }
                itemStackIn.damageItem(2, player, (p_220046_0_) -> {
                    p_220046_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        }
        player.playSound(IafSoundRegistry.SIREN_SONG, 1, 1);
        return new ActionResult<ItemStack>(ActionResultType.PASS, itemStackIn);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.iceandfire.legendary_weapon.desc").applyTextStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.iceandfire.siren_flute.desc_0").applyTextStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.iceandfire.siren_flute.desc_1").applyTextStyle(TextFormatting.GRAY));
    }
}
