package com.chygo.rpc.provider.service;

import com.chygo.rpc.api.SimpleResponse;

import java.util.Random;

/**
 *
 * The implemenation of SimpleResponse interface.
 *
 * @author jingjiejiang
 * @history Aug 23, 2021
 *
 */
public class SimpleResponseImpl implements SimpleResponse {

    @Override
    public String response(String word) {

        Random random = new Random();
        int millSec = random.nextInt(200);
        try {
            Thread.sleep(millSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Request is received: " + word + ", server sleeps for " + millSec + "ms");
        return word;
    }
}
