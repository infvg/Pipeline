package org.pipelinemc.Pipeline.Connection.message.play.player;

import com.flowpowered.networking.Message;
import lombok.Data;

import java.util.List;

@Data
public final class TabCompleteResponseMessage implements Message {

    private final List<String> completions;

}

