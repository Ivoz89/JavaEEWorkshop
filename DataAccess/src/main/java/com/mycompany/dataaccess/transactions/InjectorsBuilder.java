/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.transactions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author izielinski
 */
public class InjectorsBuilder {

    private final Map<Integer, Pair<ParameterType, Object>> paramMap;

    public InjectorsBuilder() {
        paramMap = new HashMap<>();
    }

    public InjectorsBuilder append(int index, ParameterType type, Object o) {
        paramMap.put(index, new Pair<>(type, o));
        return this;
    }

    public Map<Integer, Pair<ParameterType, Object>> toMap() {
        return paramMap;
    }
}
