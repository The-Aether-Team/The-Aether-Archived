package com.gildedgames.the_aether.client.gui.dialogue.entity;

import com.gildedgames.the_aether.client.gui.dialogue.DialogueOption;
import com.gildedgames.the_aether.client.gui.dialogue.GuiDialogue;
import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketInitiateValkyrieFight;

public class GuiValkyrieDialogue extends GuiDialogue {

    private EntityValkyrieQueen valkyrieQueen;

    private String title;

    private int medalSlotId = -1;

    public GuiValkyrieDialogue(EntityValkyrieQueen valkyrieQueen) {
        super("[\247e" + valkyrieQueen.getName() + ", the Valkyrie Queen\247r]", new DialogueOption[]{new DialogueOption("What can you tell me about this place?"), new DialogueOption("I wish to fight you!"), new DialogueOption("Nevermind")});

        this.title = this.getDialogue();
        this.valkyrieQueen = valkyrieQueen;
    }

    @Override
    public void dialogueClicked(DialogueOption dialogue) {
        if (this.getDialogueOptions().size() == 3) {
            if (dialogue.getDialogueId() == 0) {
                this.addDialogueMessage(this.title + ": This is a sanctuary for us Valkyries who seek rest.");
                this.dialogueTreeCompleted();
            } else if (dialogue.getDialogueId() == 1) {
                DialogueOption medalDialogue = new DialogueOption(this.getMedalDiaulogue());

                this.addDialogueWithOptions(this.title + ": Very well then. Bring me ten medals from my subordinates to prove your worth, then we'll see.", medalDialogue, new DialogueOption("On second thought, i'd rather not."));
            } else if (dialogue.getDialogueId() == 2) {
                this.addDialogueMessage(this.title + ": Goodbye adventurer.");
                this.dialogueTreeCompleted();
            }
        } else {
            if (dialogue.getDialogueId() == 0) {
                if (this.mc.theWorld.difficultySetting == EnumDifficulty.PEACEFUL) {
                    this.addDialogueMessage(this.title + ": Sorry, I don't fight with weaklings.");
                    this.dialogueTreeCompleted();

                    return;
                }

                if (this.medalSlotId != -1) {
                    AetherNetwork.sendToServer(new PacketInitiateValkyrieFight(this.medalSlotId, this.valkyrieQueen.getEntityId()));

                    this.valkyrieQueen.setBossReady(true);
                    this.addDialogueMessage(this.title + ": Now then, let's begin!");
                    this.dialogueTreeCompleted();
                } else {
                    this.addDialogueMessage(this.title + ": Take your time.");
                    this.dialogueTreeCompleted();
                }
            } else if (dialogue.getDialogueId() == 1) {
                this.addDialogueMessage(this.title + ": So be it then. Goodbye adventurer.");
                this.dialogueTreeCompleted();
            }
        }
    }

    private String getMedalDiaulogue() {
        for (int slotId = 0; slotId < this.mc.thePlayer.inventory.mainInventory.length; ++slotId) {
            ItemStack stack = this.mc.thePlayer.inventory.mainInventory[slotId];

            if (stack != null && stack.getItem() == ItemsAether.victory_medal) {
                if (stack.stackSize >= 10) {
                    this.medalSlotId = slotId;
                    return "I'm ready, I have the medals right here!";
                } else {
                    return "I'll return when I have them. (" + stack.stackSize + "/10)";
                }
            }
        }

        return "I'll return when I have them. (0/10)";
    }

}