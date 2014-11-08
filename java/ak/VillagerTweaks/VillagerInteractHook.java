package ak.VillagerTweaks;

import com.google.common.base.Optional;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.village.Village;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Random;

public class VillagerInteractHook
{
    public static int vanillaTraderkind = 5;
	private Random rand = new Random();
	public static ChunkPosition pos = new ChunkPosition(0, -1, 0);
	@SubscribeEvent
	public void interactEvent(EntityInteractEvent event)
	{
		ItemStack item = event.entityPlayer.getCurrentEquippedItem();
		if(event.target instanceof EntityVillager && item != null)
		{
			EntityVillager vil = (EntityVillager) event.target;
			if(getUniqueStrings(item.getItem()).equals(VillagerTweaks.changeTradeItem))
			{
				changeVillagerTrade(vil, item);
				event.setCanceled(true);
			}
			else if(getUniqueStrings(item.getItem()).equals(VillagerTweaks.changeProfessionItem))
			{
				changeVillagerProfession(vil, item);
				event.setCanceled(true);
			}
			else if(getUniqueStrings(item.getItem()).equals(VillagerTweaks.posChangeItem))
			{
				changeVillagerHomePos(vil,event.entityPlayer);
				event.setCanceled(true);
			}
		}
	}
	@SubscribeEvent
	public void interactBlock(PlayerInteractEvent event)
	{
		String chat;
		if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK 
				&& event.entityPlayer.getCurrentEquippedItem() != null 
				&& getUniqueStrings(event.entityPlayer.getCurrentEquippedItem().getItem()).equals(VillagerTweaks.posChangeItem))
		{
			pos = new ChunkPosition(event.x, event.y, event.z);
			chat = String.format("Regist Home Pos x: %d y: %d z: %d", event.x,event.y,event.z);
			event.entityPlayer.addChatMessage(new ChatComponentText(chat));
		}
	}
	private void changeVillagerTrade(EntityVillager vil, ItemStack changeItem)
	{
		ObfuscationReflectionHelper.setPrivateValue(EntityVillager.class, vil, null, 5);
		changeItem.stackSize--;
	}
	private void changeVillagerProfession(EntityVillager vil, ItemStack changeItem)
	{
		int extra = VillagerRegistry.getRegisteredVillagers().size();
        int trade = rand.nextInt(vanillaTraderkind + extra);
        vil.setProfession(trade < vanillaTraderkind ? trade : (Integer)VillagerRegistry.getRegisteredVillagers().toArray()[trade - vanillaTraderkind]);
		ObfuscationReflectionHelper.setPrivateValue(EntityVillager.class, vil, null, 5);
		changeItem.stackSize--;
	}
	private void changeVillagerHomePos(EntityVillager vil, EntityPlayer player)
	{
		String chat;
		Village village = ObfuscationReflectionHelper.getPrivateValue(EntityVillager.class, vil, 3);
		if(village == null)
			return;
		vil.setHomeArea(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, (int)((float)(village.getVillageRadius() * 0.6F)));
		chat = String.format("Set Home Pos x: %d y: %d z: %d", pos.chunkPosX,pos.chunkPosY,pos.chunkPosZ);
		player.addChatMessage(new ChatComponentText(chat));
	}
	private void setVillagerMating(EntityVillager vil, ItemStack setItem)
	{
		setItem.stackSize--;
	}
    public static String getUniqueStrings(Object obj) {
        UniqueIdentifier uId = null;
        if (obj instanceof ItemStack) {
            obj = ((ItemStack)obj).getItem();
        }
        if (obj instanceof Block) {
            uId = GameRegistry.findUniqueIdentifierFor((Block) obj);
        }
        if (obj instanceof Item){
            uId = GameRegistry.findUniqueIdentifierFor((Item) obj);
        }
        return Optional.fromNullable(uId).or(new UniqueIdentifier("none:dummy")).toString();
    }
}