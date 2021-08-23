package com.chygo.rpc.regsitry.service;

import com.chygo.rpc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Read and store services imeplementation under "service" of "provider" in a mapper.
 *
 * @author jingjiejiang
 * @history Aug 23, 2021
 *
 */
public class SvcsProviderLoader {
    private static final Logger logger = LoggerFactory.getLogger(SvcsProviderLoader.class);

    // Store service provider instance
    private static Map<String, String> instanceCacheMap = new ConcurrentHashMap<>();

    // Store the names of provide classes
    private static List<String> providerClassList = new ArrayList<>();

    static {
        loadSvcsProviderInstance(Util.PACKAGE_PREFIX);
    }

    private static void loadSvcsProviderInstance(String packageName) {

    }
}
