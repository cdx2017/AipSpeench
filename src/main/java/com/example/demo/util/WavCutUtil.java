package com.example.demo.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
/**
 * Created by DX on 2018/9/7.
 */

/**
 * wav��Ƶ�ļ���ȡ����
 * �������ڱ�����Ϊ128kbps��wav��Ƶ�ļ���������Ƶ�ļ���ͷ����Ϣռ�ó���44�ֽڣ�
 *
 * @author cdx
 */
@Component
public class WavCutUtil {

    /**
     * ��ȡwav��Ƶ�ļ�
     *
     * @param sourcefile Դ�ļ���ַ
     * @param targetfile Ŀ���ļ���ַ
     * @param start      ��ȡ��ʼʱ�䣨�룩
     * @param end        ��ȡ����ʱ�䣨�룩
     *                   <p>
     *                   return  ��ȡ�ɹ�����true�����򷵻�false
     */
    public static boolean cut(String sourcefile, String targetfile, int start, int end) {
        try {
            if (!sourcefile.toLowerCase().endsWith(".wav") || !targetfile.toLowerCase().endsWith(".wav")) {
                return false;
            }
            File wav = new File(sourcefile);
            if (!wav.exists()) {
                return false;
            }
            long t1 = getTimeLen(wav);  //��ʱ��(��)
            if (end == -100 || end > t1) {
                end = (int) t1;
            }
            if (start < 0) {
                start = 0;
            }
            if (end <= 0 || start >= t1 || start >= end) {
                return false;
            }
            FileInputStream fis = new FileInputStream(wav);
            long wavSize = wav.length() - 44;  //��Ƶ���ݴ�С��44Ϊ128kbps������wav�ļ�ͷ���ȣ�
            long splitSize = (wavSize / t1) * (end - start);  //��ȡ����Ƶ���ݴ�С
            long skipSize = (wavSize / t1) * start;  //��ȡʱ��������Ƶ���ݴ�С
            int splitSizeInt = Integer.parseInt(String.valueOf(splitSize));
            int skipSizeInt = Integer.parseInt(String.valueOf(skipSize));

            ByteBuffer buf1 = ByteBuffer.allocate(4);  //����ļ���С,4����һ��intռ���ֽ���
            buf1.putInt(splitSizeInt + 36);  //�����ļ�������Ϣ
            byte[] flen = buf1.array();  //�����ļ�����
            ByteBuffer buf2 = ByteBuffer.allocate(4);  //�����Ƶ���ݴ�С��4����һ��intռ���ֽ���
            buf2.putInt(splitSizeInt);  //�������ݳ�����Ϣ
            byte[] dlen = buf2.array();  //�������ݳ���
            flen = reverse(flen);  //���鷴ת
            dlen = reverse(dlen);
            byte[] head = new byte[44];  //����wavͷ����Ϣ����
            fis.read(head, 0, head.length);  //��ȡԴwav�ļ�ͷ����Ϣ
            for (int i = 0; i < 4; i++) {  //4����һ��intռ���ֽ���
                head[i + 4] = flen[i];  //�滻ԭͷ����Ϣ����ļ�����
                head[i + 40] = dlen[i];  //�滻ԭͷ����Ϣ������ݳ���
            }
            byte[] fbyte = new byte[splitSizeInt + head.length];  //��Ž�ȡ����Ƶ����
            for (int i = 0; i < head.length; i++) {  //�����޸ĺ��ͷ����Ϣ
                fbyte[i] = head[i];
            }
            byte[] skipBytes = new byte[skipSizeInt];  //��Ž�ȡʱ��������Ƶ����
            fis.read(skipBytes, 0, skipBytes.length);  //��������Ҫ��ȡ������
            fis.read(fbyte, head.length, fbyte.length - head.length);  //��ȡҪ��ȡ�����ݵ�Ŀ������
            fis.close();

            File target = new File(targetfile);
            if (target.exists()) {  //���Ŀ���ļ��Ѵ��ڣ���ɾ��Ŀ���ļ�
                target.delete();
            }
            FileOutputStream fos = new FileOutputStream(target);
            fos.write(fbyte);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ��ȡ��Ƶ�ļ���ʱ��
     *
     * @param file �ļ�·��
     * @return
     */
    public static long getTimeLen(File file) {
        long tlen = 0;
        if (file != null && file.exists()) {
            Encoder encoder = new Encoder();
            try {
                MultimediaInfo m = encoder.getInfo(file);
                long ls = m.getDuration();
                tlen = ls / 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tlen;
    }

    /**
     * ���鷴ת
     *
     * @param array
     */
    public static byte[] reverse(byte[] array) {
        byte temp;
        int len = array.length;
        for (int i = 0; i < len / 2; i++) {
            temp = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(cut("C:/Users/DX/Desktop/music/nihao.mp3", "C:/Users/DX/Desktop/music/newceshi.wav", 0, -100));
        //System.out.println(cut("C:/Users/DX/Desktop/music/68.wav", "C:/Users/DX/Desktop/music/68.wav", 18,-100));
        /*System.out.println(cut("f:\\111.wav", "f:\\111-cut_20_28.wav", 20, 28));*/
    }

}