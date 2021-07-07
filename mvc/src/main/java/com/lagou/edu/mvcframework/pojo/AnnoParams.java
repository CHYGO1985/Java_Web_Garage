package com.lagou.edu.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 *
 * The class is to store annotation parameters for each method.
 *
 * @author jingjiejiang
 * @history Jul 7, 2021
 *
 */
public class AnnoParams {

    private Set<String> usernames;

    public AnnoParams(Set<String> usernames) {
        this.usernames = usernames;
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Set<String> usernames) {
        this.usernames = usernames;
    }
}
