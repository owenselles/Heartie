package app.heartie.heartie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Choose authentication providers
        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
//                AuthUI.IdpConfig.FacebookBuilder().build(),
//                AuthUI.IdpConfig.TwitterBuilder().build()
        )

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.LightAppTheme)
                        .setTosAndPrivacyPolicyUrls(
                                "https://heartie.app/terms.html",
                                "https://heartie.app/privacy.html"
                        )
                        .build(),
                RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                if (response!!.isNewUser) {
                    // TODO Go to account setup page
                    Toast.makeText(this,"New Account!", Toast.LENGTH_LONG).show()
                }
                else {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Welcome back!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Can't login!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
