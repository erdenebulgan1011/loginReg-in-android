package com.example.lab9;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class GameOver extends AppCompatActivity {
    TextView tvPoints;
    ImageButton imgBtn1,imgBtn2;
    ImageView ivNewHighest,ivLooser;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_points");
        imgBtn1=findViewById(R.id.imgBtn1);
        imgBtn2=findViewById(R.id.imgBtn2);
        ivNewHighest = findViewById(R.id.ivNewHeighest);
        ivLooser=findViewById(R.id.ivLooser);
        tvPoints = findViewById(R.id.tvPoints);
        int points = getIntent().getIntExtra("points", 0);
        if (points == 240) {
            ivNewHighest.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(GameOver.this, R.anim.animation6);
            ivNewHighest.startAnimation(anim);
        } else {
            ivLooser.setVisibility(View.VISIBLE);
            Animation anima = AnimationUtils.loadAnimation(GameOver.this, R.anim.animation6);
            ivLooser.startAnimation(anima);
        }
        tvPoints.setText(String.valueOf(points));
        String mobileNumber = getIntent().getStringExtra("phoneTxtT");
        Toast.makeText( GameOver.this,"MobileNumber: " + mobileNumber + ", Points: " + points, Toast.LENGTH_SHORT).show();
    if (mobileNumber != null)
{
    saveMobileAndPointsToFirebase(mobileNumber, points);
}
    }
    public void restart(View view){
        Animation imageanim = AnimationUtils.loadAnimation(GameOver.this, R.anim.animation5);
        imgBtn1.startAnimation(imageanim);
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void exit(View view){
        Animation imageanime = AnimationUtils.loadAnimation(GameOver.this, R.anim.animation5);
        imgBtn2.startAnimation(imageanime);
        finish();
    }
    private void saveMobileAndPointsToFirebase(String mobileNumber, int points) {
        // Save points under the user's phone number in Firebase
        databaseReference.child(mobileNumber).setValue(points);
    }
}
