package com.example.wonderweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    Integer t,pe,hu;
    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    EditText et;
    TextView tv,p,h,d;
    String url="api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey="50bf2bafdb0a25ffa38debe39ac247fc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
        p=findViewById(R.id.p);
        h=findViewById(R.id.h);
        d=findViewById(R.id.d);

        relativeLayout=(RelativeLayout)findViewById(R.id.activity_home);
        btnLogout = findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        if (savedInstanceState != null) {
            t = savedInstanceState.getInt("temperature");
            tv.setText("temperature "+String.valueOf(t)+"°C");

            pe = savedInstanceState.getInt("pressure");
            p.setText("atmospheric pressure "+String.valueOf(pe)+"mb");

            hu = savedInstanceState.getInt("humidity");
            h.setText("humidity "+String.valueOf(hu)+"g.kg-1 ");
        }


    }
    public void get(View v){
        String apikey="50bf2bafdb0a25ffa38debe39ac247fc";
        String city=et.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=50bf2bafdb0a25ffa38debe39ac247fc";
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                String main="";
                String des="";

                try {
                    JSONObject object=response.getJSONObject("main");

                    String temperature=object.getString("temp");
                    double te=Double.parseDouble(temperature);
                    t=(int)(te-273.15);
                    tv.setText("temperature "+String.valueOf(t)+"°C");

                    String pressure=object.getString("pressure");
                    pe=Integer.parseInt(pressure);
                    p.setText("atmospheric pressure "+String.valueOf(pe)+"mb");

                    String humidity=object.getString("humidity");
                    hu=Integer.parseInt(humidity);
                    h.setText("humidity "+String.valueOf(hu)+"g.kg-1");

                    JSONArray ob=response.getJSONArray("weather");

                    JSONObject weather_data=ob.getJSONObject(0);
                    main=weather_data.getString("main");
                    des=weather_data.getString("description");

                    d.setText("weather condition : "+String.valueOf(main)+" - "+String.valueOf(des));

                    if(main.equals("Thunderstorm")){

                        relativeLayout.setBackgroundResource(R.drawable.thunderstorm_a);

                    }
                    else if(main.equals("Drizzle")){
                        relativeLayout.setBackgroundResource(R.drawable.drizzle_a);
                    }
                    else if(main.equals("Rain")){
                        relativeLayout.setBackgroundResource(R.drawable.rainy);
                    }
                    else if(main.equals("Snow")){
                        relativeLayout.setBackgroundResource(R.drawable.snow);
                    }
                    else if(main.equals("Mist")){
                        relativeLayout.setBackgroundResource(R.drawable.mist);
                    }
                    else if(main.equals("Smoke")){
                        relativeLayout.setBackgroundResource(R.drawable.smoke);
                    }
                    else if(main.equals("Haze")){
                        relativeLayout.setBackgroundResource(R.drawable.haze);
                    }
                    else if(main.equals("Dust")){
                        relativeLayout.setBackgroundResource(R.drawable.dust);
                    }
                    else if(main.equals("Fog")){
                        relativeLayout.setBackgroundResource(R.drawable.fog_a);
                    }
                    else if(main.equals("Sand")){
                        relativeLayout.setBackgroundResource(R.drawable.sand);
                    }
                    else if(main.equals("Dust")){
                        relativeLayout.setBackgroundResource(R.drawable.dust);
                    }
                    else if(main.equals("Sand")){
                        relativeLayout.setBackgroundResource(R.drawable.sand);
                    }
                    else if(main.equals("Ash")){
                        relativeLayout.setBackgroundResource(R.drawable.ash_a);
                    }
                    else if(main.equals("Squall")){
                        relativeLayout.setBackgroundResource(R.drawable.squall);
                    }
                    else if(main.equals("Tornado")){
                        relativeLayout.setBackgroundResource(R.drawable.tornado_a);
                    }
                    else if(main.equals("Clear")){
                        relativeLayout.setBackgroundResource(R.drawable.clearsky);
                    }
                    else if(main.equals("Clouds")){
                        relativeLayout.setBackgroundResource(R.drawable.clouds_a);
                    }
                    else{
                        relativeLayout.setBackgroundResource(R.drawable.hi);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,"Please Enter a valid City",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("temperature",t);
        outState.putInt("pressure",pe);
        outState.putInt("humidity",hu);
    }
}