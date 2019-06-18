package app.heartie.heartie

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setup.*
import java.text.SimpleDateFormat
import java.util.*

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)


        val textView: TextView = BirthdayInput
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)


        }


        textView.setOnClickListener {
            DatePickerDialog(this@SetupActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        ContinueButton.setOnClickListener {
            val textName = Nameinput.text.toString()

            BirthdateInputLayout.setError(null)
            NameInputLayout.setError(null)

            val ageCheck = Calendar.getInstance()
            ageCheck.add(Calendar.YEAR, -18)
            var allGood: Boolean? = true
            if (!cal.time.before(ageCheck.time)) {
                BirthdateInputLayout.error = "You need to be over 18 years old!"
                allGood = false
            }
            else if (textName.length <= 2) {
                NameInputLayout.error = "Name too short!"
                allGood = false
            }
            else if (GenderRadioGroup.checkedRadioButtonId == -1) {
                //TODO Handle error message
                allGood = false
            }
            else if (LookingRadioGroup.checkedRadioButtonId == -1) {
                //TODO Handle error message
                allGood = false
            }

        }
    }
}
