package com.example.apppython;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class About_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        String desc="I am Essaddiq Lakhlifi, from Morocco, I have a Bac + 5 diploma in Intelligent Systems and Mobile , a professional license in IT and Decisional Systems Engineering and DUT in Systems and Networks Administrator.\n" +
                "\n" +
                "For computer development, (JAVA / JavaEE-Spring), (c #), (PHP / laravel) (python / django) cms wordpress, administration of computer networks, and I have knowledge in computer management (ERP / PGI: Cegid ).\n" +
                "The team of develepement is\n" ;
        Element versionElement = new Element();
        versionElement.setTitle("Version 0.2");
        versionElement.setIconDrawable(R.drawable.ic_baseline_developer_mode_24);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .addItem(versionElement)

                .addGroup("Connect with us")
                .addEmail("sim.fpt@usmba.ac.ma","Email")
                .setDescription(desc)
                .addWebsite("http://lakhlifisys.wordpress.com/","Website")
                .addFacebook("http://lakhlifisys.wordpress.com/")
                .addTwitter("http://lakhlifisys.wordpress.com/")
                .addYoutube("UCvBNh_qi1PrX4Y8kDKrkOgQ")
//                    .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("lakhlifi-sdk")
                //        .addInstagram("https://www.instagram.com/ouchatti_khadija/")

                .create();

        LinearLayout Linear_about =(LinearLayout)findViewById(R.id.aboutus);
        Linear_about.addView(aboutPage);
    }
}


