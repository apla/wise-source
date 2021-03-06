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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.04.14 at 05:27:07 PM ART 
//


package com.wisemapping.xml.svgmap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2000/svg}polyline" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}line" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}g" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.w3.org/2000/svg}rect" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="focusable" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="height" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="preserveAspectRatio" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="viewBox" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="width" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "polyline",
        "line",
        "g",
        "rect"
        })
@XmlRootElement(name = "svg")
public class Svg {

    @XmlElement(required = true)
    protected List<Polyline> polyline;
    @XmlElement(required = true)
    protected List<Line> line;
    @XmlElement(required = true)
    protected List<G> g;
    @XmlElement(required = true)
    protected List<Rect> rect;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String focusable;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String height;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String preserveAspectRatio;
    @XmlAttribute(required = true)
    protected String viewBox;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String width;

    /**
     * Gets the value of the polyline property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the polyline property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolyline().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Polyline }
     */
    public List<Polyline> getPolyline() {
        if (polyline == null) {
            polyline = new ArrayList<Polyline>();
        }
        return this.polyline;
    }

    /**
     * Gets the value of the line property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the line property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Line }
     */
    public List<Line> getLine() {
        if (line == null) {
            line = new ArrayList<Line>();
        }
        return this.line;
    }

    /**
     * Gets the value of the g property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the g property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getG().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link G }
     */
    public List<G> getG() {
        if (g == null) {
            g = new ArrayList<G>();
        }
        return this.g;
    }

    /**
     * Gets the value of the rect property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rect property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRect().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Rect }
     */
    public List<Rect> getRect() {
        if (rect == null) {
            rect = new ArrayList<Rect>();
        }
        return this.rect;
    }

    /**
     * Gets the value of the focusable property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFocusable() {
        return focusable;
    }

    /**
     * Sets the value of the focusable property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFocusable(String value) {
        this.focusable = value;
    }

    /**
     * Gets the value of the height property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHeight(String value) {
        this.height = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the preserveAspectRatio property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPreserveAspectRatio() {
        return preserveAspectRatio;
    }

    /**
     * Sets the value of the preserveAspectRatio property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPreserveAspectRatio(String value) {
        this.preserveAspectRatio = value;
    }

    /**
     * Gets the value of the viewBox property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getViewBox() {
        return viewBox;
    }

    /**
     * Sets the value of the viewBox property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setViewBox(String value) {
        this.viewBox = value;
    }

    /**
     * Gets the value of the width property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWidth(String value) {
        this.width = value;
    }

}
