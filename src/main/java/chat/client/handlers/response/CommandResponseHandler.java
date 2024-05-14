package chat.client.handlers.response;

import chat.client.models.User;
import chat.client.ui.ChatLayout;
import chat.sever.CommandResponse;
import chat.utils.StatusCode;

import javax.crypto.SecretKey;
import java.awt.*;

public class CommandResponseHandler extends ResponseHandler<CommandResponse> {

    public CommandResponseHandler(ChatLayout chatLayout, User user, SecretKey secretKey) {
        super(chatLayout, user, secretKey);
    }

    @Override
    void handleResult(CommandResponse commandResponse) {
        String text = commandResponse.getDisplayMessage();
        if(commandResponse.getStatusCode() == StatusCode.ACCEPTED) {
            chatLayout.updateChatArea(text, Color.GREEN);
        } else {
            chatLayout.updateChatArea(text, Color.RED);
        }
    }
}
