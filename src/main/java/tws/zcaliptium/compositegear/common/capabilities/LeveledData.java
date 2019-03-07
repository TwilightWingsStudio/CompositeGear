/*******************************************************************************
 * Copyright (c) 2018-2019 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.common.capabilities;

import tws.zcaliptium.compositegear.lib.capabilities.ILeveled;

/**
 * Default implementation for leveled 'something'.
 * 
 * @link ILeveled
 */
public class LeveledData implements ILeveled
{
	private int level;
	
	public LeveledData()
	{
		this.level = 0;
	}

	@Override
	public int getLevel()
	{
		return this.level;
	}

	@Override
	public void setLevel(int level)
	{
		this.level = level;
	}
}
