package com.codecool.bolla;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler implements HttpHandler {

    @WebRoute(urlPattern = "/", method = "GET")
    public static void rootHandler(HttpExchange httpExchange) throws IOException {
        String response = "This is the root";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(urlPattern = "/test", method = "GET")
    public static void testHandler(HttpExchange httpExchange) throws IOException {
        String response = "This is the test";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(urlPattern = "users", method = "POST")
    public static void usersHandler(HttpExchange httpExchange) throws IOException {
        String response = "This is the root";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        String requestMethod = httpExchange.getRequestMethod();

        Class handler = RequestHandler.class;
        Method[] methods = handler.getDeclaredMethods();

        callingExchangeHandler(uri, requestMethod, methods, httpExchange);
    }

    private void callingExchangeHandler(String uri, String requestMethod, Method[] methods, HttpExchange httpExchange) {
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(WebRoute.class);
            if (isMatchingURLAndMethod(uri, requestMethod, (WebRoute) annotation)) {
                try {
                    System.out.println(method);
                    method.invoke(null, httpExchange);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isMatchingURLAndMethod(String uri, String requestMethod, WebRoute annotation) {
        if (annotation == null) return false;
        return annotation.urlPattern().equals(uri) && annotation.method().equals(requestMethod);
    }

}
