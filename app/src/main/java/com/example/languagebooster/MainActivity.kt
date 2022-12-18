package com.example.languagebooster

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagebooster.adaptors.RcyclerviewAdaptor
import com.example.languagebooster.db.helper.WordDBHelper
import com.example.languagebooster.models.Word
import soup.neumorphism.NeumorphFloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: NeumorphFloatingActionButton
    private lateinit var adaptor: RcyclerviewAdaptor

    private lateinit var dbHelper: WordDBHelper
    private lateinit var listWords: ArrayList<Word>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()


        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    1.toString(),
                    "First usage",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent: Intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val notification: Notification.Builder = Notification.Builder(this, 1.toString())
            .setContentTitle(resources.getString(R.string.notification_title))
            .setContentText(resources.getString(R.string.notification_description))
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(pendingIntent)

        notificationManager.notify(1, notification.build())
    }

    override fun onResume() {
        super.onResume()
        listWords.clear()
        listWords.addAll(dbHelper.getAllWords())
        adaptor.notifyDataSetChanged()
    }

    private fun init() {
        bindViews()
        dbHelper = WordDBHelper(this)
        listWords = dbHelper.getAllWords()
        adaptor = RcyclerviewAdaptor(this, listWords , dbHelper)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptor
        fab.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }
    }


    private fun bindViews() {
        recyclerView = findViewById(R.id.mainList)
        fab = findViewById(R.id.neumorphFloatingActionButton)
    }


    override fun onBackPressed() {
        var alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.close_app)
        alertDialogBuilder.setMessage(R.string.close_app_Description)
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert)
        alertDialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
            finish()
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { _, _ -> }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}