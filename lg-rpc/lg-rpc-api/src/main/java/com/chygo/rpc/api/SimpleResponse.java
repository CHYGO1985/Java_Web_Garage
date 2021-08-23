package com.chygo.rpc.api;

/**
 *
 * The simple response interface for comm between server and client.
 *
 */
public interface SimpleResponse {

    /**
     *
     * Simple response.
     *
     * @param word
     * @return
     */
    String response(String word);
}
