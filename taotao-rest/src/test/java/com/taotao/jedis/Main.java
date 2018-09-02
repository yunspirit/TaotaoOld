//package com.taotao.jedis;
////Scanner scanner=new Scanner(System.in);
////        int startx=scanner.nextInt();
////        int starty=scanner.nextInt();
////        String start=scanner.nextLine();
////        int endx=scanner.nextInt();
////        int endy=scanner.nextInt();
////        String end=scanner.nextLine();
////        int row=scanner.nextInt();
////        int col=scanner.nextInt();
////        int robotMap[][]=new int[row][col];
////        for (int i = 0; i <row ; i++) {
////        for (int j = 0; j <col ; j++) {
////        robotMap[i][j]=scanner.nextInt();
////        }
////        }
////编译器版本: Java 1.8.0_66
////        请使用标准输入输出(System.in, System.out)；已禁用图形、文件、网络、系统相关的操作，如java.lang.Process , javax.swing.JFrame , Runtime.getRuntime；不要自定义包名称，否则会报错，即不要添加package answer之类的语句；您可以写很多个类，但是必须有一个类名为Main，并且为public属性，并且Main为唯一的public class，Main类的里面必须包含一个名字为'main'的静态方法（函数），这个方法是程序的入口
////        时间限制: 1S (C/C++以外的语言为: 3 S)   内存限制: 128M (C/C++以外的语言为: 640 M)
////        输入:
////        输入：
////         4 
////        3
////         1,4 
////        2,3 
////        7,3
////        输出:
////        输入：
////        4
////        6
////        1,3
////        1,4
////        2,3
////        5,7
////        5,8
////        6,2
////        输入范例:
////        输出：
////        yes
////        输出范例:
////        输出：
////        no
////
////import sun.awt.image.ImageWatched;
//
//import java.util.LinkedList;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//        int group=scanner.nextInt();
//        int m=scanner.nextInt();
//        boolean yueshu[][]=new boolean[group*2][group*2];
//        for(int i=0;i<m;i++){
//            int x=scanner.nextInt();
//            int y=scanner.nextInt();
//            yueshu[x][y]=true;
//            yueshu[y][x]=true;
//        }
//        System.out.println(help(0,new LinkedList<Integer>(),group,yueshu));
//    }
//    //DFS深度遍历  n是深度
//    public static boolean help(int n, LinkedList<Integer> tmp,int group,boolean yueshu[][]){
//        if(tmp.size()>=group) return true;
//        boolean flag1=false,flag2=false;
//        for (int i = 0; i <n-1 ; i++) {
//            int x=tmp.get(i);
//            if(yueshu[x][2*n-1]==true){
//                flag1=true;
//                break;
//            }
//        }
//
//        for (int i = 0; i <n-1 ; i++) {
//            int x=tmp.get(i);
//            if(yueshu[x][2*n]==true){
//                flag2=true;
//                break;
//            }
//        }
//        if(!flag1 && !flag2) return false;
//        if(!flag1){
//            tmp.add(2*n-1);
//            if(help(n+1, tmp,group, yueshu))  return true;
//            tmp.remove(tmp.size()-1);
//        }
//        if(!flag2){
//            tmp.add(2*n);
//            if(help(n+1, tmp,group, yueshu))  return true;
//            tmp.remove(tmp.size()-1);
//        }
//       return false;
//    }
//
//}
