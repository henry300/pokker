package pokker.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pokker.client.game.TableClient;
import pokker.lib.game.player.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class Seat extends VBox {
    int seatNr;
    boolean active = false;
    Label nameLabel = new Label();
    Label moneyLabel = new Label("0€");
    PlayerChipsAndCardsHBox chipsAndCardsHBox;
    Player player = null;
    TableClient table;


    public Seat(int seatNr, int x, int y) {
        this.setSpacing(7);
        this.seatNr = seatNr;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.getStyleClass().add("unactiveSeat");
        this.setMaxSize(122, 52);
        this.setAlignment(Pos.BASELINE_CENTER);
        nameLabel.setTextFill(Color.WHITE);
        moneyLabel.setTextFill(Color.WHITE);
//        this.moneyRect.setHeight(40);
//        this.moneyRect.setWidth(40);
//        this.moneyRect.setFill(Color.RED);
//        this.moneyRect.setTranslateY(-100);
        this.getChildren().addAll(nameLabel, moneyLabel);
    }

    public void addChipsAndCardsHBox(PlayerChipsAndCardsHBox moneyRect, TableClient table) {
        this.chipsAndCardsHBox = moneyRect;
        this.table = table;
    }

    public void addPlayer(Player player) {
        this.player = player;
        this.setVisible(true);
        this.chipsAndCardsHBox.setVisible(true);
        updateMoneyLabel();
        updateNameLabel();
    }

    /**
     * 'Active' means that it is this player's turn
     *
     * @param active boolean true or false
     */
    public void setActive(boolean active) {
        if (active == true) {
            this.getStyleClass().remove("unactiveSeat");
            this.getStyleClass().add("activeSeat");
        } else {
            this.getStyleClass().remove("activeSeat");
            this.getStyleClass().add("unactiveSeat");
        }
        this.active = active;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public void updateMoneyLabel() {
        if (player != null) {
            NumberFormat nf = new DecimalFormat("##.##");
            moneyLabel.setText(nf.format(player.getMoney()) + "€");
        } else {
            moneyLabel.setText("-");
        }
    }

    public void updateNameLabel() {
        if (this.player != null) {
            nameLabel.setText(player.getName());
        } else {
            nameLabel.setText("No player");
        }
    }

    public void updateChipsAndCards() {
        boolean hasCards = table.areCardsDealt() && table.getPlayersInRound().contains(player);

        chipsAndCardsHBox.update(player.getMoney(), table, hasCards);
    }

    public void removePlayer() {
        this.player = null;
        this.setVisible(false);
        this.chipsAndCardsHBox.setVisible(false);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isActive() {
        return active;
    }
}