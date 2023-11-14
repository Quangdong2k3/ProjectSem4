package com.StoreBook.repository;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import com.StoreBook.entity.Persistent_logins;

@Repository
public class CustomPersistentTokenRepository implements PersistentTokenRepository {

    @Autowired
    private PersistentLoginRepository persistentLoginRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        Persistent_logins login = new Persistent_logins();
        
        login.setUsername(token.getUsername());
        login.setSeries(token.getSeries());
        login.setToken(token.getTokenValue());
        login.setLast_used(token.getDate());
        persistentLoginRepository.save(login);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Persistent_logins login = persistentLoginRepository.findById(series).orElse(null);
        if (login != null) {
            login.setToken(tokenValue);
            login.setLast_used(lastUsed);
            persistentLoginRepository.save(login);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        Persistent_logins login = persistentLoginRepository.findById(series).orElse(null);
        if (login != null) {
            return new PersistentRememberMeToken(login.getUsername(), login.getSeries(), login.getToken(), login.getLast_used());
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        persistentLoginRepository.deleteByUsername(username);
    }
}

