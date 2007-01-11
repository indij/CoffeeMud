package com.planet_ink.coffee_mud.MOBS;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/* 
   Copyright 2000-2007 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class Teacher extends StdMOB
{
	public String ID(){return "Teacher";}
	public Teacher()
	{
		super();
		Username="Cornelius, Knower of All Things";
		setDescription("He looks wise beyond his years.");
		setDisplayText("Cornelius is standing here contemplating your ignorance.");
		CMLib.factions().setAlignment(this,Faction.ALIGN_GOOD);
		setMoney(100);
		baseEnvStats.setWeight(150);
		setWimpHitPoint(200);

		Behavior B=CMClass.getBehavior("MOBTeacher");
		if(B!=null) addBehavior(B);
		B=CMClass.getBehavior("MudChat");
		if(B!=null) addBehavior(B);
		B=CMClass.getBehavior("CombatAbilities");
		if(B!=null) addBehavior(B);

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,25);
		baseCharStats().setStat(CharStats.STAT_WISDOM,25);
		baseCharStats().setStat(CharStats.STAT_CHARISMA,25);
		baseCharStats().setStat(CharStats.STAT_DEXTERITY,25);
		baseCharStats().setStat(CharStats.STAT_STRENGTH,25);
		baseCharStats().setStat(CharStats.STAT_CONSTITUTION,25);
		baseCharStats().setMyRace(CMClass.getRace("Human"));
		baseCharStats().getMyRace().startRacing(this,false);

		baseEnvStats().setAbility(10);
		baseEnvStats().setLevel(25);
		baseEnvStats().setArmor(-500);

		baseState.setHitPoints(4999);
		baseState.setMana(4999);
		baseState.setMovement(4999);

		recoverMaxState();
		resetToMaxState();
		recoverEnvStats();
		recoverCharStats();
	}




}
