package com.gildedgames.the_aether.entities.effects;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketSendPoison;
import com.gildedgames.the_aether.player.PlayerAether;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

public class PotionInebriation extends Potion
{
    public static Potion inebriation;

    public static void initialization()
    {
        inebriation = new PotionInebriation();
    }

    private int duration;

    public double rotD, motD;

    public PotionInebriation()
    {
        super(AetherConfig.getInebriationId(), true, 0x51297B);
        this.duration = 0;
        this.setPotionName("effect.aether.inebriation");
        this.setIconIndex(0, 0);
    }

    public boolean isReady(int duration, int amplifier)
    {
        this.duration = duration;
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn != null)
        {
            this.distractEntity(entityLivingBaseIn);

            if (this.duration % 50 == 0)
            {
                entityLivingBaseIn.attackEntityFrom(new DamageSource("inebriation").setDamageBypassesArmor(), 1.0F);
            }

            if (entityLivingBaseIn instanceof EntityPlayer)
            {
                if (this.duration >= 500)
                {
                    EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                    IPlayerAether iPlayerAether = AetherAPI.get(player);

                    if (iPlayerAether != null)
                    {
                        PlayerAether playerAether = (PlayerAether) iPlayerAether;

                        if (!player.worldObj.isRemote)
                        {
                            playerAether.setPoisoned();
                            AetherNetwork.sendToAll(new PacketSendPoison(player));
                        }
                    }
                }
            }
        }
    }

    public void distractEntity(EntityLivingBase entityLivingBaseIn)
    {
        double gaussian = entityLivingBaseIn.worldObj.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;
        entityLivingBaseIn.motionX += this.motD;
        entityLivingBaseIn.motionZ += this.motD;
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        entityLivingBaseIn.rotationYaw = (float)((double)entityLivingBaseIn.rotationYaw + rotD);
        entityLivingBaseIn.rotationPitch = (float)((double)entityLivingBaseIn.rotationPitch + rotD);

        if (entityLivingBaseIn.worldObj instanceof WorldServer)
        {
            ((WorldServer) entityLivingBaseIn.worldObj).func_147487_a("iconcrack_" + Item.getIdFromItem(Items.dye) + "_" + 1, entityLivingBaseIn.posX, entityLivingBaseIn.boundingBox.minY + entityLivingBaseIn.height * 0.8D, entityLivingBaseIn.posZ, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(Aether.locate("textures/gui/inventory/inebriation.png"));
        return super.getStatusIconIndex();
    }
}
