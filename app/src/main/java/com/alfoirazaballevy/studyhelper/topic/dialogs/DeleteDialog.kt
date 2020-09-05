package com.alfoirazaballevy.studyhelper.topic.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.alfoirazaballevy.studyhelper.layoutadapters.ListAdapter
import androidx.fragment.app.DialogFragment
import com.alfoirazaballevy.studyhelper.db.DbHelper

class DeleteDialog(
    private val topicId : Long,
    private val topicName : String,
    private val listViewPosition : Int,
    private val adapter : ListAdapter
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alDiagBld = AlertDialog.Builder(activity)
        alDiagBld.setTitle("Confirmar Borrado")
        alDiagBld.setMessage("Borrar $topicName")
        alDiagBld.setCancelable(false)

        alDiagBld.setPositiveButton("SI", DialogInterface.OnClickListener { dialogInterface, i ->
            val dHlp = DbHelper(context)
            dHlp.deleteTopic(topicId)
            adapter.lstObjects.removeAt(listViewPosition)
            adapter.notifyDataSetChanged()
        })

        alDiagBld.setNegativeButton("NO", DialogInterface.OnClickListener { dialogInterface, i ->
            Toast.makeText(context, "Borrado Cancelado", Toast.LENGTH_SHORT).show()
        })

        return alDiagBld.create()

    }

}