// Ronan Reilly 2012
package manage.my.footballers;

import java.util.ArrayList;
import java.util.List;

public class PlayersContainer {
	
	private List<Player> players;
	
	public PlayersContainer(){
		this.players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player p){
		this.players.add(p);
	}
	
	public boolean removePlayer(Player p){
		return this.players.remove(p);
	}
	
	public List<Player> getPlayers(){
		return this.players;
	}

}
