package com.legacy.aether.client.gui.dialogue.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

import com.legacy.aether.client.gui.dialogue.DialogueOption;
import com.legacy.aether.client.gui.dialogue.GuiDialogue;
import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketInitiateValkyrieFight;

public class GuiValkyrieDialogue extends GuiDialogue
{

	private EntityValkyrieQueen valkyrieQueen;

	private String title;

	private int medalSlotId = -1;

	public GuiValkyrieDialogue(EntityValkyrieQueen valkyrieQueen) 
	{
		super("[\247e" + valkyrieQueen.getBossName() + ", the Valkyrie Queen\247r]", new DialogueOption[] {new DialogueOption("What can you tell me about this place?"), new DialogueOption("I wish to fight you!"), new DialogueOption("Nevermind")});

		this.title = this.getDialogue();
		this.valkyrieQueen = valkyrieQueen;
	}

	@Override
	public void dialogueClicked(DialogueOption dialogue)
	{
		if (this.getDialogueOptions().size() == 3)
		{
			if (dialogue.getDialogueId() == 0)
			{
				this.addDialogueMessage(this.title + ": This is a sanctuary for us Valkyries who seek rest.");
				this.dialogueTreeCompleted();
			}
			else if (dialogue.getDialogueId() == 1)
			{
				DialogueOption medalDialogue = new DialogueOption(this.getMedalDiaulogue());

				this.addDialogueWithOptions(this.title + ": Very well then. Bring me ten medals from my subordinates to prove your worth, then we'll see.", medalDialogue, new DialogueOption("On second thought, i'd rather not."));
			}
			else if (dialogue.getDialogueId() == 2)
			{
				this.addDialogueMessage(this.title + ": Goodbye adventurer.");
				this.dialogueTreeCompleted();
			}
		}
		else
		{
			if (dialogue.getDialogueId() == 0)
			{
	        	if (this.mc.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	        	{
	        		this.addDialogueMessage(this.title +  ": Sorry, I don't fight with weaklings.");
					this.dialogueTreeCompleted();

					return;
	        	}

				if (this.medalSlotId != -1)
				{
					AetherNetworkingManager.sendToServer(new PacketInitiateValkyrieFight(this.medalSlotId, this.valkyrieQueen.getEntityId()));

					this.valkyrieQueen.setBossReady(true);
					this.addDialogueMessage(this.title + ": Now then, let's begin!");
					this.dialogueTreeCompleted();
				}
				else
				{
					this.addDialogueMessage(this.title + ": Take your time.");
					this.dialogueTreeCompleted();
				}
			}
			else if (dialogue.getDialogueId() == 1)
			{
				this.addDialogueMessage(this.title + ": So be it then. Goodbye adventurer.");
				this.dialogueTreeCompleted();
			}
		}
	}

	private String getMedalDiaulogue()
	{
		for (int slotId = 0; slotId < this.mc.player.inventory.mainInventory.size(); ++slotId)
		{
			ItemStack stack = this.mc.player.inventory.mainInventory.get(slotId);

			if (stack.getItem() == ItemsAether.victory_medal)
			{
				if (stack.getCount() >= 10)
				{
					this.medalSlotId = slotId;
					return "I'm ready, I have the medals right here!";
				}
				else
				{
					return "I'll return when I have them. (" + stack.getCount() + "/10)";
				}
			}
		}

		return "I'll return when I have them. (0/10)";
	}

}