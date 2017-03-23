package h264.com;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lostad.app.demo.util.SceneUtil;
import com.lostad.applib.core.Action1;
import com.lostad.applib.core.Func3;
import com.lostad.applib.core.MyCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Hocean on 2017/3/23.
 */

public class VideoView extends View implements Runnable {

    private Thread thread;
    private VView vview;

    private int sceneX = 1080; //屏幕
    private int sceneY = 1920;
    private float scalcX = 1.0f; //缩放比例
    private float scalcY = 1.0f;
    private int width = 352;  // 视窗与缓存
    private int height = 288;

    private Bitmap mBitQQ = null;
    private Paint mPaint = null;
    private Bitmap mSCBitmap = null;

    private byte[] mPixel = new byte[width * height * 2];
    private ByteBuffer buffer = ByteBuffer.wrap(mPixel);
    private Bitmap VideoBit = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    private int mTrans = 0x0F0F0F0F;

    private InputStream is = null; //必须参数
    public Action1<Bitmap> actBitmap;  //图片回调

    private Func3<byte[], Integer, Integer, Integer> funcRead;

    private volatile boolean isStart = false;
    private volatile boolean isPaue = false;
    public volatile boolean isExit = true;
    private int sleepTime = 50; //默认50ms 每帧

    private Object control = new Object();//只是任意的实例化一个对象而已

    public VideoView(Context context) {
        super(context);
        vview = new VView();
        setFocusable(true);

        Point p = SceneUtil.getSizeNew(context);
        sceneX = p.x;
        sceneY = p.y;

        load();
    }

