// Ronan Reilly 2012
package manage.my.footballers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class ListMyPlayers extends ListActivity {
	private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    
    private MyApplication myApp;
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, PlayersContainer> {

		@Override
		protected PlayersContainer doInBackground(String... urls) {
			PlayersContainer result;
			String myUrl = urls[0];
			result = getPlayersListFromServer(myUrl);
			return result;
		}
		
		@Override
		protected void onPostExecute(PlayersContainer result) {
			ListMyPlayers.this.myApp = (MyApplication)ListMyPlayers.this.getApplication();
			ListMyPlayers.this.myApp.setPlayersContainer(result);
			ListMyPlayers.this.fillData(result.getPlayers());
		}
	}
	
	private class DeletePlayerTask extends AsyncTask<Player, Void, String> {
		private Player myPlayer;
		@Override
		protected String doInBackground(Player... players) {
			String result;
			this.myPlayer = players[0];
			result = deletePlayerFromServer(this.myPlayer);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result.equalsIgnoreCase("OK")) {
				PlayersContainer pc = ListMyPlayers.this.myApp.getPlayersContainer();
				pc.removePlayer(this.myPlayer);
				ListMyPlayers.this.fillData(pc.getPlayers());
			}
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(ListMyPlayers.this);
				builder.setMessage("Error/Exception: " + result)
				       .setCancelable(false)
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.myApp = (MyApplication)this.getApplication();
        
        setContentView(R.layout.main);
        setListAdapter(new PlayerListAdapter(this, null));
        registerForContextMenu(getListView());
        
        DownloadWebPageTask myBackgroundTask = new DownloadWebPageTask();
        myBackgroundTask.execute("http://192.168.15.7/~ronanseanreilly/footballPlayers/xml/view_players.php");   
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                createPlayer();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                Player p = this.myApp.getPlayersContainer().getPlayers().get(info.position);
                deletePlayer(p);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void deletePlayer(Player p) {
    	DeletePlayerTask deleteTask = new DeletePlayerTask();
    	deleteTask.execute(p);
	}

	private void createPlayer() {
        Intent i = new Intent(this, EditPlayerActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, EditPlayerActivity.class);
        i.putExtra("position", position);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    

	public void fillData(List<Player> players) {
		this.setListAdapter(new PlayerListAdapter(ListMyPlayers.this, players));
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        
        this.fillData(this.myApp.getPlayersContainer().getPlayers());
    }

	private PlayersContainer getPlayersListFromServer(String urlString) {
		PlayersContainer result = null;
		try {
            URL url = new URL(urlString);

            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            MyXMLHandler myXMLHandler = new MyXMLHandler();
            xr.setContentHandler(myXMLHandler);
            xr.parse(new InputSource(is));
            result = myXMLHandler.getPlayersContainer();

	    } catch (IOException e) {
	    	Log.v("ListPlayersActivity", "Error downloading player list: " + e.getMessage());
	    }
		catch (SAXException e) {			
	    	Log.v("ListPlayersActivity", "Error parsing book player: " + e.getMessage());
		}
		catch (ParserConfigurationException e) {
	    	Log.v("ListPlayersActivity", "Error building XML player: " + e.getMessage());
		}
		return result;
	}
	
    public String deletePlayerFromServer(Player player) {
    	String urlString = "http://192.168.15.7/~ronanseanreilly/footballPlayers/xml/delete_player.php?player_id=" + 
    						player.id;
    	String result = null;
    	try {
			URL url = new URL(urlString);
			
			/* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            
            /** Handling XML */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            /** Create handler to handle XML Tags ( extends DefaultHandler ) */
            HttpResponseHandler myHandler = new HttpResponseHandler();
            xr.setContentHandler(myHandler);
            xr.parse(new InputSource(is));
            result = myHandler.getStatus();
		} 
    	catch (MalformedURLException e) {
			Log.v("ListBooksActivity", "Error deleting player - Malformed URL exception: " + e.getMessage());
		} 
    	catch (IOException e) {
			Log.v("ListBooksActivity", "Error deleting player - IO Exception: " + e.getMessage());
		} 
    	catch (ParserConfigurationException e) {
			Log.v("ListBooksActivity", "Error deleting player - Parser Configuration Error: " + e.getMessage());
		} 
    	catch (SAXException e) {
			Log.v("ListBooksActivity", "Error deleting player - SAX Exception: " + e.getMessage());
		}
		return result;
	}
}