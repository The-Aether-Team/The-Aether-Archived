package com.gildedgames.the_aether.client.gui.dialogue.entity;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketInitiateValkyrieFight;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;

import com.gildedgames.the_aether.client.gui.dialogue.DialogueOption;
import com.gildedgames.the_aether.client.gui.dialogue.GuiDialogue;
import com.gildedgames.the_aether.items.ItemsAether;

public class GuiValkyrieDialogue extends GuiDialogue
{

	private EntityValkyrieQueen valkyrieQueen;

	private String title;

	private boolean medalCountMet = false;

	public static final int MEDALS_NEEDED = 10;

	public GuiValkyrieDialogue(EntityValkyrieQueen valkyrieQueen)
	{
		super("[" + TextFormatting.YELLOW + valkyrieQueen.getBossName() + ", " + I18n.format("title.aether_legacy.valkyrie_queen.name") + TextFormatting.RESET + "]", new DialogueOption(I18n.format("gui.queen.dialog.0")), new DialogueOption(I18n.format("gui.queen.dialog.1")), new DialogueOption(I18n.format("gui.queen.dialog.2")));

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
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.queen.answer.0"));
				this.dialogueTreeCompleted();
			}
			else if (dialogue.getDialogueId() == 1)
			{
				DialogueOption medalDialogue = new DialogueOption(this.getMedalDiaulogue());

				this.addDialogueWithOptions(this.title + ": " + I18n.format("gui.queen.answer.1"), medalDialogue, new DialogueOption(I18n.format("gui.valkyrie.dialog.player.denyfight")));
			}
			else if (dialogue.getDialogueId() == 2)
			{
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.queen.answer.2"));
				this.dialogueTreeCompleted();
			}
		}
		else
		{
			if (dialogue.getDialogueId() == 0)
			{
	        	if (this.mc.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	        	{
	        		this.addDialogueMessage(this.title +  ": " + I18n.format("gui.queen.peaceful"));
					this.dialogueTreeCompleted();

					return;
	        	}

				if (this.medalCountMet)
				{
					AetherNetworkingManager.sendToServer(new PacketInitiateValkyrieFight(this.valkyrieQueen.getEntityId()));

					this.valkyrieQueen.setBossReady(true);
					this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.ready"));
					this.dialogueTreeCompleted();
				}
				else
				{
					this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.nomedals"));
					this.dialogueTreeCompleted();
				}
			}
			else if (dialogue.getDialogueId() == 1)
			{
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.nofight"));
				this.dialogueTreeCompleted();
			}
		}
	}

	private String getMedalDiaulogue()
	{
		int medals = 0;
		for (int slotId = 0; slotId < this.mc.player.inventory.mainInventory.size(); ++slotId)
		{
			ItemStack stack = this.mc.player.inventory.mainInventory.get(slotId);

			if (stack.getItem() == ItemsAether.victory_medal)
			{
				medals += stack.getCount();
			}
		}

		if (medals >= MEDALS_NEEDED)
		{
			this.medalCountMet = true;
			return I18n.format("gui.valkyrie.dialog.player.havemedals");
		}
		else
		{
			return I18n.format("gui.valkyrie.dialog.player.lackmedals") + " (" + medals + "/" + MEDALS_NEEDED + ")";
		}
	}
}