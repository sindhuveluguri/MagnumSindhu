package com.example.magnumsindhu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ArticlesAdaptor.OnItemClickListener{

    private RecyclerView recyclerView;
    private ArticlesAdaptor adaptor;
    private ArrayList<DataList> dataLists;
    private RequestQueue mrequestqueue;
    private ProgressBar progressBar;
    private JSONArray jsonArray;
    String loginName, loginid, avatar, url;

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_LOGIN = "LoginName";
    public static final String EXTRA_ID = "LoginId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview);
        final LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        dataLists = new ArrayList<>();

        mrequestqueue = Volley.newRequestQueue(this);
        adaptor = new ArticlesAdaptor(MainActivity.this, dataLists);
        recyclerView.setAdapter(adaptor);
        getData();
        adaptor.setOnItemClickListener(MainActivity.this);

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String urlPage = "https://api.github.com/search/users?q=saransh&page=2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPage, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);

                                loginName = item.getString("login");
                                loginid = item.getString("id");
                                avatar = item.getString("avatar_url");
                                url = item.getString("url");

                                dataLists.add(new DataList(loginName, loginid, avatar));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adaptor.setData(dataLists);
                        adaptor.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Volley", error.toString());
            }
        });
        mrequestqueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, ProfileActivity.class);
        DataList clickedItem = dataLists.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImage());
        detailIntent.putExtra(EXTRA_LOGIN, clickedItem.getLoginName());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getLoginId());
        startActivity(detailIntent);
    }

    //SearchBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptor.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
