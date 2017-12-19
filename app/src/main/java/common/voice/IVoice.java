package common.voice;

import com.baidu.tts.client.SpeechSynthesizeBag;

import java.util.List;

public interface IVoice {
	/**
	 * 播放声音
	 * @param text
	 * 要播报的内容
	 */
	public int playVoice(String text);
	
	/**
	 * 设置后台播报内容
	 * @param bstext
	 * 内容
	 * @return
	 */
	public SpeechSynthesizeBag getSpeechSynthesizeBag(String bstext);
	
	/**
	 * 后台播报内容
	 * @param bags
	 * 内容集合
	 * @return
	 */
	public int batchSpeak(List<SpeechSynthesizeBag> bags);
	/**
	 * 设置声音性别
	 * @param i 
	 * 0--普通女声，1--普通男声，2--特别男声，3--情感男声
	 */
	public void setSoundType(int i);
	/**
	 * 停止播放
	 */
	public void stopVoice();
	/**
	 * 释放类
	 * 
	 */
	public void release();
}
