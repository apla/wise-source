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

public class PublicPagesController extends BaseMultiActionController {

    public ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException noSuchRequestHandlingMethodException, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return this.home(httpServletRequest, httpServletResponse);
    }

    public ModelAndView faq(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("faq");
    }

    public ModelAndView aboutUs(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("aboutUs");
    }

    public ModelAndView video(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("video");
    }

    public ModelAndView crew(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("crew");
    }

    public ModelAndView privacyPolicy(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("privacyPolicy");
    }

    public ModelAndView home(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("homepage");
    }

    public ModelAndView tryEditor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        final MindMap mindmap = getMindmapService().getMindmapById(TRY_EXAMPLE_MINDMAP_ID);

        ModelAndView view = new ModelAndView("mindmapEditor", "mindmap", mindmap);
        final String xmlMap = mindmap.getNativeXmlAsJsLiteral();
        view.addObject(MindmapEditorController.MAP_XML_PARAM, xmlMap);
        view.addObject("editorTryMode", true);
        view.addObject("showHelp", true);
        return view;
    }

    public ModelAndView termsOfUse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("termsOfUse");
    }

    public static final int TRY_EXAMPLE_MINDMAP_ID = 3;

}
