package com.alfoirazaballevy.studyhelper.list_items.subject

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ListDialog(private val subjectId : Long, private val subjectName : String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val optionStrings = arrayOf(
            "Modificar",
            "Borrar"
        )

        val alDiagBuilder = AlertDialog.Builder(activity)
        alDiagBuilder.setTitle(subjectName)
        alDiagBuilder.setItems(optionStrings, DialogInterface.OnClickListener { dialogInterface, i ->
            println("Selected option ${optionStrings[i]} for subjectId: ${subjectId}")
        })

        return alDiagBuilder.create()

    }

}