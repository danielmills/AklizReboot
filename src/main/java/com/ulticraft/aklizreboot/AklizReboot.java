package com.ulticraft.aklizreboot;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AklizReboot extends JavaPlugin
{
	private Logger logger;
	private ConfigurationFile config;
	private AklizController controller;
	private boolean setup;
	
	@Override
	public void onEnable()
	{
		logger = getServer().getLogger();
		log("Starting AklizReboot");
		config = new ConfigurationFile(this);
		setup = true;
		
		if(!getDataFolder().exists())
		{
			getDataFolder().mkdirs();
		}
		
		if(!new File(getDataFolder(), "config.yml").exists())
		{
			try
			{
				new File(getDataFolder(), "config.yml").createNewFile();
				config.saveConfig();
			} 
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		if(config.getDataConnectionId().equals(Data.DEFAULT_ID))
		{
			setup = false;
		}
		
		if(config.getDataConnectionUsername().equals(Base64.encodeBytes(getConfig().getString(Data.DEFAULT_USERNAME).getBytes())))
		{
			setup = false;
		}
		
		if(config.getDataConnectionPassword().equals(Base64.encodeBytes(getConfig().getString(Data.DEFAULT_PASSWORD).getBytes())))
		{
			setup = false;
		}
		
		if(isSetup())
		{
			dbg("Authentication credentials look ok.");
		}
		
		else
		{
			dbg("Authentication credentials may be incorrect. Kicking into setup mode.");
			log("Will notify all users with the permission to use this plugin to set it up.");
		}
	}
	
	@Override
	public void onDisable()
	{
		log("Stopping AklizReboot");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return CommandHandler.command(this, sender, cmd, label, args);
	}
	
	public void log(String msg)
	{
		logger.info(msg);
	}
	
	public void warn(String msg)
	{
		logger.warning(msg);
	}
	
	public void dbg(String msg)
	{
		if(getConfiguration().isDataPluginDebug())
		{
			logger.info("DEBUG: " + msg);
		}
	}
	
	public ConfigurationFile getConfiguration()
	{
		return config;
	}
	
	public AklizController getAklizController()
	{
		return controller;
	}
	
	public boolean isSetup()
	{
		return setup;
	}
}
