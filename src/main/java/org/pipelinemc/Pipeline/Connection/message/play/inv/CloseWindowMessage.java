package org.pipelinemc.Pipeline.Connection.message.play.inv;

import com.flowpowered.networking.Message;
import lombok.Data;

@Data
public final class CloseWindowMessage implements Message {

    private final int id;

}
