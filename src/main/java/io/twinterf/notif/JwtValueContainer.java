package io.twinterf.notif;

public class JwtValueContainer {
    private final String signatureValue;
    private final String issuerValue;

    public JwtValueContainer() {
        signatureValue = System.getenv("SIGNATURE_VALUE");
        issuerValue = System.getenv("ISSUER_VALUE");
    }

    public String getSignatureValue() {
        return signatureValue;
    }

    public String getIssuerValue() {
        return issuerValue;
    }
}
