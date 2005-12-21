package com.planet_ink.coffee_mud.Commands;
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
   Copyright 2000-2005 Bo Zimmerman

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
public class Order extends StdCommand
{
	public Order(){}

	private String[] access={"ORDER"};
	public String[] getAccessWords(){return access;}

	public boolean execute(MOB mob, Vector commands)
		throws java.io.IOException
	{
		if(commands.size()<3)
		{
			mob.tell("Order who do to what?");
			return false;
		}
		commands.removeElementAt(0);
		if(commands.size()<2)
		{
			mob.tell("Order them to do what?");
			return false;
		}
		if((!CMSecurity.isAllowed(mob,mob.location(),"ORDER"))
		&&(!mob.isMonster())
		&&(CMath.bset(mob.getBitmap(),MOB.ATT_AUTOASSIST)))
		{
			mob.tell("You may not order someone around with AUTOASSIST off.");
			return false;
		}

		String whomToOrder=(String)commands.elementAt(0);
		Vector V=new Vector();
		boolean allFlag=whomToOrder.equalsIgnoreCase("all");
		if(whomToOrder.toUpperCase().startsWith("ALL.")){ allFlag=true; whomToOrder="ALL "+whomToOrder.substring(4);}
		if(whomToOrder.toUpperCase().endsWith(".ALL")){ allFlag=true; whomToOrder="ALL "+whomToOrder.substring(0,whomToOrder.length()-4);}
		int addendum=1;
		String addendumStr="";
		do
		{
			MOB target=target=mob.location().fetchInhabitant(whomToOrder+addendumStr);
			if(target==null) break;
			if((CMLib.flags().canBeSeenBy(target,mob))
			&&(target!=mob)
			&&(!V.contains(target)))
				V.addElement(target);
			addendumStr="."+(++addendum);
		}
		while(allFlag);

		if(V.size()==0)
		{
            if(whomToOrder.equalsIgnoreCase("ALL"))
    			mob.tell("You don't see anyone called '"+whomToOrder+"' here.");
            else
                mob.tell("You don't see anyone here.");
			return false;
		}
		
		MOB target=null;
		if(V.size()==1)
		{
			target=(MOB)V.firstElement();
			if((!CMLib.flags().canBeSeenBy(target,mob))
			||(!CMLib.flags().canBeHeardBy(mob,target))
			||(target.location()!=mob.location()))
			{
				mob.tell("'"+whomToOrder+"' doesn't seem to be listening.");
				return false;
			}
			if(!target.willFollowOrdersOf(mob))
			{
				mob.tell("You can't order '"+target.name()+"' around.");
				return false;
			}
		}

		commands.removeElementAt(0);

		Object O=CMLib.english().findCommand(mob,commands);
		String order=CMParms.combine(commands,0);
		if(!CMSecurity.isAllowed(mob,mob.location(),"ORDER"))
		{
			if((O instanceof Command)&&(!((Command)O).canBeOrdered()))
			{
				mob.tell("You can't order anyone to '"+order+"'.");
				return false;
			}
		}
			
		Vector doV=new Vector();
		for(int v=0;v<V.size();v++)
		{
			target=(MOB)V.elementAt(v);
			O=CMLib.english().findCommand(target,commands);
			if(!CMSecurity.isAllowed(mob,mob.location(),"ORDER"))
			{
				if((O instanceof Command)&&(!((Command)O).canBeOrdered()))
				{
					mob.tell("You can't order "+target.name()+" to '"+order+"'.");
					continue;
				}
				if(O instanceof Ability)
					O=CMLib.english().getToEvoke(target,commands);
				if(O instanceof Ability)
				{
					if(CMath.bset(((Ability)O).flags(),Ability.FLAG_NOORDERING))
					{
						mob.tell("You can't order "+target.name()+" to '"+order+"'.");
						continue;
					}
				}
			}
			if((!CMLib.flags().canBeSeenBy(target,mob))
			||(!CMLib.flags().canBeHeardBy(mob,target))
			||(target.location()!=mob.location()))
				mob.tell("'"+whomToOrder+"' doesn't seem to be listening.");
			else
			if(!target.willFollowOrdersOf(mob))
				mob.tell("You can't order '"+target.name()+"' around.");
			else
			{
				CMMsg msg=CMClass.getMsg(mob,target,null,CMMsg.MSG_SPEAK,"^T<S-NAME> order(s) <T-NAMESELF> to '"+order+"'^?.");
				if(mob.location().okMessage(mob,msg))
				{
					mob.location().send(mob,msg);
					doV.addElement(target);
				}
			}
		}
		for(int v=0;v<doV.size();v++)
		{
			target=(MOB)doV.elementAt(v);
			target.enqueCommand((Vector)commands.clone(),0);
		}
		return false;
	}
	public double actionsCost(){return 1.0;}
	public boolean canBeOrdered(){return true;}

	
}
