package com.jt.test.junitTest;

import com.jt.test.TestApplicationMapTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * EqualsTest
 *
 * @author jt
 * @date 2022/5/13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationMapTest.class )
public class EqualsTest {
    @Test
    public void equalsAndEqualTo(){
        //八种基本类型以及其包装类
        int a = 1;
        int a1 = 1;
        long b = 2;
        long b1 = 2;
        byte c = 3;
        byte c1 = 3;
        short d = 4;
        short d1 = 4;
        double e = 5.0;
        double e1 = 5.0;
        float f = 6.0f;
        float f1 = 6.0f;
        char g = 7;
        char g1 = 7;
        boolean h = true;
        boolean h1 = true;
        Integer i = 1;
        Integer i1 = 1;
        Long j = 2L;
        Long j1 = 2L;
        Byte k = 3;
        Byte k1 = 3;
        Short l = 4;
        Short l1 = 4;
        Double m = 5.0;
        Double m1 = 5.0;
        Float n = 6.0f;
        Float n1 = 6.0f;
        Character o = 7;
        int i2 = o.hashCode();
        Character o1 = 7;
        o1.hashCode();
        Boolean p = true;
        p.hashCode();
        Boolean p1 = true;

        //==和equals判断
        boolean resulta = a == a1;
        System.out.println(resulta);
        boolean resultb = b == b1;
        boolean resultc = c == c1;
        boolean resultd = d == d1;
        boolean resulte = e == e1;
        boolean resultf = f == f1;
        boolean resultg = g == g1;
        boolean resulth = h == h1;

        //包装类重写了hashcode,用equls比较内容
        boolean resulti = i == i1;
        System.out.println(resulti);
        boolean resultj = j == j1;
        boolean resultk = k == k1;
        boolean resultl = l == l1;
        boolean resultm = m == m1;
        boolean resultn = n == n1;
        boolean resulto = o == o1;
        boolean resultp = p == p1;

        //特殊类型String，String类重写了hashcode方法，返回的不是内存地址而是根据内容计算出来的一个特定值，所以==比较的时候
        //String类的内容相同，返回的hashcode也相同，但是其实他们的内存地址是不同的
        String q = "qqqq";
        String q1 = "qqqq";
        String  Q = "QQQQ";
        boolean resultq = q == q1;
        boolean resultq1 = q1 == "qqqq";
        boolean resultQ = Q.equalsIgnoreCase(q1);
        System.out.println(resultq);
        System.out.println(resultq1);
        System.out.println(resultQ);

        //查看hashcode源码对比
        Object object = new Object();
        int objHash = object.hashCode();
        System.out.println(objHash);
    }
}
