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

package com.wisemapping.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wisemapping.model.MindMap;
import com.wisemapping.filter.UserAgent;

import java.lang.reflect.UndeclaredThrowableException;

public class EmbeddedViewController extends BaseMultiActionController {

    protected ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException noSuchRequestHandlingMethodException, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        ModelAndView view;
        try {
            final MindMap mindmap = this.getMindmapFromRequest(httpServletRequest);
            if (mindmap == null) {
                throw new IllegalStateException("Map could not be found");
            }

            final String fullViewStr = httpServletRequest.getParameter("fullView");
            final boolean fullView = Boolean.parseBoolean(fullViewStr);

            final UserAgent userAgent = UserAgent.create(httpServletRequest);

            if (userAgent.isBrowserSupported()) {
                view = new ModelAndView("embeddedView");
                view.addObject("mindmap", mindmap);
                final String xmlMap = mindmap.getNativeXmlAsJsLiteral();
                view.addObject("mapXml", xmlMap);

                final String zoomStr = httpServletRequest.getParameter("zoom");
                float zoom = 1;
                if(zoomStr!=null)
                {
                    try {
                        zoom = Float.parseFloat(zoomStr);
                    } catch (NumberFormatException e) {
                    }
                }
                view.addObject("zoom",zoom);

            } else {

                view = new ModelAndView("embeddedViewImg");
                view.addObject("mindmap", mindmap);
            }
            view.addObject("fullView", fullView);

        } catch (UndeclaredThrowableException e) {
            // Security exception ....
            view = new ModelAndView("embeddedViewError");
        }

        return view;
    }
}
