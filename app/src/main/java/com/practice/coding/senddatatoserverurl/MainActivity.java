package com.practice.coding.senddatatoserverurl;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressDialog progressDialog;
    ArrayList<Person> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvResult);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait . . .");
        progressDialog.setMessage("Sending data to the server. . .");

    }

    public void sendData(View view) {
        progressDialog.show();
        String url = "http://10.0.3.2:8088/APIs_Receiving_Data_From_Android_App/insert_single_record.php"; //10.0.3.2 : use for genymotion to access computer localhost
        //String url = "http://192.168.56.1:8088/APIs_Receiving_Data_From_Android_App/insert_single_record.php"; //192.168.56.1 computer localhost address ip
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                textView.setText("Server Response : "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                textView.setText("Error :" + error.getMessage());
                Log.d("MyTag", error+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("name", "Shahzaib");
                params.put("rollNo", "45");
                params.put("class", "Masters");

                return params;
            }
        };

        queue.add(request);
    }

    ////////////////////////////////////////////// Send ArrayList Data ////////////////////////////////////////////
    public void sendArrayListData(View view) {
        progressDialog.show();

        //Fill Data in Person Arraylist
        for(int i = 0; i<4; i++)
        {
            String name = "User : "+i;
            String rollNo = "RollNo : "+i;
            String className = "Masters";
            Person person = new Person(name, rollNo, className);

            arrayList.add(person);
        }

        //Convert Person Array to JsonObject
        Gson gson = new Gson();
        final String jsonObject = gson.toJson(arrayList);
/*
        //Convert the JsonObject to JsonArray
        JsonParser jsonParser = new JsonParser();
        final JsonArray myJsonArray = jsonParser.parse(jsonObject).getAsJsonArray();*/

        /*
        //Populate dummy data into arrayList
        for(int row = 0; row<arrayList.size(); row++)
        {
            for(int cols = 0; cols<2; cols++)
            {
                String name = "User : "+cols;
                String rollNo = "RollNo : "+cols;
                String className = "Masters";
                Person person = new Person(name, rollNo, className);
            }
        }
        */


        //Convert ArrayList data to jsonArray
        //final JSONArray myJsonArray = new JSONArray();
        /*
        for(Person person: arrayList)
        {
            myJsonArray.put(person.getName());
            myJsonArray.put(person.getRollNo());
            myJsonArray.put(person.getClassName());
        }
        */
        

        String url = "http://10.0.3.2:8088/APIs_Receiving_Data_From_Android_App/insert_array_data.php"; //10.0.3.2 : use for genymotion to access computer localhost
        //String url = "http://192.168.56.1:8088/APIs_Receiving_Data_From_Android_App/insert_array_data.php"; //192.168.56.1 computer localhost address ip
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                textView.setText("Server Response : "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                textView.setText("Error :" + error.getMessage());
                Log.d("MyTag", error+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //params.put("user_array", myJsonArray.toString());
                params.put("user_array", jsonObject);

                return params;
            }
        };

        queue.add(request);

    }
}

/*
    //PHP Script for sending Single user data from my android app to my localhost database
    //For inserting record into phpMyadmin ..database PHP Script or means the API the Receive data and insert to database

     <?php
	$servername = "localhost";
            $username = "root";
            $password = "";
            $dbname = "testdb";

            // Create connection
            $conn = new mysqli($servername, $username, $password, $dbname);
            // Check connection
            if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
            }else{
            echo "Connected Successfully!\n";
            }
            if($_SERVER['REQUEST_METHOD']=='POST')
            {
            $name = $_POST['name'];
            $rollNo = $_POST['rollNo'];
            $class = $_POST['class'];
            $sql = "INSERT INTO students (name, rollNo, class) VALUES ('$name', '$rollNo', '$class');";

            if ($conn->query($sql) === TRUE) {
            echo "New record inserted successfully";
            } else {
            echo "Record Not Inserted. \n Error: " . $sql . "<br>" . $conn->error;
            }
            }else{
            echo "Please send data with POST Request!";
            }
            $conn->close();
            ?>

  */
