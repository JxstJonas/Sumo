package net.vergessxner.sumo.utils.helpers;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * @author Jonas
 * Created: 25 Juni 2021
 */

public class KnockBack {

    private static Random random = new Random();

    public static void applyKnockback(LivingEntity livingEntity, Item ball, double resistance){
        if(random.nextDouble() >= resistance){
            double dist = 1;
            dist += random.nextDouble()*0.4-0.2; // adds or subtract 0.2 to the distance
            int knockBackLevel = 1;
            dist += 3*knockBackLevel;
            if(reducedKnockback.contains(livingEntity.getType())){
                dist *= 0.5;
            }
            double mag = distanceToMagnitude(dist);
            Location location = getLocation(ball);
            location.setPitch(location.getPitch()-15);
            Vector velocity = setMag(location.getDirection(),mag);
            livingEntity.setVelocity(velocity);
        }
    }

    private static Location getLocation(Entity entity){
        if(entity instanceof Item){
            Item projectile = (Item) entity;
            Location location = entity.getLocation();
            location.setDirection(projectile.getVelocity());
            return location;
        }else{
            return entity.getLocation();
        }
    }

    private static double distanceToMagnitude(double distance){
        return ((distance + 1.5)/5d);
    }

    private static Vector setMag(Vector vector, double mag){
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        double denominator = Math.sqrt(x*x + y*y + z*z);
        if(denominator != 0 ){
            return vector.multiply(mag/denominator);
        }else{
            return vector;
        }
    }

    private static int getKnockBackLevel(Entity entity){
        if(entity instanceof LivingEntity){
            ItemStack mainHand = ((LivingEntity)entity).getEquipment().getItemInMainHand();
            if(mainHand != null && mainHand.hasItemMeta() && mainHand.getItemMeta().hasEnchants() && mainHand.getItemMeta().hasEnchant(Enchantment.KNOCKBACK)){
                return mainHand.getItemMeta().getEnchantLevel(Enchantment.KNOCKBACK);
            }else{
                return 0;
            }
        }else if(entity instanceof AbstractArrow){
            AbstractArrow arrow = (AbstractArrow) entity;
            return arrow.getKnockbackStrength();
        }else{
            return 0;
        }
    }

    private static ImmutableSet<EntityType> reducedKnockback = new ImmutableSet.Builder<EntityType>()
            .add(EntityType.VEX)
            .build();

}
