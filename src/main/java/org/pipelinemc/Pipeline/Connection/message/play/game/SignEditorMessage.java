package org.pipelinemc.Pipeline.Connection.message.play.game;

import com.flowpowered.networking.Message;
import lombok.Data;

@Data
public final class SignEditorMessage implements Message {

    private final int x, y, z;

}

