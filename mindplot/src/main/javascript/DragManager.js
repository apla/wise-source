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

mindplot.DragManager = function(workspace)
{
    this._workspace = workspace;
    this._listeners = {};
    this._processMouseMoveEvent = true;
    var dragManager = this;
    this._precitionUpdater = null;
};

mindplot.DragManager.prototype.add = function(node)
{
    // Add behaviour ...
    var workspace = this._workspace;
    var screen = workspace.getScreenManager();
    var dragManager = this;

    var mouseDownListener = function(event)
    {
        if (workspace.isWorkspaceEventsEnabled())
        {
            // Disable double drag... 
            workspace.enableWorkspaceEvents(false);

            // Set initial position.
            var dragNode = node.createDragNode();
            var mousePos = screen.getWorkspaceMousePosition(event);
            dragNode.setPosition(mousePos.x, mousePos.y);
            var periodicalFunction = function() {
                dragManager._processMouseMoveEvent = true;
            };
            // Start precision timer updater ...
            dragManager._precitionUpdater = periodicalFunction.periodical(mindplot.DragManager.DRAG_PRECISION_IN_SEG);

            // Register mouse move listener ...
            var mouseMoveListener = dragManager._buildMouseMoveListener(workspace, dragNode, dragManager);
            workspace.addEventListener('mousemove', mouseMoveListener);

            // Register mouse up listeners ...
            var mouseUpListener = dragManager._buildMouseUpListener(workspace, node, dragNode, dragManager);
            workspace.addEventListener('mouseup', mouseUpListener);

            // Execute Listeners ..
            var startDragListener = dragManager._listeners['startdragging'];
            startDragListener(event, node);

            // Change cursor.
            window.document.body.style.cursor = 'move';
        }
    };
    dragManager._mouseMoveListener = mouseDownListener;

    node.addEventListener('mousedown', mouseDownListener);
};

mindplot.DragManager.prototype.remove = function(node)
{
    var nodes = this._topics;
    var contained = false;
    var index = -1;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i] == node) {
            contained = true;
            index = i;
        }
    }
    if (contained)
    {
        elem = new Array();
    }
};

mindplot.DragManager.prototype._buildMouseMoveListener = function(workspace, dragNode, dragManager)
{
    var screen = workspace.getScreenManager();
    var result = function(event) {
        if (dragManager._processMouseMoveEvent)
        {
            // Disable mouse move rendering ...
            dragManager._processMouseMoveEvent = false;
            if (!dragNode._isInTheWorkspace)
            {
                // Add shadow node to the workspace.
                workspace.appendChild(dragNode);
                dragNode._isInTheWorkspace = true;
            }

            var pos = screen.getWorkspaceMousePosition(event);
            dragNode.setPosition(pos.x, pos.y);

            // Call mouse move listeners ...
            var dragListener = dragManager._listeners['dragging'];
            if (dragListener)
            {
                dragListener(event, dragNode);
            }
        }
    };
    dragManager._mouseMoveListener = result;
    return result;
};

mindplot.DragManager.prototype._buildMouseUpListener = function(workspace, node, dragNode, dragManager)
{
    var screen = workspace.getScreenManager();
    var result = function(event) {

        core.assert(dragNode.isDragTopic, 'dragNode must be an DragTopic');

        // Remove drag node from the workspace.
        var hasBeenDragged = dragNode._isInTheWorkspace;
        if (dragNode._isInTheWorkspace)
        {
            dragNode.removeFromWorkspace(workspace);
        }

        // Remove all the events.
        workspace.removeEventListener('mousemove', dragManager._mouseMoveListener);
        workspace.removeEventListener('mouseup', dragManager._mouseUpListener);

        // Help GC
        dragManager._mouseMoveListener = null;
        dragManager._mouseUpListener = null;

        // Execute Listeners only if the node has been moved.
        var endDragListener = dragManager._listeners['enddragging'];
        endDragListener(event, dragNode);

        if (hasBeenDragged)
        {
            dragNode._isInTheWorkspace = false;
        }

        // Stop presition updater listener ...
        $clear(dragManager._precitionUpdater);
        dragManager._precitionUpdater = null;

        // Change the cursor to the default.
        window.document.body.style.cursor = 'default';

        workspace.enableWorkspaceEvents(true);

    };
    dragManager._mouseUpListener = result;
    return result;
};

/**
 * type:
 *  - startdragging.
 *  - dragging
 *  - enddragging
 */
mindplot.DragManager.prototype. addEventListener = function(type, listener)
{
    this._listeners[type] = listener;
};

mindplot.DragManager.DRAG_PRECISION_IN_SEG = 100;