    public VideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, int width, int height) {
        super(context);
        setSize(width, height);

        vview = new VView();
        setFocusable(true);

        Point p = SceneUtil.getSizeNew(context);
        sceneX = p.x;
        sceneY = p.y;

        load();
    }


    public void load() {
        mPixel = new byte[width * height * 2];
        for (int i = 0; i < mPixel.length; i++) {
            mPixel[i] = (byte) 0x00;
        }
        buffer = ByteBuffer.wrap(mPixel);
        VideoBit = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setScalc(float scaleX, float scaleY) {
        this.scalcX = scaleX;
        this.scalcY = scaleY;
    }

    public void setScalcScene(float sceneX, float sceneY) {
        this.scalcX = this.sceneX * sceneX / width;
        this.scalcY = this.sceneY * sceneY / height;
    }

    public void ready(String file) {
        try {
            is = new FileInputStream(file);
        } catch (IOException e) {
            return;
        }
    }

    public void ready(File file) {
        try {
            is = new FileInputStream(file);
        } catch (IOException e) {
            return;
        }
    }

    public void ready(InputStream is) {
        this.is = is;
    }

    public void ready(byte[] byt) {
        //byte[] byt = new byte[1024];
        is = new ByteArrayInputStream(byt);
    }

    public void start() {
        if(isPaue)paue(false);
        if (isExit) {
            thread = new Thread(this);
            thread.start();
            isStart = true;
        } else {
            stop();
            start();
        }
    }

    public void start(Func3<byte[], Integer, Integer, Integer> func) {
        start();
    }


    public void paue() {
        paue(!isPaue);
    }

    public void paue(boolean isPaue) {
        if (!isPaue) {
            synchronized (control) {
                control.notifyAll();
            }
        }
        this.isPaue = isPaue;
    }

    public void stop() {
        try {
            if(isPaue)paue(false);
            isStart = false;
            if (thread != null) {
                thread.interrupt();
                thread.join(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            //    	Bitmap tmpBit = Bitmap.createBitmap(mPixel, 320, 480, Bitmap.Config.RGB_565);//.ARGB_8888);

            buffer.rewind(); //vive 必须清除缓存 防止粘包
            VideoBit.copyPixelsFromBuffer(buffer);//makeBuffer(data565, N));

            // 保存画布状态
            canvas.save();
            canvas.scale(scalcX, scalcY);
            if (actBitmap != null) actBitmap.invoke(VideoBit);
            canvas.drawBitmap(VideoBit, 0, 0, null);

            // 画布状态回滚
            canvas.restore();
        } catch (Exception e) {

        }

    }

    //融入流
    int MergeBuffer(byte[] NalBuf, int NalBufUsed, byte[] SockBuf, int SockBufUsed, int SockRemain) {
        int i = 0;
        byte Temp;
        for (i = 0; i < SockRemain; i++) {
            Temp = SockBuf[i + SockBufUsed];
            NalBuf[i + NalBufUsed] = Temp;
            mTrans <<= 8;
            mTrans |= Temp;
            if (mTrans == 1) // 找到一个开始字
            {
                i++;
                break;
            }
        }
        return i;
    }

    //播放开始
    public void run() {
        try {
            int iTemp = 0;
            int nalLen;
            boolean bFirst = true;
            boolean bFindPPS = true;
            int bytesRead = 0;
            int NalBufUsed = 0;
            int SockBufUsed = 0;
            byte[] NalBuf = new byte[40980]; // 40k
            byte[] SockBuf = new byte[2048];

            vview.InitDecoder(width, height);
            while (isStart && !Thread.currentThread().isInterrupted()) {
                long start = System.currentTimeMillis();
//                synchronized (control) {
//                    if (isPaue) {
//                        try {
//                            control.wait();
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
                if (!isPaue)
                {
                    try {
                        if (funcRead != null) {
                            bytesRead = funcRead.invoke(SockBuf, 0, 2048);
                        } else {
                            bytesRead = is.read(SockBuf, 0, 2048);
                        }
                    } catch (IOException e) {
                    }
                    if (bytesRead <= 0)
                        break;
                    SockBufUsed = 0;
                    while (bytesRead - SockBufUsed > 0) {
                        nalLen = MergeBuffer(NalBuf, NalBufUsed, SockBuf, SockBufUsed, bytesRead - SockBufUsed);
                        NalBufUsed += nalLen;
                        SockBufUsed += nalLen;
                        while (mTrans == 1) {
                            mTrans = 0xFFFFFFFF;
                            if (bFirst == true) // the first start flag
                            {
                                bFirst = false;
                            } else  // a complete NAL data, include 0x00000001 trail.
                            {
                                if (bFindPPS == true) // true
                                {
                                    if ((NalBuf[4] & 0x1F) == 7) {
                                        bFindPPS = false;
                                    } else {
                                        NalBuf[0] = 0;
                                        NalBuf[1] = 0;
                                        NalBuf[2] = 0;
                                        NalBuf[3] = 1;
                                        NalBufUsed = 4;
                                        break;
                                    }
                                }
                                //	decode nal
                                iTemp = vview.DecoderNal(NalBuf, NalBufUsed - 4, mPixel);
                                if (iTemp > 0) {
                                    try {
                                        postInvalidate();  //使用postInvalidate可以直接在线程中更新界面    // postInvalidate();
                                    } catch (Exception e) {

                                    }
                                }
                            }
                            NalBuf[0] = 0;
                            NalBuf[1] = 0;
                            NalBuf[2] = 0;
                            NalBuf[3] = 1;
                            NalBufUsed = 4;
                        }
                    }
                }
                long end = System.currentTimeMillis();
                try {
                    if (end - start < sleepTime) {
                        Thread.sleep(sleepTime - (end - start));
                    }
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    break;
                }
            }
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            vview.UninitDecoder();
            isExit = true;
        } catch (Exception e) {
        }

    }

    public Action1<Bitmap> getActBitmap() {
        return actBitmap;
    }

    public void setActBitmap(Action1<Bitmap> actBitmap) {
        this.actBitmap = actBitmap;
    }

    public Func3<byte[], Integer, Integer, Integer> getFuncRead() {
        return funcRead;
    }

    public void setFuncRead(Func3<byte[], Integer, Integer, Integer> funcRead) {
        this.funcRead = funcRead;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getFPS() {
        return 1000 / sleepTime;
    }

    public void setFPS(int fps) {
        this.sleepTime = 1000 / fps;
    }
}
