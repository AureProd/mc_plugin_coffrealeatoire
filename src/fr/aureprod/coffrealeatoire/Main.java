package fr.aureprod.coffrealeatoire;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	public Boolean coffreON = false;
	public float seconds = (float) this.getConfig().getConfigurationSection("global").getInt("temps");
	public Main main = this;
	
	@Override
	public void onEnable() 
	{
		saveDefaultConfig();
		
		System.out.println("Le plugin coffre aleatoire est est demarre !!!");
		getCommand("start_coffrealeatoire").setExecutor(this);
		getCommand("stop_coffrealeatoire").setExecutor(this);
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
				
		Bukkit.getServer().getScheduler().runTaskTimer(this,new Runnable() 
		{			
			@Override
			public void run()
			{
				if(coffreON)
				{
					Random ran = new Random(); 
					Integer nxtbis = main.getConfig().getConfigurationSection("global.positions").getKeys(false).size();
					Integer nxt = ran.nextInt(nxtbis);
					
					nxtbis--;
					
					for (String stringters : main.getConfig().getConfigurationSection("global.positions").getKeys(false)) 
					{
						List<Float> xyzspawn = main.getConfig().getConfigurationSection("global.positions").getFloatList(stringters);
						String nomWorld = main.getConfig().getConfigurationSection("global").getString("monde");
						World world = main.getServer().getWorld(nomWorld);
						
						Location locspawn = new Location(world, xyzspawn.get(0), xyzspawn.get(1)+1, xyzspawn.get(2));
						
						locspawn.getBlock().setType(Material.AIR);
					}
			        
			        for (String stringters : main.getConfig().getConfigurationSection("global.positions").getKeys(false)) 
					{
						if (nxtbis == nxt) 
						{
							List<Float> xyzspawn = main.getConfig().getConfigurationSection("global.positions").getFloatList(stringters);
							String nomWorld = main.getConfig().getConfigurationSection("global").getString("monde");
							World world = main.getServer().getWorld(nomWorld);
							
							Location locspawn = new Location(world, xyzspawn.get(0), xyzspawn.get(1)+1, xyzspawn.get(2));
							
							locspawn.getBlock().setType(Material.AIR);
							locspawn.getBlock().setType(Material.CHEST);
							
							Chest chest = (Chest) locspawn.getBlock().getState();
							chest.getInventory().clear();
							chest.setCustomName(main.getConfig().getConfigurationSection("global.msg").getString("nom_coffre"));
							chest.update();
							
							Integer nb_items = main.getConfig().getConfigurationSection("global").getInt("nombre_items");
							
							for (int i = 0; i < nb_items; i++) 
							{
								Integer nxtbisters = main.getConfig().getConfigurationSection("global.items").getKeys(false).size();
								Integer nxtters = ran.nextInt(nxtbisters);
								
								nxtbisters--;
						        
						        for (String stringquatros : main.getConfig().getConfigurationSection("global.items").getKeys(false)) 
								{
									if (nxtbisters == nxtters) 
									{
										int itemcount = main.getConfig().getConfigurationSection("global.items").getInt(stringquatros);
										
										ItemStack item = new ItemStack(Material.getMaterial(stringquatros), itemcount);
										
										Integer slots_coffre = 27;
										Integer slotalea = ran.nextInt(slots_coffre);
										
										while (chest.getInventory().getItem(slotalea) != null)
										{
											slotalea = ran.nextInt(slots_coffre);
										}
										
										chest.getInventory().setItem(slotalea, item);
									}
									nxtbisters--;
								}
							}
							main.getServer().broadcastMessage(main.getConfig().getConfigurationSection("global.msg").getString("coffre_spawn"));
							
							return;
						}
						else nxtbis--;
					}
				}
				
				//Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new coffrealeatoire(this), (long)(this.seconds * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft
			}
		}, 0, (long)(seconds * 20)); // Always multiply by twenty because that's the amount of ticks in Minecraft
	}
	
	@Override
	public void onDisable() 
	{
		System.out.println("Le plugin coffre aleatoire est arreter !!!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) 
	{
		if (cmd.getName().equalsIgnoreCase("start_coffrealeatoire"))
		{
			if (args.length == 0)
			{
				coffreON = true;
				
				if (sender instanceof Player) 
				{
					sender.sendMessage(ChatColor.GREEN + "Le coffre est sur ON ...");
					System.out.println("Le coffre est sur ON ...");
				}
				else
				{
					System.out.println("Le coffre est sur ON ...");
				}
				
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Il ne faut pas mettre d'arguments dans cette commande !!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("stop_coffrealeatoire"))
		{
			if (args.length == 0)
			{
				coffreON = false;
				
				if (sender instanceof Player) 
				{
					sender.sendMessage(ChatColor.RED + "Le coffre est sur OFF ...");
					System.out.println("Le coffre est sur OFF ...");
				}
				else
				{
					System.out.println("Le coffre est sur OFF ...");
				}
				
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Il ne faut pas mettre d'arguments dans cette commande !!");
			}
		}
		return false;
	}
}
