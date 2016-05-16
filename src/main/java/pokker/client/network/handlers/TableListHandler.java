package pokker.client.network.handlers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import pokker.client.game.PlayerInstanceCreator;
import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.game.player.Player;
import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Handles the message when a list of tables is received
 */
public class TableListHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        Type tablesType = new TypeToken<List<TableClient>>() {
        }.getType();
        List<TableClient> tables = message.setGson(new GsonBuilder().registerTypeAdapter(Player.class, new PlayerInstanceCreator()).create()).bodyToObject(tablesType);
        connection.getGame().setTables(tables);
    }
}
