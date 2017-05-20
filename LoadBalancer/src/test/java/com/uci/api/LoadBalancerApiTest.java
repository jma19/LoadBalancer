package com.uci.api;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by junm5 on 5/17/17.
 */
public class LoadBalancerApiTest {
    @Test
    public void name() throws Exception {

        long start  = System.currentTimeMillis();
        System.out.println();
        ArrayList<Integer> objects = Lists.newArrayList();
        objects.add(1);
        objects.add(2);
        objects.add(3);
        objects.add(4);

        Iterator<Integer> iterator = objects.iterator();

        while (iterator.hasNext()){
            Integer next = iterator.next();
            if(next == 1){
                iterator.remove();
            }
        }
        System.out.println(System.currentTimeMillis() - start);



    }
}