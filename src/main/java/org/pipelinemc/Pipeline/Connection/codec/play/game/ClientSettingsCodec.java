package org.pipelinemc.Pipeline.Connection.codec.play.game;

import com.flowpowered.networking.Codec;
import com.flowpowered.networking.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.glowstone.net.message.play.game.ClientSettingsMessage;

import java.io.IOException;

public final class ClientSettingsCodec implements Codec<ClientSettingsMessage> {
    @Override
    public ClientSettingsMessage decode(ByteBuf buf) throws IOException {
        String locale = ByteBufUtils.readUTF8(buf);
        int viewDistance = buf.readByte();
        int chatFlags = buf.readByte();
        boolean colors = buf.readBoolean();
        int skinFlags = buf.readUnsignedByte();
        return new ClientSettingsMessage(locale, viewDistance, chatFlags, colors, skinFlags);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, ClientSettingsMessage message) throws IOException {
        ByteBufUtils.writeUTF8(buf, message.getLocale());
        buf.writeByte(message.getViewDistance());
        buf.writeByte(message.getChatFlags());
        buf.writeBoolean(message.isChatColors());
        buf.writeByte(message.getSkinFlags());
        return buf;
    }
}
