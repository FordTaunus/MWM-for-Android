                                                                     
                                                                     
                                                                     
                                             
 /*****************************************************************************
  *  Copyright (c) 2011 Meta Watch Ltd.                                       *
  *  www.MetaWatch.org                                                        *
  *                                                                           *
  =============================================================================
  *                                                                           *
  *  Licensed under the Apache License, Version 2.0 (the "License");          *
  *  you may not use this file except in compliance with the License.         *
  *  You may obtain a copy of the License at                                  *
  *                                                                           *
  *    http://www.apache.org/licenses/LICENSE-2.0                             *
  *                                                                           *
  *  Unless required by applicable law or agreed to in writing, software      *
  *  distributed under the License is distributed on an "AS IS" BASIS,        *
  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. *
  *  See the License for the specific language governing permissions and      *
  *  limitations under the License.                                           *
  *                                                                           *
  *****************************************************************************/

 /*****************************************************************************
  * MediaControl.java                                                         *
  * MediaControl                                                              *
  * Volume control and vanilla Android player control via intents             *
  *                                                                           *
  *                                                                           *
  *****************************************************************************/

package org.metawatch.manager;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;

public class MediaControl {
	
	final static byte VOLUME_UP = 10;
	final static byte VOLUME_DOWN = 11;
	final static byte NEXT = 15;
	final static byte PREVIOUS = 16;
	final static byte TOGGLE = 20;
	
	public static void next(Context context) {
		sendMediaButtonEvent(context, KeyEvent.KEYCODE_MEDIA_NEXT);
	}
	
	public static void previous(Context context) {
		sendMediaButtonEvent(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
	}
	
	public static void togglePause(Context context) {
		sendMediaButtonEvent(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
	}

	public static void AnswerCall(Context context) {
		sendMediaButtonEvent(context, KeyEvent.KEYCODE_HEADSETHOOK, "android.permission.CALL_PRIVILEGED");
	}

	public static void DismissCall(Context context) {
		sendMediaButtonEvent(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
	}

	public static void ToggleSpeakerphone(AudioManager audioManager) {
		audioManager.setSpeakerphoneOn(!audioManager.isSpeakerphoneOn());
	}

	
	public static void volumeDown(AudioManager audioManager) {
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
	}
	
	public static void volumeUp(AudioManager audioManager) {
		audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
	}
	

	private static void sendMediaButtonEvent(Context context, int keyCode)
	{
		sendMediaButtonEvent(context, keyCode, null);
	}
	
	private static void sendMediaButtonEvent(Context context, int keyCode, String permission)
	{
		long time = SystemClock.uptimeMillis();
		Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent downEvent = new KeyEvent(time, time, KeyEvent.ACTION_DOWN, keyCode, 0);
		downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
		context.sendOrderedBroadcast(downIntent, permission);
		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(time, time, KeyEvent.ACTION_UP, keyCode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
		context.sendOrderedBroadcast(upIntent, permission);
	}
}
