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

mindplot.Workspace = function(profile, screenManager, zoom)
{
    // Create a suitable container ...
    core.assert(screenManager, 'Div container can not be null');
    this._zoom = zoom;
    this._screenManager = screenManager;
    this._screenWidth = profile.width;
    this._screenHeight = profile.height;

    // Initalize web2d workspace.
    var workspace = this._createWorkspace(profile);
    this._workspace = workspace;

    var screenContainer = screenManager.getContainer();
    // Fix the height of the container ....
    screenContainer.style.height = this._screenHeight + "px";

    // Append to the workspace...
    workspace.addItAsChildTo(screenContainer);
    this.setZoom(zoom, true);

    // Register drag events ...
    this._registerDragEvents();

    this._eventsEnabled = true;

};

mindplot.Workspace.prototype._updateScreenManager = function()
{
    var zoom = this._zoom;
    this._screenManager.setScale(zoom);

    var coordOriginX = -((this._screenWidth * this._zoom) / 2);
    var coordOriginY = -((this._screenHeight * this._zoom) / 2);
    this._screenManager.setOffset(coordOriginX, coordOriginY);
};

mindplot.Workspace.prototype._createWorkspace = function(profile)
{
    // Initialize workspace ...
    var coordOriginX = -(this._screenWidth / 2);
    var coordOriginY = -(this._screenHeight / 2);

    var workspaceProfile = {
        width: this._screenWidth + "px",
        height: this._screenHeight + "px",
        coordSizeWidth:this._screenWidth,
        coordSizeHeight:this._screenHeight,
        coordOriginX:coordOriginX,
        coordOriginY:coordOriginY,
        fillColor:'transparent',
        strokeWidth:0};

    web2d.peer.Toolkit.init();
    return  new web2d.Workspace(workspaceProfile);
};

mindplot.Workspace.prototype.appendChild = function(shape)
{
    if (shape.addToWorkspace)
    {
        shape.addToWorkspace(this);
    } else
    {
        this._workspace.appendChild(shape);
    }
};

mindplot.Workspace.prototype.removeChild = function(shape)
{
    // Element is a node, not a web2d element?
    if (shape.removeFromWorkspace)
    {
        shape.removeFromWorkspace(this);
    } else
    {
        this._workspace.removeChild(shape);
    }
};

mindplot.Workspace.prototype.addEventListener = function(type, listener)
{
    this._workspace.addEventListener(type, listener);
};

mindplot.Workspace.prototype.removeEventListener = function(type, listener)
{
    this._workspace.removeEventListener(type, listener);
};

mindplot.Workspace.prototype.getSize = function()
{
    return this._workspace.getCoordSize();
};

mindplot.Workspace.prototype.setZoom = function(zoom, center)
{
    this._zoom = zoom;
    var workspace = this._workspace;

    // Update coord scale...
    var coordWidth = zoom * this._screenWidth;
    var coordHeight = zoom * this._screenHeight;
    workspace.setCoordSize(coordWidth, coordHeight);

    // Center topic....
    var coordOriginX;
    var coordOriginY;
    if (center)
    {
        coordOriginX = -(coordWidth / 2);
        coordOriginY = -(coordHeight / 2);
    } else
    {
        var coordOrigin = workspace.getCoordOrigin();
        coordOriginX = coordOrigin.x;
        coordOriginY = coordOrigin.y;
    }

    workspace.setCoordOrigin(coordOriginX, coordOriginY);

    // Update screen.
    this._screenManager.setOffset(coordOriginX, coordOriginY);
    this._screenManager.setScale(zoom);
};

mindplot.Workspace.prototype.getScreenManager = function()
{
    return this._screenManager;
};


mindplot.Workspace.prototype.enableWorkspaceEvents = function(value)
{
    this._eventsEnabled = value;
};

mindplot.Workspace.prototype.isWorkspaceEventsEnabled = function()
{
    return this._eventsEnabled;
};

mindplot.Workspace.prototype.dumpNativeChart = function()
{
    var workspace = this._workspace;
    return workspace.dumpNativeChart();
};

mindplot.Workspace.prototype._registerDragEvents = function()
{
    var workspace = this._workspace;
    var screenManager = this._screenManager;
    this._dragging = true;
    var mWorkspace = this;
    var mouseDownListener = function(event)
    {
        if (!workspace.mouseMoveListener)
        {
            if (mWorkspace.isWorkspaceEventsEnabled())
            {
                mWorkspace.enableWorkspaceEvents(false);

                var mouseDownPosition = screenManager.getWorkspaceMousePosition(event);
                var originalCoordOrigin = workspace.getCoordOrigin();
                var periodicalFunction = function() {
                    mWorkspace._processMouseMoveEvent = true;
                };
                // Start precision timer updater ...
                mWorkspace._precitionUpdater = periodicalFunction.periodical(mindplot.Workspace.DRAG_PRECISION_IN_SEG);

                workspace.mouseMoveListener = function(event)
                {
                    if (mWorkspace._processMouseMoveEvent)
                    {
                        // Disable mouse move rendering ...
                        mWorkspace._processMouseMoveEvent = false;

                        var currentMousePosition = screenManager.getWorkspaceMousePosition(event);

                        var offsetX = currentMousePosition.x - mouseDownPosition.x;
                        var coordOriginX = -offsetX + originalCoordOrigin.x;

                        var offsetY = currentMousePosition.y - mouseDownPosition.y;
                        var coordOriginY = -offsetY + originalCoordOrigin.y;

                        workspace.setCoordOrigin(coordOriginX, coordOriginY);

                        // Change cursor.
                        if (core.UserAgent.isMozillaFamily())
                        {
                            window.document.body.style.cursor = "-moz-grabbing";
                        } else
                        {
                            window.document.body.style.cursor = "move";
                        }
                    }
                };
                workspace.addEventListener('mousemove', workspace.mouseMoveListener);

                // Register mouse up listeners ...
                workspace.mouseUpListener = function(event)
                {
                    // Stop presition updater listener ...
                    $clear(mWorkspace._precitionUpdater);
                    mWorkspace._precitionUpdater = null;

                    workspace.removeEventListener('mousemove', workspace.mouseMoveListener);
                    workspace.removeEventListener('mouseup', workspace.mouseUpListener);
                    workspace.mouseUpListener = null;
                    workspace.mouseMoveListener = null;
                    window.document.body.style.cursor = 'default';

                    // Update screen manager offset.
                    var coordOrigin = workspace.getCoordOrigin();
                    screenManager.setOffset(coordOrigin.x, coordOrigin.y);
                    mWorkspace.enableWorkspaceEvents(true);
                };
                workspace.addEventListener('mouseup', workspace.mouseUpListener);
            }
        } else
        {
            workspace.mouseUpListener();
        }
    };

    workspace.addEventListener('mousedown', mouseDownListener);
};

mindplot.Workspace.DRAG_PRECISION_IN_SEG = 50;
