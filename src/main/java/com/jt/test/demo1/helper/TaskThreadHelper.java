package com.jt.test.demo1.helper;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * TaskThreadHelper
 *
 * @author jt
 * @date 2022/5/10
 **/
@Service
public class TaskThreadHelper {
    public List task1() {
        int jobs = 1000001;
        List iList = new LinkedList();
        for (int i = 0 ;i< jobs ;i++) {

            iList.add(i);
        }
        System.out.println(iList.size());
        return iList;
    }

    public List task2() {
        int jobs = 1000001;
        List iList = new LinkedList();
        for (int i = 0 ;i< jobs ;i++) {

            iList.add(i);
        }
        System.out.println(iList.size());
        return iList;
    }
}
