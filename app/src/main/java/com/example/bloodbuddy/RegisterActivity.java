package com.example.bloodbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private EditText registerName, registerEmail, registerPhone, registerPassword;
    private RadioGroup genderGroup;
    private RadioButton genderButton;
    private Spinner districtSpinner, talukSpinner;
    private Button registerButton;


    // Arrays of districts and taluks
    private String[] districts = {"Bagalkot", "Bangalore Rural", "Bangalore Urban", "Belgaum", "Bellary", "Bidar", "Bijapur", "Chamarajanagar", "Chikballapur", "Chikmagalur", "Chitradurga", "Dakshina Kannada", "Davanagere", "Dharwad", "Gadag", "Gulbarga", "Hassan", "Haveri", "Kodagu", "Kolar", "Koppal", "Mandya", "Mysore", "Raichur", "Ramanagara", "Shimoga", "Tumkur", "Udupi", "Uttara Kannada", "Yadgir"};
    private String[][] taluks = {
            {"Bagalkot", "Badami", "Bilagi", "Hungund", "Jamkhandi", "Mudhol"},
            {"Devanahalli" , "Doddaballapur", "Hosakote" , "Nelamangala"},
            {"Bangalore North" , "Bangalore East" , "Bangalore South" , "Anekal"},
            {"Athani", "Bailhongal", "Belgaum", "Chikodi", "Gokak", "Hukkeri", "Khanapur", "Ramdurg", "Raibag", "Saundatti"},
            {"Bellary", "Siruguppa", "Hospet", "Kudligi", "Sandur", "Hadagali", "Hagaribommanahalli"},
            {"Humnabad", "Bidar", "Bhalki", "Aurad", "Basavakalyan"},
            {"Bijapur", "Basavana Bagewadi", "Sindagi", "Indi", "Muddebihal"},
            {"Chamarajanagar", "Gundlupet", "Kollegal", "Yelandur"},
            {"Chikballapur", "Chintamani", "Gauribidanur", "Bagepalli", "Sidlaghatta", "Gudibanda"},
            {"Chikmagalur", "Kadur", "Koppa", "Mudigere", "Narasimharajapura", "Sringeri", "Tarikere"},
            {"Chitradurga", "Hiriyur", "Hosadurga", "Holalkere", "Molakalmuru", "Challakere"},
            {"Mangalore", "Bantwal", "Belthangady", "Puttur", "Sullia", "Ullal", "Karkala", "Mudabidri", "Mulki", "Surathkal", "Moodabidri"},
            {"Davanagere", "Channagiri", "Honnali", "Harihar", "Harapanahalli", "Jagalur"},
            {"Dharwad", "Hubli", "Kalghatgi", "Kundgol", "Navalgund"},
            {"Gadag", "Mundargi", "Nargund", "Ron", "Shirhatti"},
            {"Kamalapur", "Shahbad", "Kalaburagi", "Aland", "Jewargi", "Afzalpur"},
            {"Arsikere", "Belur", "Channarayapatna", "Hassan", "Holenarsipur", "Sakleshpur", "Alur", "Arkalgud"},
            {"Hanagal", "Haveri", "Hirekerur", "Ranebennur", "Byadgi", "Savanur", "Shiggaon"},
            {"Madikeri", "Somwarpet", "Virajpet"},
            {"Bangarapet", "Kolar", "Malur", "Mulbagal", "Srinivaspur"},
            {"Gangawati", "Koppal", "Kushtagi", "Yelburga"},
            {"Krishnarajpet", "Mandya", "Malavalli", "Nagamangala", "Pandavapura", "Srirangapatna", "Maddur"},
            {"Hunsur", "Krishnarajanagara", "Mysore", "Nanjangud", "Piriyapatna", "Tirumakudal Narsipur"},
            {"Devadurga", "Lingsugur", "Manvi", "Raichur", "Sindhanur"},
            {"Channapatna", "Kanakapura", "Magadi", "Ramanagaram"},
            {"Bhadravathi", "Hosanagara", "Sagara", "Shikarpur", "Shimoga", "Sorab", "Tirthahalli"},
            {"Tumkur", "Sira", "Tiptur", "Gubbi", "Madhugiri"},
            {"Karkala", "Kundapura", "Udupi"},
            {"Ankola", "Bhatkal", "Haliyal", "Karwar", "Kumta", "Mundgod", "Siddapur", "Sirsi", "Yellapur", "Dandeli"},
            {"Shahpur", "Shorapur", "Yadgir"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerName = findViewById(R.id.register_name);
        registerEmail = findViewById(R.id.register_email);
        registerPhone = findViewById(R.id.register_phone);
        registerPassword = findViewById(R.id.register_password);
        genderGroup = findViewById(R.id.gender_group);
        districtSpinner = findViewById(R.id.spinner_district);
        talukSpinner = findViewById(R.id.spinner_taluk);
        registerButton = findViewById(R.id.register_button);

        // Set up the district spinner
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);

        // Set up the taluk spinner
        ArrayAdapter<String> talukAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taluks[0]);
        talukAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        talukSpinner.setAdapter(talukAdapter);

        // Update taluk spinner based on selected district
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> talukAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, taluks[position]);
                talukAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                talukSpinner.setAdapter(talukAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = registerName.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String phone = registerPhone.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        genderButton = findViewById(selectedGenderId);
        String gender = genderButton == null ? "" : genderButton.getText().toString().trim();
        String district = districtSpinner.getSelectedItem().toString();
        String taluk = talukSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            registerName.setError("Name is required");
            registerName.requestFocus();
            return;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("Valid email is required");
            registerEmail.requestFocus();
            return;
        }

        // Validate email domain
        String emailDomain = email.substring(email.indexOf("@") + 1);
        if (!emailDomain.equals("gmail.com")) {
            registerEmail.setError("Email must be a valid Gmail address (e.g., user@gmail.com)");
            registerEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            registerPhone.setError("Phone number is required");
            registerPhone.requestFocus();
            return;
        }

        if (!phone.matches("\\d{10}")) {
            registerPhone.setError("Enter a valid Phone number");
            registerPhone.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            registerPassword.setError("Password must be at least 6 characters");
            registerPassword.requestFocus();
            return;
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User registered successfully
                            Log.d(TAG, "User registered successfully");

                            // Store additional user details in Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("phone", phone);
                            userMap.put("gender", gender);
                            userMap.put("district", district);
                            userMap.put("taluk", taluk);

                            currentUserDb.setValue(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User details saved successfully");
                                            } else {
                                                Log.e(TAG, "Failed to save user details", task.getException());
                                            }
                                        }
                                    });

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Finish current activity to prevent returning to it with back button
                        } else {
                            // Registration failed
                            Log.e(TAG, "Registration failed", task.getException());
                            if (task.getException() instanceof FirebaseAuthException) {
                                FirebaseAuthException authEx = (FirebaseAuthException) task.getException();
                                if (authEx.getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                    // Email already exists
                                    Toast.makeText(RegisterActivity.this, "Email already registered. Please use a different email.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed: " + authEx.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }
}
