package com.example;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ApiGateway {
    private static final int PORT = 8080;

    private static final int TRIP_SERVICE_PORT = 8082;
    private static final int USER_SERVICE_PORT = 8081;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT, 10, InetAddress.getByName("127.0.0.1"));
        System.out.println("EatSleepAndHike API Gateway started on port " + PORT);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                (new RequestHandler(clientSocket)).run();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class RequestHandler implements Runnable {
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        public RequestHandler(Socket socket) throws IOException {
            this.socket = socket;
            socket.setSoTimeout(200);
        }

        public void run(){
            try{
                Socket serviceSocket = null;
                while(!socket.isClosed() && socket.isConnected()){
                    StringBuilder sb = new StringBuilder();
                    String line;
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    line = reader.readLine();
                    if (line.contains("/users") || line.contains("/profile")
                            || line.contains(" /login ") || line.contains("/email-check")
                            || line.contains(" /register ")) {
                        serviceSocket = new Socket("localhost", USER_SERVICE_PORT);
                        line = line.replace("\r\nHost: localhost:8080","\r\nHost: localhost:" + USER_SERVICE_PORT);
                    } else {
                        serviceSocket = new Socket("localhost", TRIP_SERVICE_PORT);
                        line = line.replace("\r\nHost: localhost:8080","\r\nHost: localhost:" + TRIP_SERVICE_PORT);
                    }
                    sb.append(line).append("\r\n");

                    if (line.startsWith("OPTIONS")){
                        sendResponseForOptions(socket);
                        continue;
                    }

                    boolean isChunked = false;
                    int contentLength = 0;
                    while(!Objects.equals(line = reader.readLine(), "")){
                        if (line.startsWith("Host: ")){
                            if (serviceSocket.getLocalPort() == USER_SERVICE_PORT)
                                line = "Host: localhost:" + USER_SERVICE_PORT;
                            else
                                line = "Host: localhost:" + TRIP_SERVICE_PORT;
                        }
                        if (line.startsWith("Content-Length: ")){
                            contentLength = Integer.parseInt(line.substring("Content-Length: ".length()));
                        }
                        if(line.equals("Transfer-Encoding: chunked"))
                            isChunked = true;
                        sb.append(line).append("\r\n");
                    }
                    sb.append("\r\n");
                    System.out.println("|-------| Request |-------|");
                    System.out.println(sb);


                    ArrayList<Character> data = new ArrayList<>();
                    int charsRead = 0;
                    char[] buffer = new char[8192];

                    BufferedWriter serviceWriter = new BufferedWriter(new OutputStreamWriter(serviceSocket.getOutputStream()));
                    serviceWriter.write(sb.toString());

                    if (isChunked){
                        int cur = 0;
                        int prev = 0; // \n - 10   \r - 13
                        int grandPrev = 0;
                        while((cur = reader.read()) != -1){
                            serviceWriter.write(cur);
                            System.out.print(cur);
                            if (cur == 10 && prev == 13 && grandPrev == 10){
                                break;
                            }
                            grandPrev = prev;
                            prev = cur;
                        }
                    } else{
                        while(contentLength > 0 && (charsRead = reader.read(buffer)) != -1){
                            for (int i = 0; i < charsRead; i++){
                                data.add(buffer[i]);
                            }
                            contentLength -= (new String(buffer, 0, charsRead)).getBytes(StandardCharsets.UTF_8).length;
                        }
                        char[] charData = new char[data.size()];
                        for (int i = 0; i < data.size(); i++) {
                            charData[i] = data.get(i);
                        }
                        serviceWriter.write(charData);
                    }
                    serviceWriter.flush();

                    isChunked = false;
                    serviceSocket.setSoTimeout(200);
                    BufferedReader serviceReader = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                    sb = new StringBuilder();
                    while(!Objects.equals(line = serviceReader.readLine(), "")){
                        if (line.startsWith("Content-Length: ")){
                            contentLength = Integer.parseInt(line.substring("Content-Length: ".length()));
                        }
                        if(line.equals("Transfer-Encoding: chunked"))
                            isChunked = true;
                        sb.append(line).append("\r\n");
                    }
                    sb.append("\r\n");
                    System.out.println("|-------| Response |-------|");
                    System.out.println(sb);

                    writer.write(sb.toString());
                    if (isChunked){
                        int cur = 0;
                        int prev = 0; // \n - 10   \r - 13
                        int grandPrev = 0;
                        while((cur = serviceReader.read()) != -1){
                            writer.write(cur);
                            System.out.print(cur);
                            if (cur == 10 && prev == 13 && grandPrev == 10){
                                break;
                            }
                            grandPrev = prev;
                            prev = cur;
                        }
                    }
                    else{
                        data.clear();
                        while(contentLength > 0 && (charsRead = reader.read(buffer)) != -1){
                            for (int i = 0; i < charsRead; i++){
                                writer.write(buffer[i]);
                                System.out.print(buffer[i]);
                            }
                            contentLength -= (new String(buffer, 0, charsRead)).getBytes(StandardCharsets.UTF_8).length;;
                        }
                    }
                    writer.flush();
                }

            } catch (Exception ex){

            }
        }

        private void sendResponseForOptions(Socket socket) throws IOException{
            String headers = """
                    HTTP/1.1 200 OK
                    Access-Control-Allow-Origin: http://localhost:5173
                    Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
                    Access-Control-Allow-Headers: Authorization, Content-Type
                    Access-Control-Allow-Credentials: true
                    
                    """;
            socket.getOutputStream().write(headers.getBytes(StandardCharsets.UTF_8));
            socket.getOutputStream().flush();

        }

    }
}
