package com.company;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MyHttpServer {

    private final DataBase dataBase;
    private final com.sun.net.httpserver.HttpServer server;

    public MyHttpServer(DataBase dataBase) throws IOException {
        this.dataBase = dataBase;

        server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        HttpContext contextGet = server.createContext("/get");
        HttpContext contextAdd = server.createContext("/post");
        HttpContext contextDelete = server.createContext("/delete");
        HttpContext contextUpdate = server.createContext("/put");

        contextGet.setHandler(this::getAllDataFromAccounts);
        contextAdd.setHandler(this::addNewAccount);
        contextDelete.setHandler(this::deleteAccount);
        contextUpdate.setHandler(this::updateAccountBalance);

    }

    public void start() {
        server.start();
    }

    private void getAllDataFromAccounts(HttpExchange httpExchange) throws IOException {
        try {
            ArrayList<Account> accounts = dataBase.getAllDataFromAccounts();
            String json = new Gson().toJson(accounts);

            httpExchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(json.getBytes());

            outputStream.close();

        } catch (RuntimeException e) {
            httpExchange.sendResponseHeaders(500, -1);
        }


    }

    private void updateAccountBalance(HttpExchange httpExchange) throws IOException {

        StringBuilder bodyJSON = generateBodyJSON(httpExchange);

        try {
            JSONObject response = new JSONObject(bodyJSON.toString());

            Account account = new Account();
            account.setName(response.getString("name"));
            account.setBalance(response.getInt("balance"));
            dataBase.updateAccountBalance(account);

            httpExchange.sendResponseHeaders(200, -1);

        } catch (RuntimeException e) {
            httpExchange.sendResponseHeaders(500, -1);
        }

    }

    private void deleteAccount(HttpExchange httpExchange) throws IOException {

        StringBuilder bodyJSON = generateBodyJSON(httpExchange);

        try {
            JSONObject response = new JSONObject(bodyJSON.toString());

            Account account = new Account();
            account.setName(response.getString("name"));
            dataBase.deleteAccount(account.getName());

            httpExchange.sendResponseHeaders(200, -1);

        } catch (RuntimeException e) {
            httpExchange.sendResponseHeaders(500, -1);
        }

    }

    private void addNewAccount(HttpExchange httpExchange) throws IOException {

        StringBuilder bodyJSON = generateBodyJSON(httpExchange);

        try {
            JSONObject response = new JSONObject(bodyJSON.toString());

            Account account = new Account();
            account.setName(response.getString("name"));
            account.setBalance(response.getInt("balance"));

            dataBase.addNewAccount(account);

            httpExchange.sendResponseHeaders(200, -1);

        } catch (RuntimeException e) {
            httpExchange.sendResponseHeaders(500, -1);
        }
    }

    private StringBuilder generateBodyJSON(HttpExchange httpExchange) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8));

        StringBuilder bodyJSON = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            bodyJSON.append(line);
        }

        bufferedReader.close();

        return bodyJSON;
    }
}
