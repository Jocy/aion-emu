/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.gameobjects.stats.GameStats;
import com.aionemu.gameserver.model.gameobjects.stats.LifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.NpcGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.NpcLifeStats;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.SpawnTemplate;
import com.aionemu.gameserver.services.DecayService;
import com.aionemu.gameserver.services.RespawnService;
import com.aionemu.gameserver.utils.StatsFunctions;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * This class is a base class for all in-game NPCs, what includes: monsters and npcs that player can talk to (aka
 * Citizens)
 * 
 * @author Luno
 * 
 */
public class Npc extends Creature
{
	/**
	 *  Template keeping all base data for this npc 
	 */
	private NpcTemplate		template;

	/**
	 *  Spawn template of this npc. Currently every spawn template is responsible for spawning just one npc.
	 */
	private SpawnTemplate	spawn;
	
	/**
	 * Lifestats of this npc
	 */
	private NpcLifeStats npcLifeStats;
	
	/**
	 * Gamestats of this npc
	 */
	private NpcGameStats npcGameStats;
	
	/**
	 * Constructor creating instance of Npc.
	 * 
	 * @param spawn
	 *            SpawnTemplate which is used to spawn this npc
	 * @param objId
	 *            unique objId
	 */
	public Npc(SpawnTemplate spawn, int objId, NpcController controller)
	{
		super(objId, controller, new WorldPosition());

		this.template = spawn.getNpc();
		this.spawn = spawn;
		StatsFunctions.computeStats (this);
		controller.setOwner(this);
	}

	public NpcTemplate getTemplate()
	{
		return template;
	}

	public SpawnTemplate getSpawn()
	{
		return spawn;
	}

	@Override
	public String getName()
	{
		return getTemplate().getName();
	}

	public int getNpcId()
	{
		return getTemplate().getNpcId();
	}

	/**
	 * Return NpcController of this Npc object.
	 * 
	 * @return NpcController.
	 */
	@Override
	public NpcController getController()
	{
		return (NpcController) super.getController();
	}

	@Override
	public byte getLevel()
	{
		return getTemplate().getLevel();
	}
	
	public void onDie()
	{
		RespawnService.getInstance().scheduleRespawnTask(this);
		DecayService.getInstance().scheduleDecayTask(this);
	}

	/* (non-Javadoc)
	 * @see com.aionemu.gameserver.model.gameobjects.Creature#getGameStats()
	 */
	@Override
	public NpcGameStats getGameStats()
	{
		return npcGameStats;
	}

	/* (non-Javadoc)
	 * @see com.aionemu.gameserver.model.gameobjects.Creature#setGameStats(com.aionemu.gameserver.model.gameobjects.stats.GameStats)
	 */
	@Override
	public void setGameStats(GameStats gameStats)
	{
		this.npcGameStats = (NpcGameStats)gameStats;
	}

	/* (non-Javadoc)
	 * @see com.aionemu.gameserver.model.gameobjects.Creature#setLifeStats(com.aionemu.gameserver.model.gameobjects.stats.LifeStats)
	 */
	@Override
	public void setLifeStats(LifeStats lifeStats)
	{
		this.npcLifeStats = (NpcLifeStats)lifeStats;
	}

	/* (non-Javadoc)
	 * @see com.aionemu.gameserver.model.gameobjects.Creature#getLifeStats()
	 */
	@Override
	public NpcLifeStats getLifeStats()
	{
		return npcLifeStats;
	}
}
