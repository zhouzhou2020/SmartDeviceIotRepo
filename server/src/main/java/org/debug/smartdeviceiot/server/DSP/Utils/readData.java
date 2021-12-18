package org.debug.smartdeviceiot.server.DSP.Utils;

import java.io.*;


public class readData {

    public static short[] extractShortsArray(byte []bytes) {
        int len = bytes.length;
        short[] shorts = new short[len >> 1 + (len & 1)];
        for (int i = 1; i < len; i += 2) {
            byte h = bytes[i-1];
            int l = bytes[i]& 0xff;
            shorts[i >> 1] = (short) (h << 8 | l);
        }
        return shorts;
    }

    public static void main(String[] args) {
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

    public static byte[] fileToByteArray(String filePath) {

        File src = new File(filePath);//获得文件的源头，从哪开始传入(源)
        byte[] dest = null;//最后在内存中形成的字节数组(目的地)
        //选择流
        InputStream is = null;
        ByteArrayOutputStream baos= null;
        try {
            is = new FileInputStream(src);
            baos = new ByteArrayOutputStream();
            byte[] flush = new byte[1024*10];
            int len = -1;
            while((len = is.read(flush)) != -1) {
                baos.write(flush,0,len);
            }
            baos.flush();
            dest = baos.toByteArray();
            return dest;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static void byteArrayToFile(byte[] src,String filePath) {
        File dest = new File(filePath);//目的地，新文件
        InputStream is = null;//ByteArrayInputStream的父类
        OutputStream os = null;
        //操作
        try {
            is = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest);

            byte[] flush = new byte[1024*10];
            int len = -1;
            while((len = is.read(flush)) != -1) {
                os.write(flush,0,len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != os) {//关闭文件流
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
