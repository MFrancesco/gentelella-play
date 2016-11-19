package com.github.gentelella.play.security;

import com.github.gentelella.play.models.User;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.UsernamePasswordAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by fre on 07/07/16.
 */
public class DbBasedUsernamePasswordAuthenticator implements UsernamePasswordAuthenticator {

    private final CryptoUtils cryptoUtils;

    public DbBasedUsernamePasswordAuthenticator(CryptoUtils cryptoUtils){
        this.cryptoUtils = cryptoUtils;
    }

    @Override
    public void validate(UsernamePasswordCredentials credentials) throws HttpAction {
        if (credentials == null) {
            throwsException("No credential");
        }
        String email = credentials.getUsername();
        String password = credentials.getPassword();
        if (CommonHelper.isBlank(email)) {
            throwsException("Username cannot be blank");
        }
        if (CommonHelper.isBlank(password)) {
            throwsException("Password cannot be blank");
        }


        final CommonProfile profile = new CommonProfile();
        List<User> users = User.find.all();
        if (users.isEmpty()){
            profile.addRole(User.UserType.ADMIN.toString());
            profile.addAttribute("display_name", email);
            profile.addAttribute("email", email);
            profile.addAttribute(Pac4jConstants.USERNAME, email);
            credentials.setUserProfile(profile);
            return;
        }
        //Remember, using CryptoUtils here
        String pwd = cryptoUtils.encrypt(password);
        User user = User.find.where().eq("email", email).eq("password", pwd).findUnique();
        if (user == null){
            throwsException("Invalid credential");
        }
        //User is not null, exist
        //Set id
        profile.setId(user.id);

        //Set role
        if (user.type.equals(User.UserType.ADMIN))
            profile.addRole(User.UserType.ADMIN.toString());
        else
            profile.addRole(User.UserType.USER.toString());

        profile.addAttribute("display_name", user.name);
        profile.addAttribute("email", user.email);
        profile.addAttribute(Pac4jConstants.USERNAME, email);

        credentials.setUserProfile(profile);
    }

    protected void throwsException(final String message) {
        throw new CredentialsException(message);
    }

}
