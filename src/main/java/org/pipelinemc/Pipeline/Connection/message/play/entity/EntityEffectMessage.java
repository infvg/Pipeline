package org.pipelinemc.Pipeline.Connection.message.play.entity;

import com.flowpowered.networking.Message;
import lombok.Data;

@Data
public final class EntityEffectMessage implements Message {

    private final int id;
    private final int effect, amplifier;
    private final int duration;
    private final boolean hideParticles;

}
