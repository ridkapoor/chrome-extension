package com.extensionhandler.services;

import org.springframework.stereotype.Service;

/**
 * Created by ridkapoor on 6/16/17.
 */
@Service("urlService")
public class URLCreator {

  /*  @Autowired
    private ConnectionManager connectionManager;

    @PostConstruct
    public void init(){
        connectionManager.registerHost("https://terminal2.expedia.com",);
    }*/

    public String getSignInDeepLinkURL() {
        StringBuffer stringBuffer = new StringBuffer("");

        return stringBuffer.toString();

    }


}
