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
 * $Id: LineMouseBehavior.java,v 1.2 2005/01/25 15:57:15 romale Exp $
 */


package org.eu.cnampaint.swing;

import org.eu.cnampaint.framework.Line;


/**
 * Comportement de souris pour une ligne.
 *
 * @author alex
 * @version $Revision: 1.2 $, $Date: 2005/01/25 15:57:15 $
 */
public class LineMouseBehavior extends RectangularMouseBehavior {
    //~ Constructeurs ----------------------------------------------------------

    public LineMouseBehavior(final DrawArea drawArea) {
        super(drawArea);
    }

    //~ Méthodes ---------------------------------------------------------------

    protected void updateDraw(int startX, int startY, int x, int y) {
        final Line line = (Line) drawArea.getCurrentGraphicObject();
        line.setX(startX);
        line.setY(startY);
        line.setX2(x);
        line.setY2(y);
        drawArea.repaint();
    }
}
