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

import com.wisemapping.exceptions.WiseMappingException;
import com.wisemapping.service.MindmapService;
import com.wisemapping.view.MindMapInfoBean;
import com.wisemapping.model.MindMap;
import com.wisemapping.model.User;
import com.wisemapping.security.Utils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RenameMindmapController
        extends BaseSimpleFormController {

    //~ Methods ..............................................................................................
    protected Object formBackingObject(HttpServletRequest httpServletRequest) throws Exception {
        final MindMap mindMap = getMindmapFromRequest(httpServletRequest);
        User user = Utils.getUser();
        if (!mindMap.getOwner().equals(user)) {
            throw new IllegalStateException("No enought right to execute this operation");
        }


        return new MindMapInfoBean(mindMap);
    }

    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
            throws ServletException, WiseMappingException {
        final MindMapInfoBean bean = (MindMapInfoBean) command;
        final MindmapService mindmapService = this.getMindmapService();
        mindmapService.updateMindmap(bean.getMindMap(), false);

        return new ModelAndView(getSuccessView());
    }
}
