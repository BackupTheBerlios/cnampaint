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
 * $Id: AbstractShapeGraphicObject.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.framework;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;


/**
 * Impl�mentation de <tt>GraphicObject</tt> utilisant un objet <tt>Shape</tt>
 * pour d�crire la forme. L'objet <tt>Shape</tt> est d�fini par la m�thode �
 * impl�menter <tt>getShape()</tt>.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public abstract class AbstractShapeGraphicObject extends AbstractGraphicObject {
    //~ Constructeurs ----------------------------------------------------------

    public AbstractShapeGraphicObject() {
        this(0, 0, 0, 0);
    }


    public AbstractShapeGraphicObject(final int x, final int y) {
        this(x, y, 0, 0);
    }


    public AbstractShapeGraphicObject(final int x, final int y,
        final int width, final int height) {
        super(x, y, width, height);
    }

    //~ M�thodes ---------------------------------------------------------------

    public final void paint(Graphics g) {
        final Graphics2D        g2                = (Graphics2D) g.create();
        final GraphicProperties graphicProperties = getGraphicProperties();

        g2.setPaint(graphicProperties.getBackgroundColor());
        g2.setStroke(new BasicStroke(graphicProperties.getBorderWidth()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                graphicProperties.getTransparency()));

        final Shape shape = getShape();
        if (shape != null) {
            if (graphicProperties.isFilling()) {
                g2.setColor(graphicProperties.getBackgroundColor());
                g2.fill(shape);
            }

            g2.setColor(graphicProperties.getBorderColor());
            g2.draw(shape);
        }

        g2.dispose();
    }


    /**
     * Retourne un objet <tt>Shape</tt> qui d�finit la forme de l'objet
     * graphique.
     *
     * @return un objet <tt>Shape</tt> d�finissant la forme
     */
    protected abstract Shape getShape();
}
