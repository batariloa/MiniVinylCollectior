package com.batarilo.vinylcollection.ui.dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.di.AppModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Inject



class NoteDialog(context: Context, val record:Record, val recordRepository: RecordRepository)  : Dialog(context) {



    init {
        setCancelable(true)
    }
    private val mainActivityScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.note_dialog)
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(record.note!=null)
            findViewById<EditText>(R.id.et_note).setText(record.note)

        findViewById<ImageButton>(R.id.btn_accept).setOnClickListener {
            record.note = findViewById<EditText>(R.id.et_note).text.toString()
            mainActivityScope.launch {
                recordRepository.updateRecord(record)
            }
            dismiss()
        }

        findViewById<ImageButton>(R.id.btn_close_dialog).setOnClickListener{
            dismiss()
        }
    }
}