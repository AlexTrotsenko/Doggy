package alex.trotsenko.doggy;

import com.google.gson.Gson;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity
{
   /**
    * A dummy authentication store containing known user names and passwords.
    * TODO: remove after connecting to a real authentication system.
    */
   private static final String[] DUMMY_CREDENTIALS = new String[] {"foo@example.com:hello",
         "bar@example.com:world"};

   /**
    * The default email to populate the email field with.
    */
   public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

   /**
    * Keep track of the login task to ensure we can cancel it if requested.
    */
   private UserLoginTask authTask = null;

   // Values for email and password at the time of the login attempt.
   private String email;
   private String password;

   // UI references.
   private EditText emailView;
   private EditText passwordView;
   private View loginFormView;
   private View loginStatusView;
   private TextView loginStatusMessageView;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_login);

      //TODO Check what intent was called this activity and read data accordantly
      
      // Set up the login form.
      email = getIntent().getStringExtra(EXTRA_EMAIL);
      emailView = (EditText) findViewById(R.id.email);
      emailView.setText(email);

      passwordView = (EditText) findViewById(R.id.password);
      passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
         {
            if (id == R.id.login || id == EditorInfo.IME_NULL)
            {
               attemptLogin();
               return true;
            }
            return false;
         }
      });

      loginFormView = findViewById(R.id.login_form);
      loginStatusView = findViewById(R.id.login_status);
      loginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

      findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            attemptLogin();
         }
      });
      
      findViewById(R.id.go_back).setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View view)
         {
            Intent result = new Intent("login.CANCELED", Uri.parse("content://result_uri"));
            setResult(Activity.RESULT_OK, result);
            finish();
         }
      });
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.login, menu);    
      return true;
   }

   /**
    * Attempts to sign in or register the account specified by the login form.
    * If there are form errors (invalid email, missing fields, etc.), the
    * errors are presented and no actual login attempt is made.
    */
   public void attemptLogin()
   {
      if (authTask != null)
      {
         return;
      }

      // Reset errors.
      emailView.setError(null);
      passwordView.setError(null);

      // Store values at the time of the login attempt.
      email = emailView.getText().toString();
      password = passwordView.getText().toString();

      boolean cancel = false;
      View focusView = null;

      // Check for a valid password.
      if (TextUtils.isEmpty(password))
      {
         passwordView.setError(getString(R.string.error_field_required));
         focusView = passwordView;
         cancel = true;
      }
      else if (password.length() < 4)
      {
         passwordView.setError(getString(R.string.error_invalid_password));
         focusView = passwordView;
         cancel = true;
      }

      // Check for a valid email address.
      if (TextUtils.isEmpty(email))
      {
         emailView.setError(getString(R.string.error_field_required));
         focusView = emailView;
         cancel = true;
      }
      else if (!email.contains("@"))
      {
         emailView.setError(getString(R.string.error_invalid_email));
         focusView = emailView;
         cancel = true;
      }

      if (cancel)
      {
         // There was an error; don't attempt login and focus the first
         // form field with an error.
         focusView.requestFocus();
      }
      else
      {
         // Show a progress spinner, and kick off a background task to
         // perform the user login attempt.
         loginStatusMessageView.setText(R.string.login_progress_signing_in);
         showProgress(true);
         authTask = new UserLoginTask();
         authTask.execute((Void) null);
      }
   }

   /**
    * Shows the progress UI and hides the login form.
    */
   @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
   private void showProgress(final boolean show)
   {
      // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
      // for very easy animations. If available, use these APIs to fade-in
      // the progress spinner.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
      {
         int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

         loginStatusView.setVisibility(View.VISIBLE);
         loginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
               .setListener(new AnimatorListenerAdapter()
               {
                  @Override
                  public void onAnimationEnd(Animator animation)
                  {
                     loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                  }
               });

         loginFormView.setVisibility(View.VISIBLE);
         loginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
               .setListener(new AnimatorListenerAdapter()
               {
                  @Override
                  public void onAnimationEnd(Animator animation)
                  {
                     loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                  }
               });
      }
      else
      {
         // The ViewPropertyAnimator APIs are not available, so simply show
         // and hide the relevant UI components.
         loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
         loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      }
   }

   /**
    * Represents an asynchronous login/registration task used to authenticate
    * the user.
    */
   public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
   {
      @Override
      protected Boolean doInBackground(Void... params)
      {
         // TODO: attempt authentication

         try
         {
            // Simulate network access.
            Thread.sleep(2000);
         }
         catch (InterruptedException e)
         {
            return false;
         }

         for (String credential : DUMMY_CREDENTIALS)
         {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(email))
            {
               // Account exists, return true if the password matches.
               return pieces[1].equals(password);
            }
         }

         // TODO: register the new account here.
         return true;
      }

      @Override
      protected void onPostExecute(final Boolean success)
      {
         authTask = null;
         showProgress(false);

         if (success)
         {
            Intent result = new Intent("login.COMPLETED", Uri.parse("content://result_uri"));
            setResult(Activity.RESULT_OK, result);
            finish();
         }
         else
         {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
         }
      }

      @Override
      protected void onCancelled()
      {
         authTask = null;
         showProgress(false);
      }
   }
}
