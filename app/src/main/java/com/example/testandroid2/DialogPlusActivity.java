package com.example.testandroid2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

public class DialogPlusActivity extends ToolBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_plus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog_plus, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			showDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDialog(){
		DialogPlus dialog = DialogPlus.newDialog(this)
//			    .setAdapter(new )
				.setContentHolder(new ViewHolder(R.layout.layout_header))
			    .setOnItemClickListener(new OnItemClickListener() {
			        @Override
			        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
			        }
			    })
			    .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
			    .create();
			dialog.show();
	}
}
