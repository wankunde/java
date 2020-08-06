package com.wankun.threadlocal;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-07-09.
 */
public class Session {
    private static ThreadLocal<Session> activeSession = new InheritableThreadLocal<Session>() {
        @Override
        protected Session childValue(Session parentValue) {
            for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                if(!element.getClassName().startsWith("java.lang.") && !element.getMethodName().equals("childValue"))
                System.out.println(element);
            }
            return new Session();
        }
    };

    public static Session getActiveSession() {
        Session session = activeSession.get();
        if (activeSession.get() == null) {
            session = new Session();
            activeSession.set(session);
        }
        return session;
    }

    public static void main(String[] args) throws InterruptedException {
        getAndPrintSession();

        Thread childThread = new Thread() {
            @Override
            public void run() {
                getAndPrintSession();
            }
        };
        childThread.start();
        childThread.join();
    }

    public static void getAndPrintSession() {
        Session session = Session.getActiveSession();
        System.out.println(session.hashCode());
    }
}
