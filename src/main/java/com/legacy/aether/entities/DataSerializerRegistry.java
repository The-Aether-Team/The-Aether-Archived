package com.legacy.aether.entities;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;

public class DataSerializerRegistry
{
    public static final DataSerializer<IBlockState> BLOCK_STATE_SERIALIZER = new DataSerializer<IBlockState>()
    {
    	@Override
        public void write(PacketBuffer buf, IBlockState value)
        {
        	buf.writeInt(Block.getStateId(value));
        }

        @Override
        public IBlockState read(PacketBuffer buf)
        {
            return Block.getStateById(buf.readInt());
        }

		@Override
        public DataParameter<IBlockState> createKey(int id)
        {
            return new DataParameter<IBlockState>(id, this);
        }

		@Override
		public IBlockState copyValue(IBlockState value)
		{
			return value;
		}
    };

    public static final DataSerializer<UUID> UUID_SERIALIZER = new DataSerializer<UUID>()
    {
		@Override
		public void write(PacketBuffer buf, UUID value) 
		{
			buf.writeUniqueId(value);
		}

		@Override
		public UUID read(PacketBuffer buf)
		{
			return buf.readUniqueId();
		}

		@Override
		public DataParameter<UUID> createKey(int id)
		{
			return new DataParameter<UUID>(id, this);
		}

		@Override
		public UUID copyValue(UUID value) 
		{
			return value;
		}
    };

    public static void initialize()
    {
    	DataSerializers.registerSerializer(BLOCK_STATE_SERIALIZER);
    	DataSerializers.registerSerializer(UUID_SERIALIZER);
    }
}