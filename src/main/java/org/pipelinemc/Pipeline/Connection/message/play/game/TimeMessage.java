package org.pipelinemc.Pipeline.Connection.message.play.game;

import com.flowpowered.networking.Message;
import lombok.Data;

@Data
public final class TimeMessage implements Message {

    private final long worldAge, time;

}
