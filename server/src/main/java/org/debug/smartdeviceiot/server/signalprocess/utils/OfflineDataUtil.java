package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Dao;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.SignalFileDao;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class OfflineDataUtil {

    /**
     * 从文件读取数据到byte数组
     * */
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

    /**
     * bytes数组转short数组
     * */
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



    /**
     * 从文件中读取一组信号
     * */
    public static Signal importSignalFromFile(String fname) {
        SignalFileDao signalFileDao = new SignalFileDao();
        Signal signal = signalFileDao.read(fname);
        return signal;
    }

    /**
     * 把一组信号存到目标文件
     * */
    private static void saveSignalToFile(final Signal signal,String fname) {
        Dao dao = new SignalFileDao();
        dao.write(signal, fname);

//        File file = fileChooser.showSaveDialog(stage1);
//        dao.write(signal, file.getPath());
    }


    /**
     * bytes数组存入文件中
     * */
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

    //输入流读取数据到数组
    private static final byte[] readFully(InputStream in, byte[] dst) throws IOException {
        return readFully(in, dst, 0, dst.length);
    }

    private static final byte[] readFully(InputStream in, byte[] dst, int off, int len) throws IOException {
        int readCount = 0;

        int c;
        for(boolean var5 = false; readCount < len; readCount += c) {
            c = in.read(dst, off + readCount, len - readCount);
            if (c < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("specified length is ").append(len).append(" bytes, ").append("but stream is terminated at ").append(readCount).append(" bytes.");
                throw new EOFException(sb.toString());
            }
        }

        return dst;
    }

    // 从文件读取数据到double array
    public static double[] readFile2Arr(String fileName) {
        ArrayList a1 = new ArrayList<Double>();
        InputStream fin = null;
        try {
            fin = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Scanner s = new Scanner(fin);
        while (s.hasNext())
            a1.add(Double.parseDouble(s.nextLine()));
        int signalLen = a1.size();
        double[] input = new double[signalLen];
        for (int i = 0; i < input.length; i++) {
            // input[i]=1000*(Math.random()-0.5);
            input[i] = (Double) a1.get(i);
        }
        return input;
    }

    // 转储文件
    public static void bytes2file(String srcFname, String destifName) {
        FileWriter osw=null;
        try {
            osw = new FileWriter(destifName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读文件到字节数组
        byte[] datas = fileToByteArray(srcFname);
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
