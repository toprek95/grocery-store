package com.example.grocerystore.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.grocerystore.Models.GroceryItem;
import com.example.grocerystore.Utils;

public class TrackUserTime extends Service {

	private int seconds = 0;
	private boolean shouldFinish;
	private GroceryItem item;

	private IBinder binder = new LocalBinder();

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		trackTime();
		return binder;
	}

	private void trackTime() {
		Thread thread = new Thread(() -> {
			while (!shouldFinish) {
				try {
					Thread.sleep(1000);
					seconds++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public void setItem(GroceryItem item) {
		this.item = item;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		shouldFinish = true;

		int minutes = seconds / 60;
		if (minutes > 0) {
			if (null != item) {
				Utils.updateUserPoints(this, item, minutes);
			}
		}
	}

	public class LocalBinder extends Binder {
		public TrackUserTime getService() {
			return TrackUserTime.this;
		}
	}
}
