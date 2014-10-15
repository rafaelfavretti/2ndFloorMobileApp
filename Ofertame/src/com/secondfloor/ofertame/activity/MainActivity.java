package com.secondfloor.ofertame.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainActivity extends Activity implements OnClickListener {

	Button btnSignIn;
	Button btnSignUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSignIn = (Button) findViewById(R.id.btnSingIn);
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.deslogar){
			deslogar();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch(v.getId()){
			case R.id.btnSingIn:
				i = new Intent(this,SignInActivity.class);
				break;
			case R.id.btnSignUp:
				i = new Intent(this,SignUpActivity.class);
				break;
		}
		startActivity(i);
	}
	
	public void loginFacebook(View entrada){
		//Start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			
		// callback when session changes state
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			if (session.isOpened()) {
			Request.newMeRequest(session, new Request.GraphUserCallback() {
			
				// callback after Graph API response with user
				// object
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
					 TextView welcome = (TextView) findViewById(R.id.entrada);
					 welcome.setText("Seja Bem-Vindo " + user.getName() + "!");
					}
				}
			}).executeAsync();
							
			//Seto um timer para redirecionamento
				int timeout = 5000;
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
	
					@Override
					public void run() {
						finish();
						//Redireciono para a Activity de busca de produto
						Intent intent = new Intent(MainActivity.this, BuscaProdutoActivity.class);
						startActivity(intent);
					}
				}, timeout);
							
			}
		}
		});
	}

	public void deslogar(){
		Session.getActiveSession().closeAndClearTokenInformation();
	}
}