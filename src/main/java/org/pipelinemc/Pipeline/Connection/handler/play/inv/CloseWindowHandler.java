package org.pipelinemc.Pipeline.Connection.handler.play.inv;

import com.flowpowered.networking.MessageHandler;
import net.glowstone.net.GlowSession;
import net.glowstone.net.message.play.inv.CloseWindowMessage;

public final class CloseWindowHandler implements MessageHandler<GlowSession, CloseWindowMessage> {
    @Override
    public void handle(GlowSession session, CloseWindowMessage message) {
        // closing the inventory will drop any items as needed
        session.getPlayer().closeInventory();
    }
}
