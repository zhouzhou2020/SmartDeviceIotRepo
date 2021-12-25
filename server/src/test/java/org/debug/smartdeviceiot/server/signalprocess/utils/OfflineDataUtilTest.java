package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.debug.smartdeviceiot.server.signalprocess.utils.OfflineDataUtil.extractShortsArray;
import static org.debug.smartdeviceiot.server.signalprocess.utils.OfflineDataUtil.fileToByteArray;

public class OfflineDataUtilTest extends BaseTest {

    @Test
    public void test(){
        FileWriter osw=null;
        try {
            osw = new FileWriter("D:\\水平线圈data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读文件到字节数组
        byte[] datas = fileToByteArray("D:\\MyFile\\LaborWork\\智能装备项目开发\\datasets\\LSdata\\启动水平摆放\\" +
                "channel3线圈数据40960short_sTime1607326916819");


        System.out.println("---------转short[]----------");
        short[] shorts = extractShortsArray(datas);


        //打印到文件
        try{
            for (int i = 0; i < shorts.length-1; i++) {
                osw.write(String.valueOf(shorts[i])+"\n");
                osw.flush();
            }
            osw.write(String.valueOf(shorts[shorts.length-1]));
            osw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("data保存完成");
    }

}
