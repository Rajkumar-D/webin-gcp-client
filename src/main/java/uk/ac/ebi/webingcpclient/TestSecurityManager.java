package uk.ac.ebi.webingcpclient;

import com.google.cloud.secretmanager.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TestSecurityManager {

    static String projectId = "prj-dev-internal-ena-1";
    static String secretId = "enapro";

    @Value("${enapro.password}")
    private  String enaproPassword;

    @Value("${sm://enapro}")
    private  String directPass;

    public TestSecurityManager(){}

    public void getSecret() throws IOException {
        
        
        System.out.println("enaproPassword: "+enaproPassword);
        System.out.println("directPass: "+directPass);        
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            // Build the name.
            SecretName secretName = SecretName.of(projectId, secretId);

            // Create the secret.
            Secret secret = client.getSecret(secretName);
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, "1");

            // Get the replication policy.
            String replication = "";
            if (secret.getReplication().getAutomatic() != null) {
                replication = "AUTOMATIC";
            } else if (secret.getReplication().getUserManaged() != null) {
                replication = "MANAGED";
            } else {
                throw new IllegalStateException("Unknown replication type");
            }

            System.out.printf("Secret %s, replication %s\n", secret.getName(), replication);

            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
            System.out.println( response.getPayload().getData().toStringUtf8());
        }
    }

}
