package com.jt.test.helper;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * IpAddressHelper
 *
 * @Author: jt
 * @Date: 2022/6/15 16:26
 */
@Service
public class IpAddressHelper {

    InetAddress address;

    {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取ip地址
     */
    public String getIp() throws UnknownHostException {
        String hostAddress = address.getHostAddress();
        InetAddress ip = InetAddress.getByName("www.baidu.com");
        InetAddress ip2 = InetAddress.getByName("127.0.0.1");
        String s1 = ip.toString();
        String s2 = ip2.toString();
        System.out.println("ip1:"+s1+"\n"+"ip2:"+s2);
        return  hostAddress;
    }


    /**
     * 获取主机名
     */
    public String getName() throws SocketException {
       String hostName = address.getHostName();
        String macAddress = getMacAddress(address);
        return hostName;
    }

    /**
     * 获取mac的方法
     */
    private String getMacAddress(InetAddress address) throws SocketException {
        //获取网卡并得到mac地址----存在于一个byte数组中
        byte[] hardwareAddress = NetworkInterface.getByInetAddress(address).getHardwareAddress();

        //把byte数组拼装称String
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<hardwareAddress.length;i++){
            if (i !=  0){
                sb.append("-");
            }
            //&0xFF: 保留最后8位的值忽略其余位划分变量
            int i1 = hardwareAddress[i] & 0xFF;
            //十进制转十六进制
            String s = Integer.toHexString(i1);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        return sb.toString().toUpperCase();
    }
}
