/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the Open Website button is clicked. It will open the website
     * specified by the URL represented by the variable urlAsString using implicit Intents.
     *
     * @param v Button that was clicked.
     */
    public void onClickOpenWebpageButton(View v) {
        // TODO (5) Create a String that contains a URL ( make sure it starts with http:// or https:// )
        String mUrl = "https://youtube.pt";

        // TODO (6) Replace the Toast with a call to openWebPage, passing in the URL String from the previous step
        openWebPage(mUrl);
        //Toast.makeText(this, "TODO: Open a web page when this button is clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is called when the Open Location in Map button is clicked. It will open the
     * a map to the location represented by the variable addressString using implicit Intents.
     *
     * @param v Button that was clicked.
     */
    public void onClickOpenAddressButton(View v) {

        String address = "Rua Jose Falcao";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", address);
        Uri adressUri = builder.build();

        showMap(adressUri);

    }

    /**
     * This method is called when the Share Text Content button is clicked. It will simply share
     * the text contained within the String textThatYouWantToShare.
     *
     * @param v Button that was clicked.
     */
        public void onClickShareTextButton(View v) {
        String textThatYouWantToShare =
                "Sharing the coolest thing I've learned so far. You should " +
                        "check out Udacity and Google's Android Nanodegree!";

        shareText(textThatYouWantToShare);
    }

    /**
     * This is where you will create and fire off your own implicit Intent. Yours will be very
     * similar to what I've done above. You can view a list of implicit Intents on the Common
     * Intents page from the developer documentation.
     *
     * @see <http://developer.android.com/guide/components/intents-common.html/>
     *
     * @param v Button that was clicked.
     */
    public void createYourOwn(View v) {
        Toast.makeText(this,
                "TODO: Create Your Own Implicit Intent",
                Toast.LENGTH_SHORT)
                .show();
    }

    public void openWebPage(String urlString ){
        Uri uri = Uri.parse(urlString);

        Intent webViewIntent = new Intent();
        webViewIntent.setAction(Intent.ACTION_VIEW);
        webViewIntent.setData(uri);
        startActivity(webViewIntent);


    }

    public void showMap(Uri uri){
        Intent mapIntent = new Intent();
        mapIntent.setAction(Intent.ACTION_VIEW);
        mapIntent.setData(uri);
        startActivity(mapIntent);
    }

    public void shareText(String textToShare){
        String mimeType = "text/plain";
        String title = "Share Text";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

}