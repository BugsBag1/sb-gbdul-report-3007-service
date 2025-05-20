package kz.govtech.m11s.sb_gbdul_report_3007_service.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtKeyCloakTokenUtils {

    public static KeyCloakTokenClaimsData parseJwtKeyCloakToken(final String accessToken) {
        try {
            final String token;
            if (accessToken.startsWith("Bearer ")) {
                token = accessToken.replaceFirst("Bearer ", "");
            } else {
                token = accessToken;
            }

            final var decodedJWT = SignedJWT  // or PlainJWT or EncryptedJWT
                .parse(token);
            final JWTClaimsSet claimsSet = decodedJWT.getJWTClaimsSet();

            final Date exp = claimsSet.getDateClaim("exp");
            final Date iat = claimsSet.getDateClaim("iat");
            final String jti = claimsSet.getStringClaim("jti");
            final String iss = claimsSet.getStringClaim("iss");
            final String sub = claimsSet.getStringClaim("sub");
            final String typ = claimsSet.getStringClaim("typ");
            final String azp = claimsSet.getStringClaim("azp");
            final String acr = claimsSet.getStringClaim("acr");

            // Realm Access
            final Map<String, Object> realmAccessMap = claimsSet.getJSONObjectClaim("realm_access");
            final ArrayList<String> rolesRealmAccess = (ArrayList<String>) realmAccessMap.get("roles");
            var realmAccess = new RealmAccess(rolesRealmAccess);

            final String scope = claimsSet.getStringClaim("scope");
            final String departmentName = claimsSet.getStringClaim("departmentName");
            final String clientId = claimsSet.getStringClaim("clientId");
            final String clientHost = claimsSet.getStringClaim("clientHost");
            final boolean emailVerified = claimsSet.getBooleanClaim("email_verified");
            final String preferredUsername = claimsSet.getStringClaim("preferred_username");
            final String clientAddress = claimsSet.getStringClaim("clientAddress");

            return new KeyCloakTokenClaimsData(
                exp,
                iat,
                jti,
                iss,
                sub,
                typ,
                azp,
                acr,
                realmAccess,
                null,
                scope,
                departmentName,
                clientId,
                clientHost,
                emailVerified,
                preferredUsername,
                clientAddress
            );
        } catch (final ParseException e) {
            throw new RuntimeException("Invalid token!");
        }
    }

    public static void main(final String[] args) {
        System.out.print(JwtKeyCloakTokenUtils.parseJwtKeyCloakToken(
            "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxTzc1b0VwNnB1NUVaeFk4TlY3WGhEV3dwdHlZWWd2ZEM3OExQb1FtY3F3In0.eyJleHAiOjE3MDU2NzIyNjQsImlhdCI6MTcwNTY3MTk2NCwianRpIjoiNDNmMGIyOTEtMDE2ZC00ODMyLWFkMDItODliMzk3ZjM2NGJiIiwiaXNzIjoiaHR0cHM6Ly9rZXlja2xvYWsuYXBwcy5vc2gtY2xrMDEtdGVzdC5ldWIua3ovcmVhbG1zL2dvdnRlY2gtaW50ZXJuYWwiLCJzdWIiOiJjMzlhOGFhOC03ZTBiLTQ2YWYtOWEyOS1mMmNhZWM5NzRlMzAiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJldWJhbmstY3JlZG8tYXBwIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJnb3Z0ZWNoLWRhdGEtYWNjZXNzLWFncmVlbWVudC1tYW5hZ2UtdjEiLCJzbWFydGJyaWRnZS1nYmRmbC1nZW5lcmljLWZsLXF1ZXJ5LXYxIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiZXViYW5rLWNyZWRvLWFwcCI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImRlcGFydG1lbnROYW1lIjoi0JrQoNCV0JTQniIsImNsaWVudElkIjoiZXViYW5rLWNyZWRvLWFwcCIsImNsaWVudEhvc3QiOiIxMC4xMDQuMTEuMzIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1ldWJhbmstY3JlZG8tYXBwIiwiY2xpZW50QWRkcmVzcyI6IjEwLjEwNC4xMS4zMiJ9.GeZd-HO98RDMfN3X3JIKvBRx1xjbZhzP2IVtWe6zBvf0BPgfgOUspR_bFp3_psppet6H0dOZhoW0GXkDtmSVUwAUHXzuJFtSGZrk70Q6-UL4v4zZiWlI_YWPfiOIeYzl07mR2JkvnKyiblPTWLY0N3Fy4pQq6Wb08DF4nA8GOkDpGxFxY8KRy7IBqrt2Gxd_ZqjcZNlS7x9pIIEH7xjYUkmuU-BywhmeF9tw-KKTYMIVHpFjfVeEfwHmhiRdQlVTGoCzuuzNWrxxAG54D8FZdR-rLSUaNYtjyNXOMmualkgU5YgZZc2sbLif9YG7zmJ-iYaLgztpa3lpmjfpzOsl-w"
        ));
    }

    @Value
    public static class KeyCloakTokenClaimsData {
        private Date exp;
        private Date iat;
        private String jti;
        private String iss;
        private String sub;
        private String typ;
        private String azp;
        private String acr;

        @JsonProperty("realm_access")
        private RealmAccess realmAccess;

        @JsonProperty("resource_access")
        private ResourceAccess resourceAccess;

        private String scope;
        private String departmentName;
        private String clientId;
        private String clientHost;
        private boolean emailVerified;
        private String preferredUsername;
        private String clientAddress;
    }

    @AllArgsConstructor
    public static class RealmAccess {
        private List<String> roles;
    }

    @AllArgsConstructor
    public static class ResourceAccess {
        @JsonProperty("eubank-credo-app")
        private EubankCredoApp eubankCredoApp;
    }

    @AllArgsConstructor
    public static class EubankCredoApp {
        private List<String> roles;
    }

}
