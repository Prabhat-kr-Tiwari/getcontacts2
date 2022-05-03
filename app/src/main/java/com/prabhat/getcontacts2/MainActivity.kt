package com.prabhat.getcontacts2

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.prabhat.getcontacts2.R
import android.os.Build
import android.content.pm.PackageManager
import android.provider.ContactsContract
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var tv_phonebook: TextView? = null
    var arrayList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_phonebook = findViewById(R.id.tv_phonebook)
        arrayList = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS), 1
            )
        } else {
            getcontact()
        }
    }

    private fun getcontact() {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val mobile =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                arrayList!!.add(
                    """
    $name
    $mobile
    """.trimIndent()
                )
                tv_phonebook!!.text = arrayList.toString()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getcontact()
            }
        }
    }
}