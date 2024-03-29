package org.pipelinemc.Pipeline.Connection.message.play.entity;

import com.flowpowered.networking.Message;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class EntityRotationMessage implements Message {

    private final int id, rotation, pitch;
    private final boolean onGround;

    public EntityRotationMessage(int id, int rotation, int pitch) {
        this(id, rotation, pitch, true);
    }

}
