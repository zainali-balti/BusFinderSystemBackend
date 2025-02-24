package com.example.Bus.Finder.System.service.Mail;

import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

        private static final String CLIENT_ID = "7119974607-6pte2athd92312hobp17q42klp28g3jo.apps.googleusercontent.com";

        public GoogleIdToken.Payload verifyGoogleToken(String idToken) {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            try {
                GoogleIdToken googleIdToken = verifier.verify(idToken);
                if (googleIdToken != null) {
                    return googleIdToken.getPayload();
                }
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}


