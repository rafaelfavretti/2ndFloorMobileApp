package com.secondfloor.ofertame.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;

public class SignUpActivity extends Activity {

	
	public static final String PRF_USER = "login";
	
	Button btnCriar;
	EditText etEmail, etUserName, etPass;
	
	boolean verificaUsuarioCadastrado = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        
        btnCriar = (Button) findViewById(R.id.btnSingUp);
        
        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        
        //Login que ficara armazenado o Usu�rio do cara em cache... seu tempo de vida � em quanto sistema estiver instalado no celular...
        SharedPreferences login = getSharedPreferences(PRF_USER, MODE_PRIVATE);
        
        //Captura as informa��es inseridas pelo usuario e salva dentro do login
        final String userNameSave = login.getString("userName", "");
        final String emailSave = login.getString("email", "");
        final String passSave = login.getString("password", "");
        
        if(userNameSave.equals("")){
        	AlertDialog.Builder alerta = new AlertDialog.Builder(SignUpActivity.this);
        	alerta.setTitle("Alerta");
        	alerta.setMessage("Para utilizar o aplicativo � necess�rio criar um Usuario..");
        	alerta.setNeutralButton("Ok", null);
			alerta.show();
        }else{
			verificaUsuarioCadastrado = true;
        } 
        
        btnCriar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String user = etUserName.getText().toString();
				String email = etEmail.getText().toString();
				String pass = etPass.getText().toString();
				
				if(verificaUsuarioCadastrado == false){
					SharedPreferences login = getSharedPreferences(PRF_USER, 0);
					SharedPreferences.Editor editor = login.edit();
					editor.putString("userName", user);
					editor.putString("email", email);
					editor.putString("password", pass);
					editor.commit();
					
					Toast.makeText(getApplicationContext(), "Usu�rio " +user+ " E-mail " +email+ " Criado com sucesso", Toast.LENGTH_LONG).show();
					
					//troca de tela...
					
				}else if(user.equals(userNameSave) & email.equals(emailSave) & pass.equals(passSave)){
					Toast.makeText(getApplicationContext(), "Usu�rio j� cadastrado ", Toast.LENGTH_LONG).show();
				}
			}
		});
    } 
    
    @Override
	protected void onStop() {
		super.onStop();
		//Garente que quando o usu�rio clicar no bot�o voltar, 
		//o sistema deve finalizar sem passar pela tela de login
		finish();
	}
    
}
