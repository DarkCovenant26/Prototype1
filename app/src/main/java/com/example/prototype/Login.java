package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    Button btn_login;
    EditText etDrivername,etWork_order;
    String stus,stpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btnLogin);
        etDrivername = findViewById(R.id.etDrivername);
        etWork_order = findViewById(R.id.etWork_order);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stpd=etWork_order.getText().toString();
                login();
            }
        });
    }

    public void login(){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.116/TrackerDB/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.contains("1")){
                            /*startActivity(new Intent(getApplicationContext(), MainActivity.class));*/
                            Intent z = new Intent(Login.this, MainActivity.class);
                            z.putExtra("Value2",stpd);
                            startActivity(z);
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid credential",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("driver_name", etDrivername.getText().toString());
                params.put("work_order", etWork_order.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
