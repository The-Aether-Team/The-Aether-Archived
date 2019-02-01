package com.legacy.aether.entities.bosses.valkyrie_queen;

import javax.annotation.Nullable;

import com.legacy.aether.Aether;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.client.gui.dialogue.entity.GuiValkyrieDialogue;
import com.legacy.aether.entities.ai.EntityAIAttackContinuously;
import com.legacy.aether.entities.ai.valkyrie_queen.ValkyrieQueenAIWander;
import com.legacy.aether.entities.projectile.crystals.EntityThunderBall;
import com.legacy.aether.entities.util.AetherNameGen;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.AetherLootTables;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityValkyrieQueen extends EntityMob implements IAetherBoss
{

    public static final DataParameter<String> VALKYRIE_NAME = EntityDataManager.<String>createKey(EntityValkyrieQueen.class, DataSerializers.STRING);

    public static final DataParameter<Boolean> VALKYRIE_READY = EntityDataManager.<Boolean>createKey(EntityValkyrieQueen.class, DataSerializers.BOOLEAN);

    private EntityAIAttackContinuously enhancedCombat = new EntityAIAttackContinuously(this, 0.65D);

    public int angerLevel;

    public int timeLeft, timeUntilTeleport, chatTime;

    public int dungeonX, dungeonY, dungeonZ;

    public int dungeonEntranceZ;

    public double safeX, safeY, safeZ;

    public float sinage;

    public double lastMotionY;

    private IBlockState dungeonStone = BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);

    private IBlockState lockedDungeonStone = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);

    private IBlockState lockedLightDungeonStone = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic);

    public EntityValkyrieQueen(World world)
    {
        super(world);
        this.setSize(0.6F, 1.95F);

        this.timeUntilTeleport = this.rand.nextInt(250);

        this.registerEntityAI();
        this.setBossName(AetherNameGen.valkGen());

        this.setSpawnPosition((int) this.posX, (int) this.posY, (int) this.posZ);
    }

    public EntityValkyrieQueen(World world, double x, double y, double z)
    {
        this(world);

        this.setPosition(x, y, z);
        this.setSpawnPosition((int) x, (int) y, (int) z);
    }

    public void registerEntityAI()
    {
        this.targetTasks.addTask(0, this.enhancedCombat);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new ValkyrieQueenAIWander(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 200.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(28.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(13.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
        this.setHealth(500F);
    }

    public void entityInit()
    {       
        super.entityInit();
        this.posX = Math.floor(this.posX + 0.5D);
        this.posY = Math.floor(this.posY + 0.5D);
        this.posZ = Math.floor(this.posZ + 0.5D);
        this.dataManager.register(VALKYRIE_READY, false);
        this.dataManager.register(VALKYRIE_NAME, AetherNameGen.valkGen());
    }

    public void swingArm()
    {
        if (!this.isSwingInProgress)
        {
            this.isSwingInProgress = true;
        }
    }

    private void becomeAngryAt(EntityLivingBase entity)
    {
        this.setAttackTarget(entity);
        this.angerLevel = 200 + rand.nextInt(200);
    }

    public void setSpawnPosition(int i, int j, int k)
    {
        this.safeX = i;
        this.safeY = j;
        this.safeZ = k; 
    }

    public void setDungeonPosition(int i, int j, int k) 
    {
        this.dungeonX = i;
        this.dungeonY = j;
        this.dungeonZ = k;
    }

    private void unlockDoor() 
    {
        IBlockState state = Blocks.AIR.getDefaultState();

        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ), state);
        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ + 1), state);
        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ + 1), state);
        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ), state);
    }

    private void unlockTreasure() 
    {
        IBlockState state = Blocks.TRAPDOOR.getDefaultState();

        this.world.setBlockState(new BlockPos(this.dungeonX + 16, dungeonY + 1, dungeonZ + 9), state.withProperty(BlockTrapDoor.FACING, EnumFacing.SOUTH), 2);
        this.world.setBlockState(new BlockPos(this.dungeonX + 17, dungeonY + 1, dungeonZ + 9), state.withProperty(BlockTrapDoor.FACING, EnumFacing.SOUTH), 2);
        this.world.setBlockState(new BlockPos(this.dungeonX + 16, dungeonY + 1, dungeonZ + 10), state.withProperty(BlockTrapDoor.FACING, EnumFacing.NORTH), 2);
        this.world.setBlockState(new BlockPos(this.dungeonX + 17, dungeonY + 1, dungeonZ + 10), state.withProperty(BlockTrapDoor.FACING, EnumFacing.NORTH), 2);

        for (int x = this.dungeonX - 27; x < this.dungeonX + 30; x++)
        {
            for (int y = this.dungeonY - 1; y < this.dungeonY + 22; y++) 
            {
                for (int z = this.dungeonZ - 6; z < this.dungeonZ + 26; z++) 
                {
                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState block = this.world.getBlockState(pos);
                    
                    if (block == this.lockedDungeonStone)
                    {
                        this.world.setBlockState(pos, this.dungeonStone, 2);
                    }
                    if (block == this.dungeonStone) 
                    {
                        this.world.setBlockState(pos, this.dungeonStone, 2);
                    }
                    if (block == this.lockedLightDungeonStone) 
                    {
                        this.world.setBlockState(pos, this.dungeonStone, 2);
                    }
                }
            }
        }
    }

    private void chatItUp(EntityPlayer player, ITextComponent s)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (this.chatTime <= 0)
        {
            if (side.isClient())
            {
                Aether.proxy.sendMessage(player, s);
            }

            this.chatTime = 60;
        }
    }

    public void makeHomeShot(int shots, EntityPlayer player) 
    {
        for (int i = 0; i < shots; i++) 
        {
            EntityThunderBall e1 = new EntityThunderBall(this.world, this.posX - (this.motionX / 2D), this.posY, this.posZ - (this.motionZ / 2D), player);
            if (!this.world.isRemote)
            {
                this.world.spawnEntity(e1);
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand)
    {
        faceEntity(entityplayer, 180F, 180F);
        
        if (this.isBossReady())
        {
            this.chatItUp(entityplayer, new TextComponentTranslation("gui.queen.ready"));
        }
        else if (this.world.isRemote)
        {
            this.displayValkyrieDialogue();
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public void displayValkyrieDialogue()
    {
        if (this.world.isRemote)
        {
            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiValkyrieDialogue(this));
        }
    }

    @Override
    public void onEntityUpdate() 
    {
        super.onEntityUpdate();

        if (!this.isBossReady()) 
        {
            this.motionY *= .5f;
            this.moveStrafing = this.moveForward = 0;
        }
        else
        {
            if (this.getAttackTarget() instanceof EntityPlayer) 
            {
                if (this.timeUntilTeleport++ >= 450)
                {
                    if (this.onGround && this.rand.nextInt(10) == 0)
                    {
                        this.makeHomeShot(1, (EntityPlayer) this.getAttackTarget());
                    }
                    else
                    {
                        this.teleport(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 4);
                    }
                }
                else if (this.timeUntilTeleport < 446 && (this.posY <= 0D || this.posY <= (this.safeY - 16D)))
                {
                    this.timeUntilTeleport = 446;
                }
                else if ((this.timeUntilTeleport % 5) == 0 && !canEntityBeSeen(this.getAttackTarget()))
                {
                    this.timeUntilTeleport += 100;
                }
            }

            if (!this.world.isRemote)
            {
                for (int k = this.dungeonZ + 2; k < this.dungeonZ + 23; k += 7) 
                {
                    IBlockState state = this.world.getBlockState(new BlockPos.MutableBlockPos().setPos(this.dungeonX - 1, this.dungeonY, k));

                    if (state != this.lockedDungeonStone || state != this.lockedLightDungeonStone)
                    {
                        this.dungeonEntranceZ = k;
                        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY, k), this.lockedDungeonStone, 2);
                        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY, k + 1), this.lockedDungeonStone, 2);
                        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY + 1, k + 1), this.lockedDungeonStone, 2);
                        this.world.setBlockState(new BlockPos(this.dungeonX - 1, this.dungeonY + 1, k), this.lockedDungeonStone, 2);
                        return;
                    }
                }
            }
        }

        if (this.getAttackTarget() != null && this.getAttackTarget().isDead)
        {
            this.setAttackTarget(null);
            unlockDoor();
            this.angerLevel = 0;
        }

        if (this.chatTime > 0)
        {
            this.chatTime--;
        }
    }

    @Override
    public void onUpdate() 
    {
        this.lastMotionY = motionY;
        super.onUpdate();
        
        if (!this.onGround && this.getAttackTarget() != null && this.lastMotionY >= 0.0D && motionY < 0.0D && getDistance(this.getAttackTarget()) <= 16F && canEntityBeSeen(this.getAttackTarget())) 
        {
            double a = this.getAttackTarget().posX - posX;
            double b = this.getAttackTarget().posZ - posZ;
            double angle = Math.atan2(a, b);
            this.motionX = Math.sin(angle) * 0.25D;
            this.motionZ = Math.cos(angle) * 0.25D;
        }

        if (!this.onGround && !isOnLadder() && Math.abs(this.motionY - this.lastMotionY) > 0.07D && Math.abs(this.motionY - this.lastMotionY) < 0.09D)
        {
            this.motionY += 0.055F;
            
            if (this.motionY < -0.275F)
            {
                this.motionY = -0.275F;
            }
        }

        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && (this.getAttackTarget() != null || this.angerLevel > 0)) 
        {
            this.angerLevel = 0;
            this.setAttackTarget(null);
        }

        if (!this.onGround) 
        {
            this.sinage += 0.75F;
        } 
        else 
        {
            this.sinage += 0.15F;
        }

        if (this.sinage > 3.141593F * 2F)
        {
            this.sinage -= (3.141593F * 2F);
        }

        this.dismountRidingEntity();
        
        if (this.getHealth() <= 0 || this.isDead) 
        {
            unlockDoor();
            unlockTreasure();

            if (this.getAttackTarget() instanceof EntityPlayer)
            {
                chatItUp((EntityPlayer) this.getAttackTarget(), new TextComponentTranslation("gui.queen.defeated"));

                AetherAPI.getInstance().get((EntityPlayer) this.getAttackTarget()).setFocusedBoss(null);
            }

            spawnExplosionParticle();
            this.setDead();
        }

        if (!otherDimension())
        {
            this.timeLeft--;
            if (this.timeLeft <= 0)
            {
                spawnExplosionParticle();
                this.setDead();
            }
        }
    }
    
    @Override
    public void move(MoverType type, double x, double y, double z)
    {
        if (this.isBossReady())
        {
            super.move(type, x, y, z);
        }
        else
        {
            super.move(type, 0, y, 0);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short) angerLevel);
        nbttagcompound.setShort("TimeLeft", (short) timeLeft);
        nbttagcompound.setBoolean("Duel", this.isBossReady());
        nbttagcompound.setInteger("DungeonX", this.dungeonX);
        nbttagcompound.setInteger("DungeonY", this.dungeonY);
        nbttagcompound.setInteger("DungeonZ", this.dungeonZ);
        nbttagcompound.setInteger("DungeonEntranceZ", this.dungeonEntranceZ);
        nbttagcompound.setTag("SafePos", newDoubleNBTList(new double[] { this.safeX, this.safeY, this.safeZ }));
        nbttagcompound.setString("BossName", this.getBossName());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
        this.timeLeft = nbttagcompound.getShort("TimeLeft");
        this.setBossReady(nbttagcompound.getBoolean("Duel"));
        this.dungeonX = nbttagcompound.getInteger("DungeonX");
        this.dungeonY = nbttagcompound.getInteger("DungeonY");
        this.dungeonZ = nbttagcompound.getInteger("DungeonZ");
        this.dungeonEntranceZ = nbttagcompound.getInteger("DungeonEntranceZ");
        NBTTagList nbttaglist = nbttagcompound.getTagList("SafePos", 10);
        this.setBossName(nbttagcompound.getString("BossName"));

        this.safeX = nbttaglist.getDoubleAt(0);
        this.safeY = nbttaglist.getDoubleAt(1);
        this.safeZ = nbttaglist.getDoubleAt(2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource ds, float i) 
    {
        if (ds.getImmediateSource() instanceof EntityPlayer) 
        {
            EntityPlayer player = (EntityPlayer)ds.getImmediateSource();

            if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
            {
                this.spawnExplosionParticle();
                chatItUp(player, new TextComponentTranslation("gui.queen.peaceful"));
                return false;
            }
 
            if (!this.isBossReady()) 
            {
                spawnExplosionParticle();
                int pokey = rand.nextInt(2);

                if (pokey == 2)
                {
                    chatItUp(player, new TextComponentTranslation("gui.queen.peaceful"));
                } 
                else 
                {
                    chatItUp(player, new TextComponentTranslation("gui.queen.nomedals"));
                }
                return false;
            }
            else
            {
                IPlayerAether playerAether = AetherAPI.getInstance().get(player);
                boolean flag;

                if (playerAether != null)
                {
                    flag = true;

                    if (!player.isDead && flag)
                    {
                        playerAether.setFocusedBoss(this);
                    }
                }

                if (this.getAttackTarget() == null) 
                {
                    this.chatTime = 0;
                    chatItUp(player, new TextComponentTranslation("gui.queen.fight"));
                    if (ds.getImmediateSource() instanceof EntityLivingBase)
                    becomeAngryAt((EntityLivingBase) ds.getImmediateSource());
                }
                else
                {
                    this.timeUntilTeleport += 60;
                }
             }
           } 
           else 
           {
              extinguish();
              return false;
           }

        return super.attackEntityFrom(ds, i);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean flag = false;

        this.swingArm();
        flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8);

        if (entity != null && this.getAttackTarget() != null && entity == this.getAttackTarget() && entity instanceof EntityPlayer) 
        {
            EntityPlayer player = (EntityPlayer)entity;

            if (player.getHealth() <= 0 || player.isDead)
            {
                this.setAttackTarget(null);
                this.angerLevel = this.chatTime = 0;
                this.chatItUp(player, new TextComponentTranslation("gui.queen.lost"));
                this.unlockDoor();
            }
        }

        return flag;
    }

    @Override
    protected void dropFewItems(boolean var1, int var2)
    {
        //this.entityDropItem(new ItemStack(ItemsAether.dungeon_key, 1, 1), 0.5F);
        //this.dropItem(Items.GOLDEN_SWORD, 1);
    }
    
    @Override
    public boolean canBeLeashedTo(final EntityPlayer player)
    {
        return false;
    }


    @Override
    public EntityItem entityDropItem(ItemStack stack, float offsetY)
    {
        if (stack.getCount() != 0 && stack.getItem() != null)
        {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + (double)offsetY, this.posZ, stack);
            entityitem.setEntityInvulnerable(true);
            entityitem.setDefaultPickupDelay();
            if (captureDrops)
                this.capturedDrops.add(entityitem);
            else
                this.world.spawnEntity(entityitem);
            return entityitem;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void fall(float f, float somethingidk)
    {
    }

    public void teleport(double x, double y, double z, int rad)
    {
        int a = this.rand.nextInt(rad + 1);
        int b = this.rand.nextInt(rad / 2);
        int c = rad - a;

        a *= ((rand.nextInt(2) * 2) - 1); // Negate or Not
        b *= ((rand.nextInt(2) * 2) - 1); // Negate or Not
        c *= ((rand.nextInt(2) * 2) - 1); // Negate or Not

        x += (double) a;
        y += (double) b;
        z += (double) c;

        int newX = (int) Math.floor(x - 0.5D);
        int newY = (int) Math.floor(y - 0.5D);
        int newZ = (int) Math.floor(z - 0.5D);

        boolean flag = false;

        for (int q = 0; q < 32 && !flag; q++)
        {
            int i = newX + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int j = newY + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int k = newZ + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));

            if (j > 124 || j < 5) 
            {
                continue;
            }

            if (this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k) && !this.isAirySpace(i, j - 1, k) && (i > dungeonX && i < dungeonX + 20 && j > dungeonY && j < dungeonY + 12 && k > dungeonZ && k < dungeonZ + 20)) 
            {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag)
        {
            this.timeUntilTeleport -= (this.rand.nextInt(40) + 40);

            if (this.posY <= 0D)
            {
                this.timeUntilTeleport = 446;
            }
        }
        else 
        {
            this.spawnExplosionParticle();
            this.enhancedCombat.resetTask();
            this.setPosition((double) newX + 0.5D, (double) newY + 0.5D, (double) newZ + 0.5D);
            
            this.isJumping = false;
            this.renderYawOffset = this.rand.nextFloat() * 360F;
            this.timeUntilTeleport = this.rand.nextInt(40);

            this.motionX = this.motionY = this.motionZ = this.moveForward = this.moveStrafing = this.rotationPitch = this.rotationYaw = 0;
        }
    }

    public boolean isAirySpace(int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = this.world.getBlockState(new BlockPos(x, y, z));

        return state == Blocks.AIR || state.getCollisionBoundingBox(world, pos) == null;
    }

    public boolean otherDimension()
    {
        return true;
    }

    public boolean canDespawn()
    {
        return false;
    }
    
    public boolean isNonBoss()
    {
        return false;
    }

    @Override
    public boolean getCanSpawnHere() 
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        return this.world.getLight(new BlockPos(i, j, k)) > 8 && this.world.checkBlockCollision(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0 && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
    }

    public int getMedals(EntityPlayer entityplayer)
    {
        int medals = 0;

        for (ItemStack item : entityplayer.inventory.mainInventory)
        {
            if (item != null)
            {
                if (item.getItem() == ItemsAether.victory_medal)
                {
                    medals += item.getCount();
                }
            }
        }

        return medals;
    }

    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }
    
    protected SoundEvent getDeathSound()
    {
       return SoundEvents.ENTITY_GENERIC_HURT;
    }

    public String getBossName()
    {
        return this.dataManager.get(VALKYRIE_NAME);
    }

    public void setBossName(String name)
    {
        this.dataManager.set(VALKYRIE_NAME, name);
    }
    
    public String getBossTitle()
    {
       return this.getBossName() + ", " + new TextComponentTranslation("title.aether_legacy.valkyrie_queen.name", new Object[0]).getFormattedText();
    }

    public void setBossReady(boolean isReady)
    {
        this.dataManager.set(VALKYRIE_READY, isReady);
    }

    public boolean isBossReady()
    {
        return this.dataManager.get(VALKYRIE_READY).booleanValue();
    }
    
    @Override
    public float getEyeHeight()
    {
        return 1.75F;
    }

    @Override
    public float getBossHealth()
    {
        return this.getHealth();
    }

    @Override
    public float getMaxBossHealth()
    {
        return this.getMaxHealth();
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.valkyrie_queen;
    }
    
}