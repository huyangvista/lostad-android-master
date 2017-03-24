package h264.com;


/**
 * Created by Hocean on 2017/3/20.
 */

public class VView  {
    static {
        System.loadLibrary("H264Android");
    }
    public native int InitDecoder(int width, int height);
    public native int UninitDecoder();
    public native int DecoderNal(byte[] in, int insize, byte[] out);
}
