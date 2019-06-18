package app.heartie.heartie

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_setup.*
import java.text.SimpleDateFormat
import java.util.*

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)


        val textView: TextView = findViewById(R.id.BirthdayInput)
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

        val continueButton: Button = findViewById(R.id.ContinueButton)

        continueButton.setOnClickListener {
            val birthdayInputLayout: TextInputLayout = findViewById(R.id.BirthdateInputLayout)
            val nameInputLayout: TextInputLayout = findViewById(R.id.NameInputLayout)
            val genderRadioGroup: RadioGroup = findViewById(R.id.GenderRadioGroup)
            val textName = Nameinput.text.toString()

            //TODO stopped working here add the 2 radiogroups
            birthdayInputLayout.setError(null)
            val ageCheck = Calendar.getInstance()
            ageCheck.add(Calendar.YEAR, -18)
            var allGood: Boolean? = true
            if (!cal.time.before(ageCheck.time)) {
                birthdayInputLayout.error = "You need to be over 18 years old!"
                allGood = false
            }
            else if (textName.length <= 2) {
                nameInputLayout.error = "Name too short!"
                allGood = false
            }
            else if (genderRadioGroup.checkedRadioButtonId == -1) {

            }

        }
    }
}
