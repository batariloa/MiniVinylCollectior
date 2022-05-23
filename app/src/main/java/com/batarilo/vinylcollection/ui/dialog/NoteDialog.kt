package com.batarilo.vinylcollection.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
import kotlinx.coroutines.*


class NoteDialog
    (context: Context, val item:RecordInList, val setRecordNote: SetRecordNote)
    : Dialog(context) {

    init {
        setCancelable(true)
    }
    private val mainActivityScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.note_dialog)
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        if(item.record.note!=null)
            findViewById<EditText>(R.id.et_note).setText(item.record.note)

        findViewById<ImageButton>(R.id.btn_accept).setOnClickListener {
           val noteCurrent = findViewById<EditText>(R.id.et_note).text.toString()
            mainActivityScope.launch {
                setRecordNote.execute(item.record,noteCurrent)
            }
            println("UPDATED NOTE IS $item")
            dismiss()
        }

        findViewById<ImageButton>(R.id.btn_close_dialog).setOnClickListener{
            dismiss()
        }

        findViewById<ImageButton>(R.id.btn_delete).setOnClickListener{
            mainActivityScope.launch {

                setRecordNote.execute(item.record, null)

            }
            dismiss()
        }
    }
}