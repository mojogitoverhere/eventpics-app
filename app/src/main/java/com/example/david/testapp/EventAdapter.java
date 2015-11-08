package com.example.david.testapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 11/3/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private static final String get_events_url = "http://192.168.0.12:3000/events?username=";

    List<Event> data = null;
    String username;
    Context context;

    public EventAdapter(String username, Context context) {
        this.username = username;
        this.context = context;
        JsonArrayRequest event_list_request = new JsonArrayRequest(
                get_events_url + username,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TEST", "received response");
                        List<Event> event_list = new ArrayList<Event>();
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                event_list.add(Event.fromJson((JSONObject)response.get(i)));
                            } catch (JSONException e) {
                                Log.e("JSON EXCEPTION ERROR", e.getMessage());
                            } catch (ParseException e) {
                                Log.e("DATE PARSE ERROR", e.getMessage());
                            }
                        }
                        data = event_list;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TEST", "Volley error: " + error.getMessage());
                        System.out.print("Volley Error");
                    }
                }
        );
        AppData.getInstance(context).addToRequestQueue(event_list_request);
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View event_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventHolder(event_item);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        final int pos = position;
        holder.event_title.setText(this.data.get(position).name);
        holder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_photo_intent = new Intent(v.getContext(), ViewPhotosActivity.class);
                view_photo_intent.putExtra("Event Data", data.get(pos));
                v.getContext().startActivity(view_photo_intent);
                }});
        }

    @Override
    public int getItemCount() {
        if (this.data == null) return 0;
        return this.data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        View item_view;
        TextView event_title;

        public EventHolder(View event_view) {
            super(event_view);
            item_view = event_view;
            event_title = (TextView)event_view.findViewById(R.id.title);
        }
    }

    public static class ItemDivider extends RecyclerView.ItemDecoration {

        public static final int[] ATTRIBUTES = {android.R.attr.listDivider};
        public Drawable divider;

        public ItemDivider(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRIBUTES);
            this.divider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for(int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + this.divider.getIntrinsicHeight();

                this.divider.setBounds(left, top, right, bottom);
                this.divider.draw(c);
            }
        }
    }

}
