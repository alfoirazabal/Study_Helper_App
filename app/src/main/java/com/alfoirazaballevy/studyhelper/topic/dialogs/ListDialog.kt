package com.alfoirazaballevy.studyhelper.topic.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter

class ListDialog(
    private val topicId : Long,
    private val topicName : String,
    private val listViewPosition : Int,
    private val adapter : ListAdapter
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val optionStrings = arrayOf(
            "Modificar",
            "Borrar"
        )

        val alDiagBuilder = AlertDialog.Builder(activity)
        alDiagBuilder.setTitle(topicName)
        alDiagBuilder.setItems(optionStrings, DialogInterface.OnClickListener { dialogInterface, i ->
            when(i) {
                0 -> {
                    Toast.makeText(activity, "Por implementar...", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    DeleteDialog(
                        topicId,
                        topicName,
                        listViewPosition,
                        adapter
                    ).show(
                        (context as AppCompatActivity).supportFragmentManager,
                        "Display delete diag"
                    )
                }
            }
        })

        return alDiagBuilder.create()

    }

}