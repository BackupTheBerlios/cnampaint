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
 * $Id: Line.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;


/**
 * Implémentation d'une ligne droite.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class Line extends AbstractShapeGraphicObject {
    //~ Champs d'instance ------------------------------------------------------

    private int x2;
    private int y2;

    //~ Constructeurs ----------------------------------------------------------

    public Line() {
        this(0, 0, 0, 0);
    }


    public Line(final int x1, final int y1, final int x2, final int y2) {
        super(x1, y1);
        setX2(x2);
        setY2(y2);
    }

    //~ Méthodes ---------------------------------------------------------------

    public Rectangle getBounds() {
        final int x = Math.min(getX(), getX2());
        final int y = Math.min(getY(), getY2());

        return new Rectangle(x, y, getWidth(), getHeight());
    }


    public void setHeight(int height) {
    }


    public int getHeight() {
        return Math.abs(getY() - getY2());
    }


    public void setWidth(int width) {
    }


    public int getWidth() {
        return Math.abs(getX() - getX2());
    }


    public void setX2(int x2) {
        this.x2 = x2;
    }


    public int getX2() {
        return x2;
    }


    public void setY2(int y2) {
        this.y2 = y2;
    }


    public int getY2() {
        return y2;
    }


    public void offset(int offsetX, int offsetY) {
        setX(getX() + offsetX);
        setY(getY() + offsetY);
        setX2(getX2() + offsetX);
        setY2(getY2() + offsetY);
    }


    protected Shape getShape() {
        return new Line2D.Float(getX(), getY(), getX2(), getY2());
    }
}
