package common.voice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.tts.client.SpeechSynthesizeBag;
import com.ysy15350.easyquickcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class TestVoiceActivity extends Activity implements OnClickListener {

    private Button button1, button2;
    private EditText editText1;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;

    private static VoiceBroadcast mVoiceBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_test);

        initialView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVoiceBroadcast = VoiceBroadcast.getInstance(this);
    }

    private void initialView() {
        this.button1 = (Button) this.findViewById(R.id.button1);
        this.button2 = (Button) this.findViewById(R.id.button2);
        this.editText1 = (EditText) this.findViewById(R.id.editText1);
        this.sex = (RadioGroup) this.findViewById(R.id.sex);
        this.male = (RadioButton) this.findViewById(R.id.male);
        this.female = (RadioButton) this.findViewById(R.id.female);

        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.male.setOnClickListener(this);
        this.female.setOnClickListener(this);

    }

    /*
     * 
     */
    @Override
    protected void onDestroy() {
        mVoiceBroadcast.release();
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        int id = arg0.getId();
        switch (id) {
            case R.id.button1:
                String text = this.editText1.getText().toString();
                mVoiceBroadcast.playVoice(text);
                break;
            case R.id.button2:
                batchSpeak();
                break;
            case R.id.male:// 男声
                mVoiceBroadcast.setSoundType(1);
                break;
            case R.id.female:// 女声
                mVoiceBroadcast.setSoundType(0);
                break;
            default:
                break;
        }

    }

    private void batchSpeak() {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        bags.add(mVoiceBroadcast.getSpeechSynthesizeBag("123456"));
        bags.add(mVoiceBroadcast.getSpeechSynthesizeBag("你好"));
        bags.add(mVoiceBroadcast.getSpeechSynthesizeBag("使用百度语音合成SDK"));
        bags.add(mVoiceBroadcast.getSpeechSynthesizeBag("hello"));
        bags.add(mVoiceBroadcast.getSpeechSynthesizeBag("这是一个demo工程"));
        mVoiceBroadcast.batchSpeak(bags);
    }

}
