package org.springframework.context.support;

import tasks.roller.Roller;

public class AbstractApplicationContext {
    public Roller getBean(Class<Roller> rolerClass) {
        return null;
    }

    public void close() {
        // mock
    }
}
