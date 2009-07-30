/**
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
package com.aionemu.gameserver.network.aion.serverpackets.unk;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.Version;

/**
 * This packet is notify client what map should be loaded.
 * 
 * @author -Nemesiss-
 * 
 */
public class SM_UNKF5 extends AionServerPacket
{
	/**
	 * Player that is entering game.
	 */
	private final Player	player;

	/**
	 * Constructor.
	 * 
	 * @param player
	 */
	public SM_UNKF5(Player player)
	{
		super(Version.Chiness ? 0xF5 : 0x07);
		this.player = player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(AionConnection con, ByteBuffer buf)
	{
		writeOP(buf, getOpcode());
		writeD(buf, player.getWorldId());// worldid+instanceid?
		writeD(buf, player.getWorldId());
		writeD(buf, 0x00);// unk
		writeC(buf, 0x00);// unk
		writeF(buf, player.getX());// x
		writeF(buf, player.getY());// y
		writeF(buf, player.getZ());// z
		writeC(buf, player.getHeading());// heading
	}
}