package common.voice;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 语音播报实现类
 *
 * @author Administrator
 */
public class VoiceBroadcast implements IVoice, SpeechSynthesizerListener {

    //http://blog.csdn.net/kjunchen/article/details/51093134
    //一定要设置：jniLibs.srcDirs=['libs']；否则导致-106 或者-200错误

    private static final String TAG = "VoiceBroadcast";

    private static Context mContext;

    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static VoiceBroadcast instance;

    private VoiceBroadcast() {

    }

    public static synchronized VoiceBroadcast getInstance(Context context) {
        if (instance == null) {
            instance = new VoiceBroadcast();
        }

        if (context != null) {
            mContext = context;
        }

        instance.initialEnv();
        instance.initialTts();

        return instance;
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;///storage/emulated/0/baiduTTS
        }
        makeDir(mSampleDirPath);

        Log.d(TAG, "initialEnv: mSampleDirPath=" + mSampleDirPath);

        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);


        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);


        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME,
                mSampleDirPath + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME,
                mSampleDirPath + "/" + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME,
                mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME);

    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = mContext.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String APPID = "10192060", APIKEY = "77Z7hSFsKMYyhiTaNjzYDw8R", SECRETKEY = "edb09d7f941344d32b7680e6d13b3c49";

    private void initialTts() {


        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();

        this.mSpeechSynthesizer.setContext(mContext);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);

        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId(APPID);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        int ret = this.mSpeechSynthesizer.setApiKey(APIKEY, SECRETKEY);
        Log.d(TAG, "initialTts: setApiKey=" + ret);

        // 文本模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);

        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        //this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/" + LICENSE_FILE_NAME);


        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);

        // 发音人（在线引擎），可用参数为0,1,2,3。。。
        // （服务器端会动态增加，各值含义参考文档，以文档说明为准。
        // 0--普通女声，1--普通男声，2--特别男声，3--情感男声，4 (情感儿童声<度丫丫>)。。。）
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");


        // 加载离线英文资源（提供离线英文合成功能）
        //mSampleDirPath:/storage/emulated/0/baiduTTS
        //bd_etts_text_en.dat
        //int result = mSpeechSynthesizer.loadModel(mSampleDirPath + "/" + TEXT_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);

        //int result1 = mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME,
        //mSampleDirPath + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);

        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {

            // 初始化tts
            mSpeechSynthesizer.initTts(TtsMode.MIX);//MIX:混合

            // 发音人（在线引擎），可用参数为0,1,2,3。。。
            // （服务器端会动态增加，各值含义参考文档，以文档说明为准。
            // 0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");

            Log.d(TAG, "initialTts: 授权成功");
            //mSpeechSynthesizer.speak("授权成功");

        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.d(TAG, "initialTts: errorMsg=" + errorMsg);
        }


    }

    @Override
    public int playVoice(String text) {
        // TODO Auto-generated method stub

        stopVoice();// 播放当前语音，先停止之前播放内容

        // 需要合成的文本text的长度不能超过1024个GBK字节。


        int ret = mSpeechSynthesizer.speak(text);

        Log.d(TAG, "playVoice: ret=" + ret);

        return ret;
    }

    @Override
    public int batchSpeak(List<SpeechSynthesizeBag> bags) {
        // TODO Auto-generated method stub
        if (bags == null || bags.size() == 0) {
            return -1;
        }
        return mSpeechSynthesizer.batchSpeak(bags);
    }

    @Override
    public void setSoundType(int i) {
        // TODO Auto-generated method stub
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, i + "");

    }

    @Override
    public SpeechSynthesizeBag getSpeechSynthesizeBag(String bstext) {
        // TODO Auto-generated method stub
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        // 需要合成的文本text的长度不能超过1024个GBK字节。
        speechSynthesizeBag.setText(bstext);
        return speechSynthesizeBag;
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub
        this.mSpeechSynthesizer.release();
    }

    @Override
    public void stopVoice() {
        // TODO Auto-generated method stub
        this.mSpeechSynthesizer.stop();
    }

    @Override
    public void onError(String arg0, SpeechError arg1) {
        // TODO Auto-generated method stub

        Log.d(TAG, "onError() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "]");

    }

    @Override
    public void onSpeechFinish(String arg0) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onSpeechFinish() called with: arg0 = [" + arg0 + "]");
        //播放结束
    }

    @Override
    public void onSpeechProgressChanged(String arg0, int arg1) {
        // TODO Auto-generated method stub
        //播放进度变化
        Log.d(TAG, "onSpeechProgressChanged() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "]");
    }

    @Override
    public void onSpeechStart(String arg0) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onSpeechStart() called with: arg0 = [" + arg0 + "]");
        Log.d(TAG, "onSpeechStart: " + "合成开始");
    }

    @Override
    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onSynthesizeDataArrived: " + "合成数据到达");
    }

    @Override
    public void onSynthesizeFinish(String arg0) {
        // TODO Auto-generated method stub

        Log.d(TAG, "onSynthesizeFinish() called with: arg0 = [" + arg0 + "]");
        Log.d(TAG, "onSynthesizeFinish: " + "合成结束");
    }

    @Override
    public void onSynthesizeStart(String arg0) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onSynthesizeStart: " + "合成开始");
    }

}
