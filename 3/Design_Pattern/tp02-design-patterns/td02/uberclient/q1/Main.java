package fr.uge.poo.uberclient.q1;

import java.util.List;

public class Main {
    void main(){
        var arnaud = new UberClient.UberClientBuilder()
                .firstName("Arnaud")
                .lastName("Carayol")
                .uid(1)
                .uid()
                .grades(List.of(1,2,5,2,5,1,1,1))
                .emails(List.of("arnaud.carayol@univ-eiffel.fr", "arnaud.carayol@u-pem.fr"))
                .phoneNumbers(List.of("0707070707"))
                .build();
    }
}
