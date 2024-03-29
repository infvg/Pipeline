package org.pipelinemc.Pipeline.Connection.message.login;

import com.flowpowered.networking.Message;
import lombok.Data;

@Data
public final class LoginSuccessMessage implements Message {

    private final String uuid;
    private final String username;

}
