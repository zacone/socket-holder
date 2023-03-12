package com.isthisone.socketholder.sample.netty;

import com.isthisone.socketholder.broadcast.client.SocketHolderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class NettySampleApplication {

    private final SocketHolderClient socketHolderClient;

    public static void main(String[] args) {

        SpringApplication.run(NettySampleApplication.class, args);
    }

    @GetMapping("/broadcast")
    public void broadcast(String msg) {
        socketHolderClient.broadcast(msg);
    }

}
