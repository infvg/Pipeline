package org.pipelinemc.Pipeline.Connection.codec.play.entity;

import com.flowpowered.networking.Codec;
import com.flowpowered.networking.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.glowstone.net.message.play.entity.EntityEffectMessage;

import java.io.IOException;

public final class EntityEffectCodec implements Codec<EntityEffectMessage> {
    @Override
    public EntityEffectMessage decode(ByteBuf buf) throws IOException {
        int id = ByteBufUtils.readVarInt(buf);
        byte effect = buf.readByte();
        byte amplifier = buf.readByte();
        int duration = ByteBufUtils.readVarInt(buf);
        boolean hideParticles = buf.readBoolean();
        return new EntityEffectMessage(id, effect, amplifier, duration, hideParticles);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, EntityEffectMessage message) throws IOException {
        ByteBufUtils.writeVarInt(buf, message.getId());
        buf.writeByte(message.getEffect());
        buf.writeByte(message.getAmplifier());
        ByteBufUtils.writeVarInt(buf, message.getDuration());
        buf.writeBoolean(message.isHideParticles());
        return buf;
    }
}
