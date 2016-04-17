package pokker.client;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageType;
import pokker.lib.messages.Request;

import java.io.IOException;
import java.util.List;

public class Game {
    private ServerConnection connection;
    private List<TableClient> tables;
    private final String playerName;

    Game(String playerName) {
        this.playerName = playerName;
    }

    public void connect(String ip, int port) throws IOException {
        disconnect();
        connection = new ServerConnection(this, ip, port);
        sendUserData();
    }

    private void sendUserData() {
        connection.sendMessage(new Message(MessageType.UserData, playerName));
    }

    public void updateTables() {
        connection.sendMessageAndWaitForResponseType(new Request(MessageType.GetTableList), MessageType.TableList);
    }

    public boolean joinTable(int tableId) {
        updateTables();
        if (connection.getGame().getTables().get(tableId).getPlayers().size() >= connection.getGame().getTables().get(tableId).getTableSize()) {
            return false;
        }
        connection.sendMessageAndWaitForResponseType(new Message(MessageType.JoinTable, tableId), MessageType.SuccessfulTableJoin);
        return true;
    }

    public List<TableClient> getTables() {
        return tables;
    }

    public synchronized void setTables(List<TableClient> tables) {
        this.tables = tables;
    }

    public void disconnect() {
        if (connection != null) {
            connection.close();
        }
    }
}
