package com.textview_contact;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import WebServices.WebServices;
import WebServices.Asynchtask;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_result = (TextView)findViewById(R.id.txtResult);
        txt_result.setMovementMethod(new ScrollingMovementMethod());
        Bundle  bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebServices ws= new WebServices("https://api.androidhive.info/contacts/", datos, MainActivity.this, MainActivity.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish", result);
        //txtresultado.setText(result);
        JSONObject jsonObj = new JSONObject(result);
        JSONArray contacts = jsonObj.getJSONArray("contacts");
        String contactos = "<ul>";
        for (int i = 0; i<contacts.length(); i++ )
        {
            JSONObject c = contacts.getJSONObject(i);
            contactos += "<li> ID: " + c.getString("id")  +"\r\n Nombre: " + c.getString("name")+ "\r\n Email: "+ c.getString("email")+ "\r\n Direccion: "+ c.getString("address")+ "\r\n Genero: "+ c.getString("gender")+"</li>";

             JSONObject phone = c.getJSONObject("phone");
            contactos += "<li>Mobile: " + phone.getString("mobile")+ "\r\n Home: "+ phone.getString("home")+ "\r\n Office: " +
                    ""+ phone.getString("office")+"</li>";
        }
        //txtresultado.setText(contactos);
        contactos += "</ul>";
        txt_result.setText(Html.fromHtml(contactos));
    }


}
