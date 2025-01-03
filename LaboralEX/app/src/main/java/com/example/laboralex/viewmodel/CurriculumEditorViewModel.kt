package com.example.laboralex.viewmodel

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import androidx.lifecycle.ViewModel
import java.io.FileOutputStream

class CurriculumEditorViewModel : ViewModel() {

    fun savePDF() { //TODO Trastear con canvas
        val document = PdfDocument()

        val info = PageInfo.Builder(100, 100, 1).create()

        val page = document.startPage(info)

        page.canvas.drawColor(4)

        document.finishPage(page)

        val stream = FileOutputStream("Ete.pdf")

        document.writeTo(stream)
    }
}