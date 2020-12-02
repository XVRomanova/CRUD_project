package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        DataBase dataBase = new DataBase();
        MyHttpServer server = new MyHttpServer(dataBase);

        server.start();
    }
}
