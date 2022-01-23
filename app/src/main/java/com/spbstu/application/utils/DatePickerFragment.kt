package com.spbstu.application.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(
    private val calendar: Calendar,
    private val REQUEST_KEY: String
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val CALENDAR_KEY = "com.spbstu.application.utils.SELECTED_DATE"

        fun defaultDateFormat(calendar: Calendar): String =
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(calendar.time)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.firstDayOfWeek = Calendar.MONDAY

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val selectedDateBundle = Bundle()
        selectedDateBundle.putSerializable(CALENDAR_KEY, calendar)

        setFragmentResult(REQUEST_KEY, selectedDateBundle)
    }
}
