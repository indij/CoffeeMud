package com.planet_ink.coffee_mud.commands;
import com.planet_ink.coffee_mud.interfaces.*;
import java.util.*;
import java.io.IOException;
public class ExternalCommands implements ExternalCommand
{
	CommandProcessor processor=null;
	
	public ExternalCommands(CommandProcessor newProcessor)
	{
		processor=newProcessor;
	}
	
	public void postAttack(MOB attacker, MOB target, Item weapon)
	{
		processor.theFight.postAttack(attacker,target,weapon);
	}
	public void postDamage(MOB attacker, MOB target, Environmental weapon, int damage)
	{
		processor.theFight.postDamage(attacker,target,weapon,damage);
	}
	public String hitWord(int weaponType, int damageAmount)
	{
		return processor.theFight.hitWord(weaponType,damageAmount);
	}
	public void standIfNecessary(MOB mob)
	{
		processor.movement.standIfNecessary(mob);
	}
	public void look(MOB mob, Vector commands, boolean quiet)
	{
		processor.basicSenses.look(mob,commands,quiet);
	}
	public void resistanceMsgs(Affect affect, MOB source, MOB target)
	{
		processor.theFight.resistanceMsgs(affect,source,target);
	}
	public void strike(MOB source, MOB target, Weapon weapon, boolean success)
	{
		processor.theFight.strike(source,target,weapon,success);
	}
	public void doDamage(MOB source, MOB target, int damageAmount)
	{
		processor.theFight.doDamage(source,target,damageAmount);
	}
	public boolean isHit(MOB attacker, MOB target)
	{
		return processor.theFight.isHit(attacker,target);
	}
	public long adjustedAttackBonus(MOB mob)
	{
		return processor.theFight.adjustedAttackBonus(mob);
	}
	public Hashtable properTargets(Ability A, MOB caster)
	{
		return processor.theFight.properTargets(A,caster);
	}
	public String mobCondition(MOB mob)
	{
		return processor.theFight.mobCondition(mob);
	}
	public void move(MOB mob, int directionCode, boolean flee)
	{
		processor.movement.move(mob,directionCode,flee);
	}
	public void flee(MOB mob, String direction)
	{
		processor.movement.flee(mob,direction);
	}
	public void roomAffectFully(Affect msg, Room room, int dirCode)
	{
		processor.movement.roomAffectFully(msg,room,dirCode);
	}
	public boolean doAttack(MOB attacker, MOB target, Weapon weapon)
	{
		return processor.theFight.doAttack(attacker,target,weapon);
	}
	public StringBuffer getEquipment(MOB seer, MOB mob)
	{
		return processor.scoring.getEquipment(seer,mob);
	}
	public void doCommand(MOB mob, Vector commands)
		throws Exception
	{
		processor.doCommand(mob,commands);
	}
	public String shortAlignmentStr(MOB mob)
	{
		return processor.scoring.shortAlignmentStr(mob);
	}
	public String alignmentStr(MOB mob)
	{
		return processor.scoring.alignmentStr(mob);
	}
	public StringBuffer getInventory(MOB seer, MOB mob)
	{
		return processor.scoring.getInventory(seer,mob);
	}
	public int getMyDirCode(Exit exit, Room room, int testCode)
	{
		return processor.movement.getMyDirCode(exit,room,testCode);
	}
	public boolean drop(MOB mob, Environmental dropThis)
	{
		return processor.itemUsage.drop(mob,dropThis);
	}
	public void read(MOB mob, Environmental thisThang, String theRest)
	{
		processor.itemUsage.read(mob,thisThang,theRest);
	}
	public void quickSay(MOB mob, MOB target, String text, boolean isPrivate, boolean tellFlag)
	{
		processor.socialProcessor.quickSay(mob,target,text,isPrivate,tellFlag);
	}
	public void score(MOB mob)
	{
		processor.scoring.score(mob);
	}
	public StringBuffer showWho(MOB who, boolean shortForm)
	{
		return processor.grouping.showWho(who,shortForm);
	}
	public boolean get(MOB mob, Item container, Item getThis, boolean quiet)
	{
		return processor.itemUsage.get(mob,container,getThis,quiet);
	}
	public boolean login(MOB mob)
		throws IOException
	{
		return processor.frontDoor.login(mob);
	}
}