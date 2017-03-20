package com.lostad.app.base.util.media;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lostad.app.demo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class H264Android extends Activity {

	VView vv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vv = new VView(this);
		setContentView(vv);
	}

	// Menu item Ids
	public static final int PLAY_ID = Menu.FIRST;
	public static final int EXIT_ID = Menu.FIRST + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, PLAY_ID, 0, R.string.play);
		menu.add(0, EXIT_ID, 1, R.string.exit);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case PLAY_ID:
			{
				// 此处设定不同分辨率的码流文件
				File skRoot = Environment.getExternalStorageDirectory();
				String file =   "/mnt/sdcard/352x288.264"; //352x288.264"; //240x320.264";
				//String file = skRoot.getParent() +   "/352x288.264"; //352x288.264"; //240x320.264";
				vv.PlayVideo(file);

				return true;
			}
			case EXIT_ID:
			{
				finish();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}

class VView extends View implements Runnable {

	Bitmap mBitQQ  = null;

	Paint mPaint = null;

	Bitmap mSCBitmap = null;

	int width = 352;  // 此处设定不同的分辨率
	int height = 288;

	byte [] mPixel = new byte[width*height*2];

	ByteBuffer buffer = ByteBuffer.wrap( mPixel );
	Bitmap VideoBit = Bitmap.createBitmap(width, height, Config.RGB_565);

	int mTrans=0x0F0F0F0F;

	String PathFileName;

	public native int InitDecoder(int width, int height);
	public native int UninitDecoder();
	public native int DecoderNal(byte[] in, int insize, byte[] out);

	static {
		System.loadLibrary("H264Android");
	}

	public VView(Context context) {
		super(context);
		setFocusable(true);

		int i = mPixel.length;

		for(i=0; i<mPixel.length; i++)
		{
			mPixel[i]=(byte)0x00;
		}
	}

	public void PlayVideo(String file)
	{
		PathFileName = file;

		new Thread(this).start();
	}

	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

//    	Bitmap tmpBit = Bitmap.createBitmap(mPixel, 320, 480, Bitmap.Config.RGB_565);//.ARGB_8888);

		buffer.rewind(); //vive 清除缓存
		VideoBit.copyPixelsFromBuffer(buffer);//makeBuffer(data565, N));

		canvas.drawBitmap(VideoBit, 0, 0, null);
	}

	int MergeBuffer(byte[] NalBuf, int NalBufUsed, byte[] SockBuf, int SockBufUsed, int SockRemain)
	{
		int  i=0;
		byte Temp;

		for(i=0; i<SockRemain; i++)
		{
			Temp  =SockBuf[i+SockBufUsed];
			NalBuf[i+NalBufUsed]=Temp;

			mTrans <<= 8;
			mTrans  |= Temp;

			if(mTrans == 1) // 找到一个开始字
			{
				i++;
				break;
			}
		}

		return i;
	}

	public void run()
	{
		InputStream is = null;
		FileInputStream fileIS=null;

		int iTemp=0;
		int nalLen;

		boolean bFirst=true;
		boolean bFindPPS=true;

		int bytesRead=0;
		int NalBufUsed=0;
		int SockBufUsed=0;

		byte [] NalBuf = new byte[40980]; // 40k
		byte [] SockBuf = new byte[2048];

		try
		{
			fileIS = new FileInputStream(PathFileName);
		}
		catch(IOException e)
		{
			return ;
		}

		InitDecoder(width, height);

		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				bytesRead = fileIS.read(SockBuf, 0, 2048);
			}
			catch (IOException e) {}

			if(bytesRead<=0)
				break;

			SockBufUsed =0;

			while(bytesRead-SockBufUsed>0)
			{
				nalLen = MergeBuffer(NalBuf, NalBufUsed, SockBuf, SockBufUsed, bytesRead-SockBufUsed);

				NalBufUsed += nalLen;
				SockBufUsed += nalLen;

				while(mTrans == 1)
				{
					mTrans = 0xFFFFFFFF;

					if(bFirst==true) // the first start flag
					{
						bFirst = false;
					}
					else  // a complete NAL data, include 0x00000001 trail.
					{
						if(bFindPPS==true) // true
						{
							if( (NalBuf[4]&0x1F) == 7 )
							{
								bFindPPS = false;
							}
							else
							{
								NalBuf[0]=0;
								NalBuf[1]=0;
								NalBuf[2]=0;
								NalBuf[3]=1;

								NalBufUsed=4;

								break;
							}
						}
						//	decode nal
						iTemp=DecoderNal(NalBuf, NalBufUsed-4, mPixel);

						if(iTemp>0)
							postInvalidate();  //使用postInvalidate可以直接在线程中更新界面    // postInvalidate();
					}

					NalBuf[0]=0;
					NalBuf[1]=0;
					NalBuf[2]=0;
					NalBuf[3]=1;

					NalBufUsed=4;
				}
			}
		}
		try{
			if(fileIS!=null)
				fileIS.close();
			if(is!=null)
				is.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		UninitDecoder();
	}
}
