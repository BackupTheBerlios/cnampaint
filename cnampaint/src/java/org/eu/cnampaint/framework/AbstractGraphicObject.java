/*
 * Copyright (c) 2005, Alexandre ROMAN
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of the CnamPaint project nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: AbstractGraphicObject.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import java.awt.Rectangle;


/**
 * Implémentation abstraite de <tt>GraphicObject</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public abstract class AbstractGraphicObject extends BaseObject
  implements GraphicObject {
    //~ Champs d'instance ------------------------------------------------------

    private GraphicProperties graphicProperties = new GraphicProperties();
    private Long              id;
    private int               height;
    private int               width;
    private int               x;
    private int               y;

    //~ Constructeurs ----------------------------------------------------------

    public AbstractGraphicObject() {
        this(0, 0);
    }


    public AbstractGraphicObject(final int x, final int y) {
        this(0, 0, 0, 0);
    }


    public AbstractGraphicObject(final int x, final int y, final int width,
        final int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    //~ Méthodes ---------------------------------------------------------------

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    public void setGraphicProperties(GraphicProperties graphicProperties) {
        this.graphicProperties = graphicProperties;
    }


    public GraphicProperties getGraphicProperties() {
        return graphicProperties;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getHeight() {
        return height;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getWidth() {
        return width;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getX() {
        return x;
    }


    public void setY(int y) {
        this.y = y;
    }


    public int getY() {
        return y;
    }


    public void offset(int offsetX, int offsetY) {
        setX(getX() + offsetX);
        setY(getY() + offsetY);
    }
}
