// Ronan Reilly 2012
package manage.my.footballers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayerListAdapter extends ArrayAdapter<Player> {
	
	private List <Player> myPlayers;
	
	public PlayerListAdapter(Context context, List<Player> objects){
		super(context, R.layout.player_list_row);
		
		myPlayers = objects;
	}
	
	public int getCount(){
		if(myPlayers == null) return 0;
		else return myPlayers.size();
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == convertView){
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.player_list_row,null);
		}
		
		TextView teamView = (TextView)v.findViewById(R.id.team);
		TextView firstNameView = (TextView)v.findViewById(R.id.firstName);
		TextView lastNameView = (TextView)v.findViewById(R.id.lastName);
		TextView ageView = (TextView)v.findViewById(R.id.age);
		TextView country_originView = (TextView)v.findViewById(R.id.country_origin);
		TextView positionView = (TextView)v.findViewById(R.id.position);
		TextView pref_footView = (TextView)v.findViewById(R.id.pref_foot);
		
		Player player = myPlayers.get(position);
		
		teamView.setText("Team:" + " " + player.team);
		firstNameView.setText("First Name:" + " " + player.firstName);
		lastNameView.setText("Last Name:" + " " + player.lastName);
		ageView.setText("Age:" + " " + player.age);
		country_originView.setText("Nationality:" + " " + player.country_origin);
		positionView.setText("Position:" + " " + player.position);
		pref_footView.setText("Preferred Foot:" + " " + player.pref_foot);
		
		return v;
		
	}
}
