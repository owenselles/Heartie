package app.heartie.heartie

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_setup.*
import java.text.SimpleDateFormat
import java.util.*

class SetupActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    val TAG = "Heartie"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)


        //TODO get location and save to firestore
//        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            //location permisions
//            val permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION)
//            ActivityCompat.requestPermissions(this, permissions, 0)
//        } else {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    // Got last known location. In some rare situations this can be null.
//                    if (location != null) {
//                        val latitude = location.latitude
//                        val longitude = location.longitude
//                    }
//                }
//        }




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
            DatePickerDialog(
                this@SetupActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        ContinueButton.setOnClickListener {
            val textName = Nameinput.text.toString()

            BirthdateInputLayout.setError(null)
            NameInputLayout.setError(null)

            //Get gender
            val selectedId = GenderRadioGroup.getCheckedRadioButtonId()
            val radioButton = findViewById<View>(selectedId) as RadioButton
            val gender = radioButton.text.toString()

            //get looking for
            val selectedId2 = LookingRadioGroup.getCheckedRadioButtonId()
            val radioButton2 = findViewById<View>(selectedId2) as RadioButton
            val lookingFor = radioButton2.text.toString()

            //format date
            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            //check if 18+
            val ageCheck = Calendar.getInstance()
            ageCheck.add(Calendar.YEAR, -18)
            var allGood: Boolean? = true
            if (!cal.time.before(ageCheck.time)) {
                BirthdateInputLayout.error = "You need to be over 18 years old!"
                allGood = false
            }
            if (textName.length <= 2) {
                NameInputLayout.error = "Name too short!"
                allGood = false
            }
            if (allGood == true) {
                val intent = Intent(this, Setup2Activity::class.java)
                startActivity(intent)
                val currentUser = FirebaseAuth.getInstance().currentUser
                val user = hashMapOf(
                    "name" to textName,
                    "birthdate" to sdf.format(cal.time),
                    "gender" to gender,
                    "looking" to lookingFor
                )
                if (currentUser != null) {
                    db.collection("users").document(currentUser.uid)
                        .update(user as Map<String, Any>)
                        .addOnSuccessListener { Log.d(TAG, "User successfully written!") }
                        .addOnFailureListener { _ -> Log.w(TAG, "Error writing user!") }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }
}
