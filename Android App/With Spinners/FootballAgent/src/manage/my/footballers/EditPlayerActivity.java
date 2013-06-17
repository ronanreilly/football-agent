// Ronan Reilly 2012
package manage.my.footballers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditPlayerActivity extends Activity{
	
	MyApplication myApp;
	
	private Player player;
	private TextView formTitle;
	private EditText team;
	private EditText firstName;
	private EditText lastName;
	private EditText age;
	private EditText country_origin;
	
	// RADIO BUTTON VARIABLES //
	// RADIO BUTTON VARIABLES //
	private String pref_foot;
	private RadioButton mLeft_Foot;
	private RadioButton mRight_Foot;
	
	// SPINNER VARIABLES //
	// SPINNER VARIABLES //
	private Spinner positionSelecter;
	private String position;
	
	
	
	
	private Button confirm;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.myApp = (MyApplication)this.getApplication();
        
		Integer position;
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
			position = extras.getInt("position");
		}
		else {
			position = null;
		}
        
        this.setContentView(R.layout.players_form);
        
        this.formTitle = (TextView)this.findViewById(R.id.form_title);
        this.team = (EditText)this.findViewById(R.id.team);
        this.firstName = (EditText)this.findViewById(R.id.firstName);
        this.lastName = (EditText)this.findViewById(R.id.lastName);
        this.age = (EditText)this.findViewById(R.id.age);
        this.country_origin = (EditText)this.findViewById(R.id.country_origin);
        
        
    	// RADIO BUTTON SETTING VIEW //
    	// RADIO BUTTON SETTING VIEW //
        this.mLeft_Foot = (RadioButton) findViewById(R.id.radio_left);
		this.mRight_Foot = (RadioButton) findViewById(R.id.radio_right);
		mLeft_Foot.setOnClickListener(radio_listener);
		mRight_Foot.setOnClickListener(radio_listener);
		
		// SPINNER SETTING VIEW //
    	// SPINNER SETTING VIEW //
		positionSelecter = (Spinner) findViewById(R.id.position);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.position_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSelecter.setAdapter(adapter);
        positionSelecter.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
        this.confirm = (Button)this.findViewById(R.id.confirm);

        if (position == null) {
        	this.formTitle.setText("Create Player");
        	this.confirm.setText("Create Player");
        	this.player = null;
        }
        else {
        	this.formTitle.setText("Edit Player");
        	this.confirm.setText("Update Player");
        	this.player = this.myApp.getPlayersContainer().getPlayers().get(position.intValue());
        	this.team.setText(this.player.team);
        	this.firstName.setText(this.player.firstName);
        	this.lastName.setText(this.player.lastName);
        	this.age.setText(this.player.age);
        	this.country_origin.setText(this.player.country_origin);
        	//this.position.(this.player.position);       	
        	
        	// RADIO BUTTON TOGGLE //
    		// RADIO BUTTON TOGGLE //
        	if (this.pref_foot.equalsIgnoreCase("Left Foot")) {
        		mLeft_Foot.toggle();
        		pref_foot = setText(this.player.pref_foot);
    		}
    		if (this.pref_foot.equalsIgnoreCase("Right Foot")) {
    			mRight_Foot.toggle();
    			pref_foot = setText(this.player.pref_foot);
    		}
        }
	}
	
	private String setText(String pref_foot2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// RADIO LISTENER //
	// RADIO LISTENER //
	private OnClickListener radio_listener = new OnClickListener() {
		public void onClick(View v) {
			RadioButton rb = (RadioButton) v;
			pref_foot = rb.getText().toString();		
		}
	};
	
	 // SPINNER LISTENER //
	 // SPINNER LISTENER //
	 public class MyOnItemSelectedListener implements OnItemSelectedListener {

	        public void onItemSelected(AdapterView<?> parent,
	            View view, int pos, long id) {
	          Toast.makeText(parent.getContext(), "The position is " +
	              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	        }

	        public void onNothingSelected(AdapterView parent) {
	          // Do nothing.
	        }
	    }
	
	
	
	public void onConfirm(View view){
		if(this.player == null){
			this.player = new Player();
			this.player.id = -1;
		}
		
		this.player.team = this.team.getText().toString();
		this.player.firstName = this.firstName.getText().toString();
		this.player.lastName = this.lastName.getText().toString();
		this.player.age = this.age.getText().toString();
		this.player.country_origin = this.country_origin.getText().toString();
		//this.player.position = this.position.getText().toString();
		
		// RADIO BUTTON TEXT //
		// RADIO BUTTON TEXT //
		this.player.pref_foot = this.pref_foot.toString();
		
		UploadPlayerTask task = new UploadPlayerTask();
		task.execute(this.player);
	}
	
	private class UploadPlayerTask extends AsyncTask<Player, Void, String>{
		private Player myPlayer;
		@Override
		protected String doInBackground(Player... players){
			String result;
			this.myPlayer = players[0];
			result = uploadPlayer(this.myPlayer);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result){
			if(result.startsWith("OK")){
				if(this.myPlayer.id ==-1){
					this.myPlayer.id = Integer.parseInt(result.substring(3));
					EditPlayerActivity.this.myApp.getPlayersContainer().addPlayer(this.myPlayer);
				}
				EditPlayerActivity.this.setResult(RESULT_OK);
				EditPlayerActivity.this.finish();
			}
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(EditPlayerActivity.this);
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
	
	private String uploadPlayer(Player myPlayer){
		DefaultHttpClient client = new DefaultHttpClient();
		String result = null;
		
		HttpPost httpPost;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		if(myPlayer.id == -1){
			httpPost = new HttpPost("http://192.168.15.7/~ronanseanreilly/footballPlayers/xml/create_player.php");
		}
		else{
			httpPost = new HttpPost("http://192.168.15.7/~ronanseanreilly/footballPlayers/xml/edit_player.php");
			nvps.add(new BasicNameValuePair("player_id", ""+myPlayer.id));
		}
		
		nvps.add(new BasicNameValuePair("team", myPlayer.team));
		nvps.add(new BasicNameValuePair("firstName", myPlayer.firstName));
		nvps.add(new BasicNameValuePair("lastName", myPlayer.lastName));
		nvps.add(new BasicNameValuePair("age", myPlayer.age));
		nvps.add(new BasicNameValuePair("country_origin", myPlayer.country_origin));
		nvps.add(new BasicNameValuePair("position", myPlayer.position));
		nvps.add(new BasicNameValuePair("pref_foot", myPlayer.pref_foot));
		
		try{
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
			httpPost.setEntity(entity);
			
			HttpResponse response = client.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			HttpResponseHandler myResponseHandler = new HttpResponseHandler();
			xr.setContentHandler(myResponseHandler);
			xr.parse(retrieveInputStream(responseEntity));
			result = myResponseHandler.getStatus();
		}
		catch(Exception e){
			result = "Exception - " + e.getMessage();
		}
		return result;
	}
	
	private InputSource retrieveInputStream(HttpEntity httpEntity){
		InputSource insrc = null;
		try {
			insrc = new InputSource(httpEntity.getContent());
		}
		catch (Exception e){
			
		}
		return insrc;
	}	
}
