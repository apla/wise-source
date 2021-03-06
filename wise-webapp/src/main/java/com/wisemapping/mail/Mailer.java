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

// ...........................................................................................................
// (C) Copyright  1996/2007 Fuego Inc.  All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Fuego Inc.
// The copyright notice above does not evidence any actual or intended
// publication of such source code.
//
// Last changed on 2007-04-03 09:29:20 (-0300), by: nachomanz. $Revision$
// ...........................................................................................................

package com.wisemapping.mail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

public final class Mailer {

    //~ Instance fields ......................................................................................

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String registrationEmail;
    private String siteEmail;

    //~ Methods ..............................................................................................

    public Mailer(String registrationEmail, String siteEmail)
    {
        this.registrationEmail = registrationEmail;
        this.siteEmail = siteEmail;
    }

    public String getRegistrationEmail()
    {
        return registrationEmail;
    }

    public String getSiteEmail()
    {
        return siteEmail;
    }

    public void sendEmail(final String from, final String to, final String subject, final Map model,
                          final String templateMail) {
        final MimeMessagePreparator preparator =
                new MimeMessagePreparator() {
                    public void prepare(MimeMessage mimeMessage)
                            throws Exception {
                        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                        message.setTo(to);
                        message.setFrom(from);
                        message.setSubject(subject);

                        final String text =
                                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mail/" + templateMail,
                                        model);

                        message.setText(text, true);
                    }
                };

        this.mailSender.send(preparator);
    }

    public void setMailSender(JavaMailSender mailer) {
        this.mailSender = mailer;
    }

    public void setVelocityEngine(VelocityEngine engine) {
        this.velocityEngine = engine;
    }
}
