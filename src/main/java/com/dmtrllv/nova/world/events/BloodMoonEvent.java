package com.dmtrllv.nova.world.events;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.WorldTickEvent;

public final class BloodMoonEvent
{
	private static final double BLOOD_MOON_DAYS_PER_CYCLE = 31;
	private static final double NIGHT_START_TICK = 13000;
	private static final double NIGHT_END_TICK = 23000;

	private static boolean isActive = false;

	public static boolean isBloodMoonActive()
	{
		return isActive;
	}

	public static void onTick(WorldTickEvent event)
	{
		if(event.world.dimension() == World.OVERWORLD)
			return;

		boolean active = isActive;

		long worldTime = event.world.dayTime();

		double d = Math.floor(worldTime / 24000);
		double timeInDay = worldTime % 24000;

		if (d % BLOOD_MOON_DAYS_PER_CYCLE == 0)
		{
			active = timeInDay >= NIGHT_START_TICK && timeInDay <= NIGHT_END_TICK;
		}
		else
		{
			active = false;
		}

		LogManager.getLogger().debug("worldTime: " + worldTime + ", timeinday: " + timeInDay + ", is blood moon active: " + active);
		isActive = active;
	}
}
