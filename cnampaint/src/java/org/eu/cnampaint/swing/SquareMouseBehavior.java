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
 * $Id: SquareMouseBehavior.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.eu.cnampaint.framework.GraphicObject;


/**
 * Comportement de souris pour un carr�.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
public class SquareMouseBehavior extends RectangularMouseBehavior {
    //~ Constructeurs ----------------------------------------------------------

    public SquareMouseBehavior(final DrawArea drawArea) {
        super(drawArea);
    }

    //~ M�thodes ---------------------------------------------------------------

    protected void updateDraw(int startX, int startY, int currentX, int currentY) {
        final int x;
        final int y;
        final int width;

        if (currentX > startX) {
            x = startX;

            if (currentY > startY) {
                y         = startY;
                width     = currentX - startX;
            } else {
                width     = currentX - startX;
                y         = startY - width;
            }
        } else {
            x = currentX;

            if (currentY > startY) {
                y         = startY;
                width     = startX - currentX;
            } else {
                width     = startX - currentX;
                y         = startY - width;
            }
        }

        final GraphicObject graphicObject = drawArea.getCurrentGraphicObject();
        graphicObject.setX(x);
        graphicObject.setY(y);
        graphicObject.setWidth(width);

        drawArea.repaint();
    }
}
