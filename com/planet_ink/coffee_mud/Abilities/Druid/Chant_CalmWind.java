package com.planet_ink.coffee_mud.Abilities.Druid;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
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
   Copyright 2000-2014 Bo Zimmerman

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

@SuppressWarnings("rawtypes")
public class Chant_CalmWind extends Chant
{
	@Override public String ID() { return "Chant_CalmWind"; }
	public final static String localizedName = CMLib.lang()._("Calm Wind");
	@Override public String name() { return localizedName; }
	@Override public int abstractQuality(){return Ability.QUALITY_INDIFFERENT;}
	@Override protected int canAffectCode(){return 0;}
	@Override protected int canTargetCode(){return 0;}
	@Override public long flags(){return Ability.FLAG_WEATHERAFFECTING;}
	@Override public int classificationCode(){return Ability.ACODE_CHANT|Ability.DOMAIN_WEATHER_MASTERY;}

	public static void xpWorthyChange(MOB mob, Climate oldC, Climate newC)
	{
		if((oldC.nextWeatherType(null)!=Climate.WEATHER_CLEAR)
		&&(oldC.nextWeatherType(null)!=Climate.WEATHER_CLOUDY)
		&&((newC.nextWeatherType(null)==Climate.WEATHER_CLEAR)
			||(newC.nextWeatherType(null)==Climate.WEATHER_CLOUDY))
		&&((newC.weatherType(null)==Climate.WEATHER_CLEAR)
				||(newC.weatherType(null)==Climate.WEATHER_CLOUDY)))
		{
			mob.tell(CMLib.lang()._("^YYou have restored balance to the weather!^N"));
			CMLib.leveler().postExperience(mob,null,null,25,false);
		}
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			final Room R=mob.location();
			if(R!=null)
			{
				if(CMath.bset(weatherQue(R),WEATHERQUE_CALM))
					return super.castingQuality(mob, target,Ability.QUALITY_BENEFICIAL_SELF);
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, Vector commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(((mob.location().domainType()&Room.INDOORS)>0)&&(!auto))
		{
			mob.tell(_("You must be outdoors for this chant to work."));
			return false;
		}
		switch(mob.location().getArea().getClimateObj().weatherType(mob.location()))
		{
		case Climate.WEATHER_WINDY:
		case Climate.WEATHER_THUNDERSTORM:
		case Climate.WEATHER_BLIZZARD:
		case Climate.WEATHER_DUSTSTORM:
			break;
		case Climate.WEATHER_HAIL:
		case Climate.WEATHER_SLEET:
		case Climate.WEATHER_SNOW:
		case Climate.WEATHER_RAIN:
			mob.tell(_("The weather is nasty, but not especially windy any more."));
			return false;
		default:
			mob.tell(_("If doesn't seem especially windy right now."));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		int size=mob.location().getArea().numberOfProperIDedRooms();
		size=size/(mob.phyStats().level()+(2*super.getXLEVELLevel(mob)));
		if(size<0) size=0;
		final boolean success=proficiencyCheck(mob,-size,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),auto?_("^JThe swirling sky changes color!^?"):_("^S<S-NAME> chant(s) into the swirling sky!^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Climate C=mob.location().getArea().getClimateObj();
				final Climate oldC=(Climate)C.copyOf();
				switch(C.weatherType(mob.location()))
				{
				case Climate.WEATHER_WINDY:
					C.setNextWeatherType(Climate.WEATHER_CLEAR);
					break;
				case Climate.WEATHER_THUNDERSTORM:
					C.setNextWeatherType(Climate.WEATHER_RAIN);
					break;
				case Climate.WEATHER_BLIZZARD:
					C.setNextWeatherType(Climate.WEATHER_SNOW);
					break;
				case Climate.WEATHER_DUSTSTORM:
					C.setNextWeatherType(Climate.WEATHER_CLEAR);
					break;
				default:
					break;
				}
				C.forceWeatherTick(mob.location().getArea());
				Chant_CalmWeather.xpWorthyChange(mob,mob.location().getArea(),oldC,C);
			}
		}
		else
			beneficialVisualFizzle(mob,null,_("<S-NAME> chant(s) into the sky, but the magic fizzles."));

		return success;
	}
}
