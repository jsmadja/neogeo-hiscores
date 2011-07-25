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

package com.anzymus.neogeo.hiscores.authenticator;

import java.io.IOException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.google.common.base.Preconditions;

public class NeoGeoFansAuthenticator {

    private HtmlTextInput loginTextfield;
    private HtmlPasswordInput passwordTextfield;
    private String login;
    private String password;

    public boolean authenticate(String login, String password) throws AuthenticationFailed {
        Preconditions.checkNotNull(login, "login is mandatory");
        Preconditions.checkNotNull(password, "password is mandatory");
        this.login = login;
        this.password = password;
        try {
            WebClient webClient = new WebClient();
            HtmlPage loginPage = (HtmlPage) webClient.getPage("http://www.neogeofans.com/leforum/index.php");
            HtmlForm loginForm = fillForm(loginPage);
            String contentResult = submitForm(loginForm);
            return contentResult.contains("Merci de vous être identifié, " + login + ".");
        } catch (IOException e) {
            throw new AuthenticationFailed(e);
        }
    }

    private String submitForm(HtmlForm loginForm) throws IOException {
        HtmlSubmitInput submitButton = (HtmlSubmitInput) loginForm.getInputByValue("S'identifier");
        HtmlPage resultPage = (HtmlPage) submitButton.click();
        String contentResult = resultPage.asText();
        return contentResult;
    }

    private HtmlForm fillForm(HtmlPage loginPage) {
        HtmlForm loginForm = (HtmlForm) loginPage.getForms().get(0);
        passwordTextfield = (HtmlPasswordInput) loginForm.getInputByName("vb_login_password");
        passwordTextfield.setValueAttribute(password);
        loginTextfield = (HtmlTextInput) loginForm.getInputByName("vb_login_username");
        loginTextfield.setValueAttribute(login);
        return loginForm;
    }

}
