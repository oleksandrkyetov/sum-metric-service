package code.challenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class PingController {

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public ResponseEntity<?> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }

}
