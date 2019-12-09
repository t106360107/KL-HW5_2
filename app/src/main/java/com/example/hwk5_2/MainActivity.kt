package com.example.hwk5_2

import androidx.appcompat.app.AppCompatActivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var flag: Boolean = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras
            tv_clock?.text = String.format(
                "%02d:%02d:%02d",
                b!!.getInt("H"),
                b.getInt("M"),
                b.getInt("S")
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(receiver, IntentFilter("MyMessage"))
        flag = MyService.flag
        btn_start.text = if(flag) "暫停" else "開始"
        btn_start.setOnClickListener{
            flag = !flag
            btn_start.text = if(flag) "暫停" else "開始"
            Toast.makeText(
                this, if(flag) "計時開始" else "計時暫停",
                Toast.LENGTH_SHORT
            ).show()
            startService(
                Intent(
                    this, MyService::class.java
                ).putExtra("flag", flag)
            )
        }

    }

    public override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
