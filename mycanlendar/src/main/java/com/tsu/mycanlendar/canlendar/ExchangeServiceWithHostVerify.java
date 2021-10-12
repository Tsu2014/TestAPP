package com.tsu.mycanlendar.canlendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.GeneralSecurityException;

import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;

public class ExchangeServiceWithHostVerify extends ExchangeService {

    //不对任何域名做验证
    private final static HostnameVerifier hostnameVerifierWithOutVerfy = new HostnameVerifier(){
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public ExchangeServiceWithHostVerify() {
        super();
    }
    public ExchangeServiceWithHostVerify(ExchangeVersion requestedServerVersion) {
        super(requestedServerVersion);
    }

    protected Registry<ConnectionSocketFactory> createConnectionSocketFactoryRegistry() {
        try {
            return RegistryBuilder.<ConnectionSocketFactory>create().register(EWSConstants.HTTP_SCHEME, new PlainConnectionSocketFactory()).register(EWSConstants.HTTPS_SCHEME, EwsSSLProtocolSocketFactory.build(null,hostnameVerifierWithOutVerfy)).build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(
                    "Could not initialize ConnectionSocketFactory instances for HttpClientConnectionManager", e
            );
        }
    }


}
