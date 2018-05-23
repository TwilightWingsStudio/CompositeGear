/*******************************************************************************
 * Copyright (c) 2018 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.compositegear.lib;

public interface ISpecialArmor
{
	public boolean isWarm();
	public boolean isCold();
	public boolean isAirMask();

	//
	public boolean isSaveSatietyHot();
	public boolean isSaveSatietyCold();
}
