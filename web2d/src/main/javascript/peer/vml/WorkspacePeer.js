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

web2d.peer.vml.WorkspacePeer = function(element)
{
    var vmlElement = window.document.createElement('v:group');
    this._element = element;
    web2d.peer.vml.ElementPeer.call(this, vmlElement);
};

objects.extend(web2d.peer.vml.WorkspacePeer, web2d.peer.vml.ElementPeer);

// Note: For some reason, VML groups are not able to receive events such as "onclick".
// As a workaround, all the workspace events will be registered in the container div element.
web2d.peer.vml.WorkspacePeer.prototype.getElementToAttachEvent = function()
{
    return this._element;
};

web2d.peer.vml.WorkspacePeer.prototype.setCoordOrigin = function(x, y)
{
    this._native.coordorigin = x + "," + y;
};

web2d.peer.vml.WorkspacePeer.prototype.setCoordSize = function(width, height)
{
    this._native.coordsize = width + "," + height;
    web2d.peer.utils.EventUtils.broadcastChangeEvent(this, "onChangeCoordSize");
    //web2d.peer.utils.EventUtils.broadcastChangeEvent(this, "textSize");
};

web2d.peer.vml.WorkspacePeer.prototype.getCoordSize = function()
{
    var coordSize = this._native.coordsize + "";
    var coord;
    if (coordSize)
    {
        coord = coordSize.split(",");
    } else
    {
        coord = [1,1];
    }

    return { width:coord[0], height:coord[1] };
};

web2d.peer.vml.WorkspacePeer.prototype.setSize = function(width, height)
{
    web2d.peer.vml.ElipsePeer.superClass.setSize.call(this, width, height);
};

web2d.peer.vml.WorkspacePeer.prototype.appendChild = function(child)
{
    web2d.peer.vml.WorkspacePeer.superClass.appendChild.call(this, child);
    web2d.peer.utils.EventUtils.broadcastChangeEvent(child, "onChangeCoordSize");
};

web2d.peer.vml.WorkspacePeer.prototype.getCoordOrigin = function()
{
    var coordOrigin = this._native.coordorigin + "";
    var coord;
    if (coordOrigin)
    {
        coord = coordOrigin.split(",");
    } else
    {
        coord = [1,1];
    }

    var y = parseFloat(coord[1]);
    var x = parseFloat(coord[0]);
    return { x:x, y:y };
};

web2d.peer.vml.WorkspacePeer.prototype.getPosition = function()
{
    return {x:0,y:0};
};
