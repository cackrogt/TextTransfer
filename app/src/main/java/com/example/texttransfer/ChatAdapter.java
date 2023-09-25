package com.example.texttransfer;

import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        EditText mText1;
        EditText mText2;
        TextView mTextRes;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.button1);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mText1 = (EditText) itemView.findViewById(R.id.inputView1);
                    mText2 = (EditText) itemView.findViewById(R.id.inputView2);
                    mText2.setMovementMethod(new ScrollingMovementMethod());
                    mTextRes = (TextView) itemView.findViewById(R.id.textView);
                    mTextRes.setMovementMethod(new ScrollingMovementMethod());
                    if(mText2.getText().toString().isEmpty()){
                        Toast.makeText(view.getContext(), "EnterProperInputs man", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try{
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("prompt", mText2.getText().toString());/*
                            jsonObject.put("system", "Always talk in English");
                            jsonObject.put("withoutContext", true);
                            jsonObject.put("stream", false);*/
                            String jsonString = jsonObject.toString();
                            new PostData().execute(jsonString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        public void updateTextView(String responseLine){
            TextView mTextRes = (TextView) itemView.findViewById(R.id.textView);
            mTextRes.setText(responseLine);
            mTextRes.invalidate();
        }

        class PostData extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                try {

                    // on below line creating a url to post the data.
                    URL url = new URL("https://us-central1-arched-keyword-306918.cloudfunctions.net/run-inference-1");

                    // on below line opening the connection.
                    HttpURLConnection client = (HttpURLConnection) url.openConnection();

                    // on below line setting method as post.
                    client.setRequestMethod("POST");

                    // on below line setting content type and accept type.
                    client.setRequestProperty("Content-Type", "application/json");
                    client.setRequestProperty("Accept", "application/json");

                    // on below line setting client.
                    client.setDoOutput(true);

                    // on below line we are creating an output stream and posting the data.
                    try (OutputStream os = client.getOutputStream()) {
                        byte[] input = strings[0].getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // on below line creating and initializing buffer reader.
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(client.getInputStream(), "utf-8"))) {

                        // on below line creating a string builder.
                        StringBuilder response = new StringBuilder();

                        // on below line creating a variable for response line.
                        String responseLine = null;

                        // on below line writing the response
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        updateTextView(response.toString());
                        // on below line displaying a toast message.
                        //Toast.makeText(itemView.getContext(), "Data has been posted to the API.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    // on below line handling the exception.
                    e.printStackTrace();
                    //Toast.makeText(itemView.getContext(), "Fail to post the data : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        }
    }



    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
