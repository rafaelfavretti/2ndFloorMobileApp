package com.secondfloor.ofertame.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainActivity extends ActionBarActivity {

	public static final String PRF_USER = "login";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
	    final EditText etUserName = (EditText) findViewById(R.id.etUserName);
	    final EditText etPass = (EditText) findViewById(R.id.etPass);
	       
	       //Recuperando o Usuário cadastrado..... para entrar
	       SharedPreferences login = getSharedPreferences(PRF_USER, MODE_PRIVATE);
	       
	       //Captura as informações inseridas pelo usuario e salva dentro do login
	       final String userNameSave = login.getString("userName", "");
	       final String passSave = login.getString("password", "");
	       
	       btnEntrar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					String user = etUserName.getText().toString();
					String pass = etPass.getText().toString();
					
					if(user.equals("") || pass.equals("")){
						Toast.makeText(getApplicationContext(), "Nenhum campo deve estar vazio", Toast.LENGTH_LONG).show();	
					
					}else if(user.equals(userNameSave) & pass.equals(passSave)){
						//troca de tela
						Toast.makeText(getApplicationContext(), "Bem vindo de volta " +user, Toast.LENGTH_LONG).show();
					
					}else{
						//troca de tela
						Toast.makeText(getApplicationContext(), "Usuário ou senha errado ", Toast.LENGTH_LONG).show();
				
					}
				}
			});
		
		
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
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
		if(Session.getActiveSession().isOpened()){
			Session.getActiveSession().closeAndClearTokenInformation();
		}else{
			Toast.makeText(getApplicationContext(), "Não há conexão ativa", Toast.LENGTH_LONG).show();
		}
	}
	
	public void redirecionaCriarConta(){
	
		Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
		startActivity(intent);
	}
}
