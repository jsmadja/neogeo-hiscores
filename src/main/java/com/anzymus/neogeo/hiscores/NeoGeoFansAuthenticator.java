/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anzymus.neogeo.hiscores;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.MediaType;
import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class NeoGeoFansAuthenticator {

    private static final String LOGIN_FORM_URL = "http://www.neogeofans.com/leforum/login.php?do=login";
    private static final String FORM_USERNAME = "vb_login_username";
    private static final String FORM_PASSWORD = "vb_login_password";

    private static final String FORM_HIDDEN_S = "s";
    private static final String FORM_HIDDEN_DO = "do";
    private static final String FORM_HIDDEN_VB_LOGIN_MD5PASSWORD = "vb_login_md5password";
    private static final String FORM_HIDDEN_VB_LOGIN_MD5PASSWORD_UTF = "vb_login_md5password_utf";

    boolean authenticate(String login, String password) {
        Preconditions.checkNotNull(login, "login is mandatory");
        Preconditions.checkNotNull(password, "password is mandatory");

        Client client = Client.create();

        Form form = new Form();
        form.add(FORM_USERNAME, login);
        form.add(FORM_PASSWORD, password);
        form.add(FORM_HIDDEN_S, "");
        form.add(FORM_HIDDEN_DO, "login");
        form.add(FORM_HIDDEN_VB_LOGIN_MD5PASSWORD, md5(password));
        form.add(FORM_HIDDEN_VB_LOGIN_MD5PASSWORD_UTF, md5(password));

        WebResource r = client.resource(LOGIN_FORM_URL);
        String s = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class, form);
        System.err.println(s);
        return s.contains("Bienvenue");
    }

    private static String md5(String text) {
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            byte[] digest = msgDigest.digest(text.getBytes());
            for (int i = 0; i < digest.length; ++i) {
                int value = digest[i];
                if (value < 0) {
                    value += 256;
                }
                sb.append(Integer.toHexString(value));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
