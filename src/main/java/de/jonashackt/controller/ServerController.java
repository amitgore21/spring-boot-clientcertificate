package de.jonashackt.controller;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;
import java.util.Map;

@RestController
public class ServerController {

    public static final String RESPONSE = "Hello Rest-User!";

    @RequestMapping(path="/**",method = RequestMethod.POST)
    public String helloWorld(HttpServletRequest request,@RequestHeader Map<String, String> headers) {
        System.out.println("Rocking REST!");

        headers.forEach((key,value)->{
            System.out.println("Header Name: "+key+"| value: "+value);
        });
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if (null != certs && certs.length > 0) {
            X509Certificate clientcert=certs[0];
            X500Principal subjetDN=clientcert.getSubjectX500Principal();
            System.out.println("subject DN of X509cert: "+ subjetDN.getName());
        }

        X509Certificate[] certs1 = (X509Certificate[]) request
                .getAttribute("javax.servlet.request.X509Certificate");
        if (certs1 != null) {
            for (int i = 0; i < certs1.length; i++) {
                System.out.println("Client Certificate [" + i + "] = " + certs1[i].toString());
            }
        } else {
            if ("https".equals(request.getScheme())) {
                System.out.println("This was an HTTPS request, " + "but no client certificate is available");
            } else {
                System.out.println("This was not an HTTPS request, " + "so no client certificate is available");
            }
        }
        return RESPONSE;
    }
}
