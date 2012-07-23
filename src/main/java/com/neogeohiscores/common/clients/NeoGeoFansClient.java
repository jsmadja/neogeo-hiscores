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

package com.neogeohiscores.common.clients;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

public class NeoGeoFansClient {

    private HtmlTextInput loginTextfield;
    private HtmlPasswordInput passwordTextfield;
    protected String login;
    private String password;
    protected WebClient webClient;

    private static final Logger LOG = LoggerFactory.getLogger(NeoGeoFansClient.class);

    protected void init() {
    }

    public boolean authenticate(String login, String password) throws AuthenticationFailed {
        Preconditions.checkNotNull(login, "login is mandatory");
        Preconditions.checkNotNull(password, "password is mandatory");
        this.login = login;
        this.password = password;
        try {
            webClient = new WebClient();
            HtmlPage loginPage = getLoginPage();
            HtmlForm loginForm = fillForm(loginPage);
            String contentResult = submitForm(loginForm);
            return contentResult.contains("Merci de vous être identifié, " + login + ".");
        } catch (IOException e) {
            LOG.error(login + " has not successfully logged in", e);
            throw new AuthenticationFailed(e);
        }
    }

    protected HtmlPage getLoginPage() throws IOException, MalformedURLException {
        return (HtmlPage) webClient.getPage("http://www.neogeofans.com/leforum/index.php");
    }

    private HtmlForm fillForm(HtmlPage loginPage) {
        HtmlForm loginForm = (HtmlForm) loginPage.getForms().get(0);
        passwordTextfield = (HtmlPasswordInput) loginForm.getInputByName("vb_login_password");
        passwordTextfield.setValueAttribute(password);
        loginTextfield = (HtmlTextInput) loginForm.getInputByName("vb_login_username");
        loginTextfield.setValueAttribute(login);
        return loginForm;
    }

    protected String submitForm(HtmlForm loginForm) throws IOException {
        HtmlSubmitInput submitButton = (HtmlSubmitInput) loginForm.getInputByValue("S'identifier");
        HtmlPage resultPage = (HtmlPage) submitButton.click();
        String contentResult = resultPage.asText();
        return contentResult;
    }

    public void post(String message, long postId) {
        try {
            HtmlPage topicPage = (HtmlPage) webClient.getPage("http://www.neogeofans.com/leforum/showthread.php?t=" + postId);
            activateForm(topicPage);
            addMessage(message, topicPage);
            removeSignature(topicPage);
            submitPost(topicPage);
        } catch (Exception e) {
            LOG.error("Can't post message:'" + message + "' in postId:" + postId + " by " + login, e);
        }
    }

    private void activateForm(HtmlPage topicPage) throws IOException {
        HtmlAnchor anchor = findAnchor(topicPage);
        anchor.click();
    }

    private HtmlAnchor findAnchor(HtmlPage topicPage) {
        List<HtmlAnchor> anchors = topicPage.getAnchors();
        for (HtmlAnchor anchor : anchors) {
            String id = anchor.getId();
            if (id.startsWith("qr_")) {
                return anchor;
            }
        }
        return null;
    }

    private void addMessage(String message, HtmlPage topicPage) {
        HtmlTextArea textArea = (HtmlTextArea) topicPage.getHtmlElementById("vB_Editor_QR_textarea");
        textArea.setText(message);
    }

    private void removeSignature(HtmlPage topicPage) throws IOException {
        try {
            HtmlCheckBoxInput signature = (HtmlCheckBoxInput) topicPage.getHtmlElementById("cb_signature");
            if (signature.isChecked()) {
                signature.click();
            }
        } catch (ElementNotFoundException e) {
            LOG.error("Can't find signature check box in:\n" + topicPage.asXml());
        }
    }

    private void submitPost(HtmlPage topicPage) throws IOException {
        try {
            HtmlForm postForm = (HtmlForm) topicPage.getForms().get(2);
            HtmlSubmitInput submitButton = (HtmlSubmitInput) postForm.getInputsByName("sbutton").get(0);
            submitButton.click();
        } catch (ElementNotFoundException e) {
            LOG.error("Can't find post button in:\n" + topicPage.asXml());
        }
    }

    public void post(String message) {
        post(message, 41930L);
    }

}
