package dsv.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        
        Intent intentIn = getIntent();
        String htmlText = intentIn.getStringExtra("text");
        
        TextView htmlTextView = (TextView) findViewById(R.id.html_text);
        htmlTextView.setText(htmlText);
        
        Button closeButton = (Button) findViewById(R.id.close_button);      
        closeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
        });
    }
}
