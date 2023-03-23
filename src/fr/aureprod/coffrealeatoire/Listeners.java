package fr.aureprod.coffrealeatoire;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class Listeners implements Listener 
{
	private Main main;
	
	public Listeners(Main mainbis)
	{
		this.main = mainbis;
	}
	
	@EventHandler
	public void onOuvreCoffre(InventoryCloseEvent event)
	{
		String nomWorld = main.getConfig().getConfigurationSection("global").getString("monde");
		World world = main.getServer().getWorld(nomWorld);
		
		if(event.getPlayer().getWorld().equals(world))
		{
			if(event.getInventory() != null)
			{
				if(event.getInventory().getType().equals(InventoryType.CHEST))
				{
					if(event.getInventory().getLocation() != null)
					{
						if(event.getInventory().getLocation().getBlock() != null)
						{
							if(event.getInventory().getLocation().getBlock().getType() != null)
							{
								if(event.getInventory().getLocation().getBlock().getType().equals(Material.CHEST))
								{
									if(((Chest) event.getInventory().getLocation().getBlock().getState()).getCustomName() != null)
									{
										if(((Chest) event.getInventory().getLocation().getBlock().getState()).getCustomName().equalsIgnoreCase(main.getConfig().getConfigurationSection("global.msg").getString("nom_coffre")))
										{
											event.getInventory().getLocation().getBlock().setType(Material.AIR);
											
											main.getServer().broadcastMessage(main.getConfig().getConfigurationSection("global.msg").getString("coffre_trouve"));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
