/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* $Id: file 64488 2006-03-10 17:32:09Z paulo $
*/

package com.wisemapping.service;

import com.wisemapping.dao.UserManager;
import com.wisemapping.exceptions.WiseMappingException;
import com.wisemapping.mail.Mailer;
import com.wisemapping.model.User;
import com.wisemapping.model.Colaborator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl
        implements UserService {
    private UserManager userManager;
    private MindmapService mindmapService;
    private Mailer mailer;

    public void activateAcount(long code)
        throws InvalidActivationCodeException 
    {
        final User user = userManager.getUserByActivationCode(code);
        if (user == null || user.isActive()) {
            throw new InvalidActivationCodeException("Invalid Activation Code");
        } else {
            final Calendar now = Calendar.getInstance();
            user.setActivationDate(now);
            userManager.updateUser(user);
            final Map<String, User> model = new HashMap<String, User>();
            model.put("user", user);
            mailer.sendEmail(mailer.getRegistrationEmail(), user.getEmail(), "WiseMapping : Active account", model, "activationAccountMail.vm");
        }
    }

    public User reloadUser(final User user) {
        return this.getUserBy(user.getId());
    }

    public void sendEmailPassword(String email)
            throws InvalidUserEmailException {
        final User user = userManager.getUserBy(email);
        if (user != null) {
            final Map<String, Object> model = new HashMap<String, Object>();
            final String password = randomstring(8, 10);
            user.setPassword(password);
            changePassword(user);
            model.put("user", user);
            model.put("password", password);
            
            mailer.sendEmail(mailer.getRegistrationEmail(), user.getEmail(), "WiseMapping : Recovery Password", model, "recoveryMail.vm");
        } else {
            throw new InvalidUserEmailException("The email '" + email + "' does not exists.");
        }
    }

    private String randomstring(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
            b[i] = (byte) rand('@', 'Z');
        return new String(b);
    }

    private int rand(int lo, int hi) {
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0)
            i = -i;
        return lo + i;
    }

    public void createUser(User user) throws WiseMappingException {
        final UUID uuid = UUID.randomUUID();
        user.setCreationDate(Calendar.getInstance());
        user.setActivationDate(null);
        user.setActivationCode(uuid.getLeastSignificantBits());

        Colaborator col = userManager.getColaboratorBy(user.getEmail());
        if (col != null)
        {
            userManager.createUser(user,col);
        }
        else
        {
            userManager.createUser(user);
        }

        //create welcome map
        mindmapService.addWelcomeMindmap(user);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        // TODO: ver como no hacer hardcode el url
        final String activationUrl = "http://wisemapping.com/c/activation.htm?code=" + user.getActivationCode();
        model.put("emailcheck", activationUrl);
        mailer.sendEmail(mailer.getRegistrationEmail(), user.getEmail(), "Welcome to Wisemapping!", model,
                "confirmationMail.vm");
        mailer.sendEmail(mailer.getRegistrationEmail(), mailer.getRegistrationEmail(), "Wisemapping : New User", model,
                "activeUserAccountMail.vm");
    }

    public void changePassword(User user) {
        userManager.updateUser(user);
    }

    public User getUserBy(String email) {
        return userManager.getUserBy(email);
    }

    public User getUserByUsername(String username) {
        return userManager.getUserByUsername(username);
    }

    public User getUserBy(long id) {
        return userManager.getUserBy(id);
    }

    public void updateUser(User user) {
        userManager.updateUser(user);
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    public void setMindmapService(MindmapService mindmapService) {
        this.mindmapService = mindmapService;
    }
}
