package org.pipelinemc.Pipeline.Connection.codec.play.player;

import com.flowpowered.networking.Codec;
import com.flowpowered.networking.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.glowstone.net.message.play.player.ResourcePackSendMessage;

import java.io.IOException;

public final class ResourcePackSendCodec implements Codec<ResourcePackSendMessage> {

    @Override
    public ResourcePackSendMessage decode(ByteBuf buffer) throws IOException {
        String url = ByteBufUtils.readUTF8(buffer);
        String hash = ByteBufUtils.readUTF8(buffer);
        return new ResourcePackSendMessage(url, hash);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, ResourcePackSendMessage message) throws IOException {
        ByteBufUtils.writeUTF8(buf, message.getUrl());
        ByteBufUtils.writeUTF8(buf, message.getHash());
        return buf;
    }
}
