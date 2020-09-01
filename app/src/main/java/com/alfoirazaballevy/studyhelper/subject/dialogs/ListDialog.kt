package com.alfoirazaballevy.studyhelper.subject.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class ListDialog(
    private val subjectId: Long,
    private val subjectName: String,
    private val listViewPosition: Int,
    private val adapter: com.alfoirazaballevy.studyhelper.subject.list_items.ListAdapter
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val optionStrings = arrayOf(
            "Modificar",
            "Borrar"
        )

        val alDiagBuilder = AlertDialog.Builder(activity)
        alDiagBuilder.setTitle(subjectName)
        alDiagBuilder.setItems(optionStrings, DialogInterface.OnClickListener { dialogInterface, i ->
            when(i) {
                0 -> {
                    Toast.makeText(activity, "Por implementar...", Toast.LENGTH_SHORT)
                }
                1 -> {
                    DeleteDialog(
                        subjectId,
                        subjectName,
                        listViewPosition,
                        adapter
                    ).show(
                        (context as AppCompatActivity).supportFragmentManager,
                        "Display Delete Diag"
                    )
                }
            }
            println("Selected option ${optionStrings[i]} for subjectId: ${subjectId}")
        })

        return alDiagBuilder.create()

    }

}