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
 * $Id: CircleMouseBehavior.java,v 1.1 2005/01/03 23:51:54 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.eu.cnampaint.framework.Circle;


/**
 * Comportement de souris pour un cercle.
 *
 * @author alex
 * @version $Revision: 1.1 $, $Date: 2005/01/03 23:51:54 $
 */
class CircleMouseBehavior extends RectangularMouseBehavior {
    //~ Constructeurs ----------------------------------------------------------

    public CircleMouseBehavior(final DrawArea drawArea) {
        super(drawArea);
    }

    //~ Méthodes ---------------------------------------------------------------

    protected void updateDraw(int startX, int startY, int x, int y) {
        final Circle circle = (Circle) drawArea.getCurrentGraphicObject();

        // distance entre les 2 points: sqrt((x1 - x2)^2 + (y1 - y2)^2)
        final int distance = (int) Math.round(Math.sqrt(Math.pow(x - startX, 2) +
                    Math.pow(y - startY, 2)));
        final int diameter = (int) Math.round(distance / 2.0);
        final int newX     = startX - diameter;
        final int newY     = startY - diameter;

        circle.setX(newX);
        circle.setY(newY);
        circle.setWidth(distance);
        drawArea.repaint();
    }
}
