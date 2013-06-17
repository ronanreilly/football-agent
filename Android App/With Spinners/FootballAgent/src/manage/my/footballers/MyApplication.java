// Ronan Reilly 2012
package manage.my.footballers;

import android.app.Application;

public class MyApplication extends Application{
	private PlayersContainer myContainer;
	
	public MyApplication(){
		this.myContainer = null;
	}
	
	public PlayersContainer getPlayersContainer() {
		return this.myContainer;
	}
	
	public void setPlayersContainer(PlayersContainer pc){
		this.myContainer = pc;
	}
}
