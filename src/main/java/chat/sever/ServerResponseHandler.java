package chat.sever;

import chat.client.models.ClientRequest;
import chat.client.models.Message;
import chat.client.models.User;

// TODO: /info command to see information about an user

public class ServerResponseHandler {
    private final User user;

    public ServerResponseHandler(User user) {
        this.user = user;
    }

    public void handleResponse(Object receivedObject) {
        if (!shouldBeSentPrivately(receivedObject)) {
            ServerManager.broadcastMessage(receivedObject);
        } else {
            System.out.println("HERE");
            ServerManager.broadcastMessage(objectToSend(receivedObject), user);
        }
    }

    private boolean shouldBeSentPrivately(Object receivedObject) {
        return receivedObject instanceof ClientRequest;
    }

    private Object objectToSend(Object object) {
        ClientRequest request = (ClientRequest) object;

        return switch (request.getRequestType()) {
            case VIEW_INFO -> getInfo(user);
            case VIEW_MEMBERS -> getMembers();
            default -> throw new IllegalStateException("Unexpected value: " + request.getRequestType());
        };
    }

    private Message getMembers() {
        StringBuilder sb = new StringBuilder("Members:\n");
        for (User u : ServerManager.getUsers()) {
            sb.append(" - ").append(u.getUsername()).append("\n");
        }
        String text = sb.substring(0, sb.length() - 2);
        return new Message(text);
    }

    private Message getInfo(User user) {
        String text = "Information " + user.getUsername() + ":\n" + user;
        return new Message(text);
    }


}
