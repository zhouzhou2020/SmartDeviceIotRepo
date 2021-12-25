//package org.debug.smartdeviceiot.server.signalprocess.jna;
//
//import com.sun.jna.Library;
//import com.sun.jna.win32.StdCallLibrary;
//
//public class DllService {
//    public interface JNAtestJna extends Library {
//        //对应dll的函数名，名字要一样
//        public double MEAN(double[] data,int fromIndex,int toIndex);
////        public int add(int a, int b);
////        public int dec(int a, int b);
//        //对应的回调函数
//        public void Handle(Cback c);
//
//        //dll的回调函数java只能用回调接口
//        public interface Cback extends StdCallLibrary.StdCallCallback {
//            public void hello();
//        }
//        //下面实现接口
//        public  class  CbackIpm implements Cback{
//            //这个是我们要传入的函数
//            @Override
//            public void hello() {
//                System.out.println("我是回调函数，执行成功");
//            }
//        }
//    }
//}
