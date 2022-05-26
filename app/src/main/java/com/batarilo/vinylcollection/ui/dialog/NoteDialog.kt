package com.batarilo.vinylcollection.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.batarilo.vinylcollection.R
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.interactors.notes.SetRecordNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class NoteDialog(context: Context, val item: Record, val setRecordNote: SetRecordNote)
    : Dialog(context) {

    init {
        setCancelable(true)
    }
    private val mainActivityScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.note_dialog)
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)


        if(item.note!=null)
            findViewById<EditText>(R.id.et_note).setText(item.note)


        findViewById<ImageButton>(R.id.btn_accept).setOnClickListener {
           val noteCurrent = findViewById<EditText>(R.id.et_note).text.toString()
            mainActivityScope.launch {
                if(noteCurrent=="")
                    setRecordNote.execute(item,null)

                if(noteCurrent!="")
                    setRecordNote.execute(item,noteCurrent)
            }
            println("UPDATED NOTE IS $item")
            dismiss()
        }

        findViewById<ImageButton>(R.id.btn_close_dialog).setOnClickListener{
            dismiss()
        }

        findViewById<ImageButton>(R.id.btn_delete).setOnClickListener{
            mainActivityScope.launch {
                setRecordNote.execute(item, null)
            }
            dismiss()
        }
    }
}